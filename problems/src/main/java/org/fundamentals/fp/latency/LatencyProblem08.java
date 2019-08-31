package org.fundamentals.fp.latency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
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
 * Feature: Consume a REST Indian God Service
 *
 * Scenario: Consume the API in a Happy path case
 *     Given a REST API about Indian gods
 *     When  the client sends the request
 *     And   execute a Rate limiter Policy
 *     Then  return all gods who contains in the name `a` & `i`
 *
 * Scenario: Force a Rate limiter behaviour
 *     Given a REST API about Indian gods
 *     When  the client sends the request
 *     And   execute a Rate limiter Policy
 *     Then  return all gods who contains in the name `a` & `i`
 */
@Slf4j
@RequiredArgsConstructor
public class LatencyProblem08 {

    @Data
    @AllArgsConstructor
    public static class Config {

        private String address;
        private Executor executor;
        private int timeout;
    }

    @NonNull
    private final LatencyProblem08.Config config;

    //It is necessary to maintain in memory the RateLimiter
    private static class RL {

        public static RateLimiter rateLimiter;

        static {

            RateLimiterConfig customConfig = RateLimiterConfig.custom()
                    .limitRefreshPeriod(Duration.ofMillis(1))
                    .limitForPeriod(2)
                    .timeoutDuration(Duration.ofMillis(25))
                    .build();

            RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(customConfig);
            rateLimiterRegistry.getEventPublisher()
                    .onEntryAdded(entryAddedEvent -> {
                        RateLimiter addedRateLimiter = entryAddedEvent.getAddedEntry();
                        LOGGER.info("RateLimiter {} added", addedRateLimiter.getName());
                    })
                    .onEntryRemoved(entryRemovedEvent -> {
                        RateLimiter removedRateLimiter = entryRemovedEvent.getRemovedEntry();
                        LOGGER.info("RateLimiter {} removed", removedRateLimiter.getName());
                    });

            rateLimiter = rateLimiterRegistry.rateLimiter("default");
            rateLimiter.getEventPublisher()
                    .onSuccess(event -> LOGGER.info("Success"))
                    .onFailure(event -> LOGGER.info("Failure"));
        }

        public static synchronized RateLimiter getRateLimiter() {
            return rateLimiter;
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
                    .supplyAsync(() -> toURL.andThen(fetch).apply(config.getAddress()), config.getExecutor())
                    .orTimeout(config.getTimeout(), TimeUnit.SECONDS)
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

    Predicate<String> godNameHasA = s -> s.toLowerCase().contains("a");
    Predicate<String> godNameHasI = s -> s.toLowerCase().contains("i");

    Function1<Option<List<String>>, Option<List<String>>> filterGreekGods = ols -> ols
            .map(l -> l.stream()
                    .filter(godNameHasA.and(godNameHasI))
                    //.peek(LOGGER::debug)
                    .collect(toUnmodifiableList()))
            .map(l -> Option.some(l))
            .getOrElse(Option.none());

    Function2<Supplier<Option<List<String>>>, Config, Supplier<Option<List<String>>>> rateLimiterBehaviour = (supplier, config) -> {

        Supplier<Option<List<String>>> decoratedSupplier = Decorators.ofSupplier(supplier)
                .withRateLimiter(RL.getRateLimiter())
                .decorate();

        return decoratedSupplier;
    };

    public Option<List<String>> JavaStreamSolution() {

        final Supplier<Option<List<String>>> supplier = () ->
                fetchAsyncWithMetrics.andThen(filterGreekGods).apply(config);

        return Try.ofSupplier(rateLimiterBehaviour.apply(supplier, config))
                .onFailure(ex -> LOGGER.warn(ex.getLocalizedMessage(), ex))
                .recover(ex -> Option.none())
                .get();
    }

}
