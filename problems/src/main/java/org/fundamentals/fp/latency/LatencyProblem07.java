package org.fundamentals.fp.latency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.decorators.Decorators;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.control.Option;
import io.vavr.control.Try;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toUnmodifiableList;
import static org.fundamentals.fp.latency.SimpleCurl.fetch;

/**
 * Feature: Consume a REST Roman God Service
 *
 * Scenario: Consume the API in a Happy path
 *     Given a REST API about Roman gods
 *     When  the client sends the request
 *     And   execute a Circuit Breaker Policy
 *     Then  return all gods finishing the name with `s`
 *
 * Scenario: Force an internal Circuit Breaker behaviour
 *     Given a REST API about Roman gods
 *     When  the client sends the request
 *     And   execute a Circuit Breaker Policy
 *     Then  return all gods finishing the name with `s`
 *
 * - Try to test the solution without any Internet call
 * - Review the timeout for Every connection.
 * - Review the circuit breaker options
 * - REST API 1: https://my-json-server.typicode.com/jabrena/latency-problems/roman
 *
 */
@Slf4j
@RequiredArgsConstructor
public class LatencyProblem07 {

    @Data
    @AllArgsConstructor
    public static class Config {

        private String address;
        private Executor executor;
        private int timeout;
        private int maxRetryAttempts;
    }

    @NonNull
    private final Config config;

    Function1<String, URL> toURL = address -> Try
        .of(() -> new URL(address))
        .onFailure(ex -> LOGGER.error(ex.getLocalizedMessage(), ex))
        .getOrElseThrow(ex -> new RuntimeException("Bad URL", ex));

    Function1<String, List<String>> serialize = param -> Try
        .of(() -> {
            ObjectMapper objectMapper = new ObjectMapper();
            List<String> deserializedData = objectMapper.readValue(param, new TypeReference<List<String>>() {});
            return deserializedData;
        })
        .onFailure(ex -> LOGGER.error(ex.getLocalizedMessage(), ex))
        .getOrElseThrow(ex -> new RuntimeException("Bad Serialization process", ex));

    Function1<Config, CompletableFuture<Option<List<String>>>> fetchAsync = config ->

        CompletableFuture
            .supplyAsync(() -> toURL.andThen(fetch).apply(config.getAddress()), config.getExecutor())
            .orTimeout(config.getTimeout(), TimeUnit.SECONDS)
            .thenApply(serialize)
            .handle((response, ex) -> {
                if(Objects.isNull(ex)) {
                    return Option.some(response);
                }
                return Option.none();
            });

    Function1<Config, Option<List<String>>> fetchAsyncWithMetrics = config -> {

        double startTime = System.currentTimeMillis();

        Option<List<String>> result = fetchAsync
                .andThen(CompletableFuture::join)
                .apply(config);

        double stopTime = System.currentTimeMillis();
        double elapsedTime = (stopTime - startTime) / 1000d;
        LOGGER.debug("Async execution: {} seconds", elapsedTime);

        return result;
    };

    Predicate<String> godStartingByS = s -> s.toLowerCase().charAt(s.length() - 1) == 's';

    Function1<Option<List<String>>, Option<List<String>>> filterGreekGods = ols -> ols
                .map(l -> l.stream()
                    .filter(godStartingByS)
                    .peek(LOGGER::debug)
                    .collect(toUnmodifiableList()))
                .map(l -> Option.some(l))
                .getOrElse(Option.none());

    Function2<Supplier<Option<List<String>>>, Config, Supplier<Option<List<String>>>> circuitBreakerBehaviour = (supplier, config) -> {

        CircuitBreakerConfig customConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .ringBufferSizeInHalfOpenState(2)
                .ringBufferSizeInClosedState(2)
                .build();

        CircuitBreakerRegistry circuitBreakerRegistry =
                CircuitBreakerRegistry.of(customConfig);
        circuitBreakerRegistry.getEventPublisher().onEntryAdded(event -> LOGGER.warn("Applying #Resilience4j Circuit Breaker"));

        CircuitBreaker circuitBreaker = circuitBreakerRegistry
                .circuitBreaker("circuitBreaker");

        Supplier<Option<List<String>>> decoratedSupplier = Decorators.ofSupplier(supplier)
                .withCircuitBreaker(circuitBreaker)
                .decorate();

        return decoratedSupplier;
    };

    public Option<List<String>> JavaStreamSolution() {

        final Supplier<Option<List<String>>> supplier = () ->
                fetchAsyncWithMetrics.andThen(filterGreekGods).apply(config);

        return Try.ofSupplier(circuitBreakerBehaviour.apply(supplier, config))
                .onFailure(ex -> LOGGER.warn(ex.getLocalizedMessage(), ex))
                .recover(ex -> Option.none())
                .get();
    }

}
