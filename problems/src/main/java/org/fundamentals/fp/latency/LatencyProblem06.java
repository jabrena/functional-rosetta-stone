package org.fundamentals.fp.latency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.reactivex.Single;
import io.vavr.Function1;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import io.vavr.control.Try;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fundamentals.fp.euler.IEulerType3;
import reactor.core.publisher.Mono;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableList;
import static org.fundamentals.fp.latency.SimpleCurl.fetch2;

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
public class LatencyProblem06 implements IEulerType3<List<String>> {

    @Data
    @AllArgsConstructor
    public static class Config {

        private List<String> list;
        private Executor executor;
        private int timeout;
    }

    private final Config config;

    @Override
    public List<String> JavaSolution() {
        return null;
    }

    Function1<String, Option<URL>> toURL = address ->
            Try.of(() -> new URL(address))
                .map(u -> Option.some(u))
                .onFailure(ex -> LOGGER.error(ex.getLocalizedMessage(), ex))
                .recover(ex -> Option.none())
                .get();

    Function<Config, CompletableFuture<Option<String>>> fetchAsync = (config) -> {

        LOGGER.info("Thread: {}", Thread.currentThread().getName());
        return CompletableFuture
                .supplyAsync(() -> toURL
                        .andThen(u -> u.getOrElseThrow(() -> new RuntimeException("Bad URL")))
                        .andThen(fetch2)
                        .apply(config.list.get(0)), config.getExecutor())
                .exceptionally(ex -> {
                    LOGGER.error("TIMEOUT: {}", ex.getLocalizedMessage(), ex);
                    return Option.none();
                })
                .completeOnTimeout(Option.none(), config.getTimeout(), TimeUnit.SECONDS);
    };

    Function<String, List<String>> serialize = param -> Try.of(() -> {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> deserializedData = objectMapper.readValue(param, new TypeReference<List<String>>() {});
        return deserializedData;
    })
    .onFailure(ex -> LOGGER.error("Bad Serialization process {}", ex.getLocalizedMessage(), ex))
    .recover(ex -> new ArrayList<>())
    .get();

    Predicate<String> godStartingByA = s -> s.toLowerCase().charAt(0) == 'a';

    Function1<Config, Option<String>> fetchAsyncObservability = config -> {

        long startTime = System.currentTimeMillis();

        Option<String> result = fetchAsync
                .andThen(CompletableFuture::join)
                .apply(config);

        long stopTime = System.currentTimeMillis();
        double elapsedTime = (stopTime - startTime) / 1000d;
        LOGGER.info("Execution time: {}", elapsedTime);

        return result;
    };

    Function1<Config, List<String>> consumeService = config -> {

        return fetchAsyncObservability
                .andThen(o -> o.map(serialize.andThen(l -> l.stream()
                        .filter(godStartingByA)
                        .peek(System.out::println)
                        .collect(toUnmodifiableList()))
                ).getOrElse(new ArrayList<>()))
                .apply(config);
    };

    Function1<Supplier<List<String>>, Supplier<List<String>>> retryBehaviour = supplier -> {

        RetryConfig customConfig = RetryConfig.custom()
                .retryOnResult(r -> r.equals(new ArrayList<String>()))
                .build();

        Retry retry = Retry.of("retry", customConfig);
        retry.getEventPublisher().onRetry(event -> LOGGER.info("Retrying execution"));

        Supplier<List<String>> decoratedSupplier = Decorators.ofSupplier(supplier)
                .withRetry(retry)
                .decorate();

        return decoratedSupplier;
    };

    @Override
    public List<String> JavaStreamSolution() {

        Supplier<List<String>> supplier = () -> consumeService.apply(config);

        return Try.ofSupplier(retryBehaviour.apply(supplier))
                .onFailure(ex -> LOGGER.error(ex.getLocalizedMessage(), ex))
                .recover(ex -> new ArrayList<>())
                .get();
    }

    @Override
    public List<String> VAVRSolution() {
        return null;
    }

    @Override
    public Mono<List<String>> ReactorSolution() {
        return null;
    }

    @Override
    public Single<List<String>> RxJavaSolution() {
        return null;
    }

    @Override
    public List<String> KotlinSolution() {
        return null;
    }

}


