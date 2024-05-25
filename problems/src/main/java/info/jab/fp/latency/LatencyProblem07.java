package info.jab.fp.latency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.core.SupplierUtils;
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

import static info.jab.fp.latency.SimpleCurl.fetch;
import static java.util.stream.Collectors.toUnmodifiableList;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

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
public class LatencyProblem07 {

    private static final Logger LOGGER = LoggerFactory.getLogger(LatencyProblem07.class);

    public static record Config(String address,Executor executor, int timeout) {};

    private final Config config;

    public LatencyProblem07(info.jab.fp.latency.LatencyProblem07.Config config) {
        this.config = config;
    }

    //It is necessary to maintain in memory the Circuit Breaker
    private static class CB {

        public static CircuitBreaker circuitBreaker;

        static {

            CircuitBreakerConfig customConfig = CircuitBreakerConfig.custom()
                    .failureRateThreshold(50)
                    .waitDurationInOpenState(Duration.ofMillis(1000))
                    .ringBufferSizeInHalfOpenState(5)
                    .ringBufferSizeInClosedState(5)
                    .automaticTransitionFromOpenToHalfOpenEnabled(true)
                    .build();

            CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(customConfig);
            circuitBreaker = circuitBreakerRegistry.circuitBreaker("circuitBreaker");
        }

        public static synchronized CircuitBreaker getCircuitBreaker() {
            return circuitBreaker;
        }
    }

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
            .supplyAsync(() -> toURL.andThen(fetch).apply(config.address()), config.executor())
            .orTimeout(config.timeout(), TimeUnit.SECONDS)
            .thenApply(serialize)
            .handle((response, ex) -> {
                if(Objects.isNull(ex)) {
                    return Option.some(response);
                }
                LOGGER.warn(ex.getLocalizedMessage(), ex);
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

        //Metrics
        LOGGER.debug("getNumberOfBufferedCalls: {}", CB.getCircuitBreaker().getMetrics().getNumberOfBufferedCalls());
        LOGGER.debug("getNumberOfFailedCalls: {}", CB.getCircuitBreaker().getMetrics().getNumberOfFailedCalls());
        LOGGER.debug("getNumberOfSuccessfulCalls: {}", CB.getCircuitBreaker().getMetrics().getNumberOfSuccessfulCalls());

        //Forcing a failure for Circuit Breaker if the Functor returns an Option.none()
        Supplier<Option<List<String>>> supplierWithResultHandling = SupplierUtils.andThen(supplier, result -> {
            if(!result.isDefined()) {
                throw new RuntimeException("Triggering a failure for CircuitBreaker");
            }
            return result;
        });

        Supplier<Option<List<String>>> decoratedSupplier = Decorators.ofSupplier(supplierWithResultHandling)
                .withCircuitBreaker(CB.getCircuitBreaker())
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
