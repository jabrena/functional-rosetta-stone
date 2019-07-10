package org.fundamentals.fp.latency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;
import static org.fundamentals.fp.latency.SimpleCurl.fetch;
import static org.fundamentals.fp.latency.SimpleCurl.log;

/**
 * Problem 1
 * Ancient European peoples worshiped many gods like Greek, Roman & Nordic gods.
 * Every God is possible to be represented as the concatenation of every character converted in Decimal.
 * Zeus = 122101117115
 *
 * Load the list of Gods and find the sum of God names starting with the letter n.
 *
 * Notes:
 * Every connection with any API has a Timeout of 2 seconds.
 * If in the process to load the list, the timeout is reached, the process will calculate with the rest of the lists.
 * REST API: https://my-json-server.typicode.com/jabrena/latency-problems
 */
@Slf4j
public class LatencyProblem01 {

    final int TIMEOUT = 5;

    final List<String> listOfGods = List.of(
            "http://my-json-server.typicode.com/jabrena/latency-problems/greek",
            "http://my-json-server.typicode.com/jabrena/latency-problems/nordic",
            "http://my-json-server.typicode.com/jabrena/latency-problems/roman");

    Function<String, URL> toURL = address -> Try.of(() ->
            new URL(address)).getOrElseThrow(ex -> {
                LOGGER.error(ex.getLocalizedMessage(), ex);
                throw new RuntimeException("Bad address", ex);
            });

    Function<String, Stream<String>> serialize = param -> Try.of(() -> {
            ObjectMapper objectMapper = new ObjectMapper();
            List<String> deserializedData = objectMapper.readValue(param, new TypeReference<List<String>>() {});
            return deserializedData.stream();
    }).getOrElse(() -> { //TODO Review this error flow
            LOGGER.error("Bad Serialization process");
            return Stream.of("BAD_SERIALIZED");
    });

    Predicate<String> godStartingByn = s -> s.toLowerCase().charAt(0) == 'n';

    Function<String, List<Integer>> toDigits = s -> s.chars()
            .mapToObj(is -> Integer.valueOf(is))
            .collect(Collectors.toList());

    Function<List<Integer>, String> concatDigits = li -> li.stream()
            .map(String::valueOf)
            .collect(Collectors.joining( "" ));

    public BigInteger JavaStreamSolution() {

        return listOfGods.stream()
                .flatMap(toURL.andThen(fetch).andThen(serialize))
                .filter(godStartingByn)
                .map(toDigits.andThen(concatDigits).andThen(BigInteger::new))
                .reduce(BigInteger.ZERO, (l1, l2) -> l1.add(l2));
    }

    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    Function<URL, CompletableFuture<String>> fetchAsync = address -> {

        LOGGER.info("Thread: {}", Thread.currentThread().getName());
        return CompletableFuture
                .supplyAsync(() -> fetch.andThen(log).apply(address), executor)
                .exceptionally(ex -> {
                    LOGGER.error(ex.getLocalizedMessage(), ex);
                    return "FETCH_BAD_RESULT";
                })
                .completeOnTimeout("[\"FETCH_BAD_RESULT_TIMEOUT\"]", TIMEOUT, TimeUnit.SECONDS);
    };

    Consumer<String> print = LOGGER::info;

    public BigInteger JavaStreamSolutionAsync() {

        List<CompletableFuture<String>> futureRequests = listOfGods.stream()
                .map(toURL.andThen(fetchAsync))
                .collect(toList());

        return futureRequests.stream()
                .map(CompletableFuture::join)
                .flatMap(serialize)
                .filter(godStartingByn)
                .peek(print)
                .map(toDigits.andThen(concatDigits).andThen(BigInteger::new))
                .reduce(BigInteger.ZERO, (l1, l2) -> l1.add(l2));
    }
}
