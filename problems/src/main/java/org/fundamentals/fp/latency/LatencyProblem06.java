package org.fundamentals.fp.latency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.vavr.Function1;
import io.vavr.control.Option;
import io.vavr.control.Try;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toUnmodifiableList;
import static org.fundamentals.fp.latency.SimpleCurl.fetch;

/**
 * Feature: Retry
 *
 * Scenario: Consume a REST Greek God Service
 *  Given a REST API about Greek gods
 *  When  the client sends the request
 *  And   execute a Retry Policy
 *  Then  return all gods starting with `a`
 *
 * - Try to test the solution without any Internet call
 * - Review the timeout for Every connection.
 * - Review the load balancing options
 * - REST API 1: https://my-json-server.typicode.com/jabrena/latency-problems/greek
 *
 */
@Slf4j
@RequiredArgsConstructor
public class LatencyProblem06 {

    @Data
    @AllArgsConstructor
    public static class Config {

        private String address;
        private Executor executor;
        private int timeout;
    }

    private final Config config;

    Function1<String, URL> toURL = address -> Try
        .of(() -> new URL(address))
        .onFailure(ex -> LOGGER.error(ex.getLocalizedMessage(), ex))
        .getOrElseThrow(() -> new RuntimeException("Bad URL"));

    Function1<String, List<String>> serialize = param -> Try
        .of(() -> {
            ObjectMapper objectMapper = new ObjectMapper();
            List<String> deserializedData = objectMapper.readValue(param, new TypeReference<List<String>>() {});
            return deserializedData;
        }).getOrElseThrow(ex -> {
            LOGGER.error("Bad Serialization process", ex);
            throw new RuntimeException(ex);
        });

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

    Function1<Config, Option<List<String>>> fetchAsyncObservability = config -> {

        long startTime = System.currentTimeMillis();

        Option<List<String>> result = fetchAsync
                .andThen(CompletableFuture::join)
                .apply(config);

        long stopTime = System.currentTimeMillis();
        double elapsedTime = (stopTime - startTime) / 1000d;
        LOGGER.debug("Async execution: {} seconds", elapsedTime);

        return result;
    };

    Predicate<String> godStartingByA = s -> s.toLowerCase().charAt(0) == 'a';

    Function1<Config, Option<List<String>>> consumeService = config ->
        fetchAsyncObservability
            .andThen(o ->
                o.map(l -> l.stream()
                            .filter(godStartingByA)
                            .peek(LOGGER::debug)
                            .collect(toUnmodifiableList()))
                .map(l -> Option.some(l))
                .getOrElse(Option.none()))
            .apply(config);

    Function1<Supplier<Option<List<String>>>, Supplier<Option<List<String>>>> retryBehaviour = supplier -> {

        RetryConfig customConfig = RetryConfig.custom()
                .retryOnResult(r -> r.equals(Option.none()))
                .build();

        Retry retry = Retry.of("retry", customConfig);
        retry.getEventPublisher().onRetry(event -> LOGGER.warn("Applying #Resilience4j Retry"));

        Supplier<Option<List<String>>> decoratedSupplier = Decorators.ofSupplier(supplier)
                .withRetry(retry)
                .decorate();

        return decoratedSupplier;
    };

    public Option<List<String>> JavaStreamSolution() {

        final Supplier<Option<List<String>>> supplier = () -> consumeService.apply(config);

        return Try.ofSupplier(retryBehaviour.apply(supplier))
                .onFailure(ex -> LOGGER.warn(ex.getLocalizedMessage(), ex))
                .recover(ex -> Option.none())
                .get();
    }

}


