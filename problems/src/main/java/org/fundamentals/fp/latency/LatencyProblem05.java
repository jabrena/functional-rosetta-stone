package org.fundamentals.fp.latency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Single;
import io.vavr.Function1;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import io.vavr.control.Try;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fundamentals.fp.euler.IEulerType3;
import reactor.core.publisher.Mono;

import static java.util.stream.Collectors.toList;
import static org.fundamentals.fp.latency.SimpleCurl.fetch;
import static org.fundamentals.fp.latency.SimpleCurl.log;

/**
 * Feature: Load Balancing
 *
 * Scenario: Consume a REST Greek God Service
 *     Given a 5 instances of the same REST API about Greek gods
 *     When  the client sends the request
 *     And   execute a load balancing policy to distribute the traffic
 *     Then  return all gods starting with `a`
 *
 * - Try to test the solution without any Internet call
 * - Review the timeout for Every connection.
 * - Review the load balancing options
 * - REST API 1: https://my-json-server.typicode.com/jabrena/latency-problems/greek
 *
 */
@Slf4j
@RequiredArgsConstructor
public class LatencyProblem05 implements IEulerType3<List<String>> {

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

    Function<Tuple2<URL, Config>, CompletableFuture<String>> fetchAsync = (tuple) -> {

        LOGGER.info("Thread: {}", Thread.currentThread().getName());
        return CompletableFuture
                .supplyAsync(() -> fetch.andThen(log).apply(tuple._1), tuple._2().getExecutor())
                .exceptionally(ex -> {
                    LOGGER.error(ex.getLocalizedMessage(), ex);
                    return "FETCH_BAD_RESULT";
                })
                .completeOnTimeout("[\"FETCH_BAD_RESULT_TIMEOUT\"]", tuple._2.getTimeout(), TimeUnit.SECONDS);
    };

    Function<String, List<String>> serialize = param -> Try.of(() -> {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> deserializedData = objectMapper.readValue(param, new TypeReference<List<String>>() {});
        return deserializedData;
    }).getOrElseThrow(ex -> {
        LOGGER.error("Bad Serialization process", ex);
        throw new RuntimeException(ex);
    });

    Predicate<String> godStartingByA = s -> s.toLowerCase().charAt(0) == 'a';

    Function1<List<String>, List<URL>> validAddress = list -> list.stream()
            .map(toURL)
            .filter(Option::isDefined)
            .map(Option::get)
            .collect(toList());

    Function1<Config, Tuple2<URL, Config>> loadBalance = config -> {
        List<URL> validAddressList = validAddress.apply(config.getList());
        Integer index = new Random().nextInt(validAddressList.size());
        return Tuple.of(validAddressList.get(index), config);
    };

    @Override
    public List<String> JavaStreamSolution() {

        return loadBalance
                .andThen(fetchAsync)
                .andThen(CompletableFuture::join)
                .andThen(serialize)
                .andThen(l -> l.stream()
                        .filter(godStartingByA)
                        .peek(System.out::println)
                        .collect(toList()))
                .apply(config);
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


