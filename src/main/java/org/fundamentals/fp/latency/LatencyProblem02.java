package org.fundamentals.fp.latency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.Tuple2;
import io.vavr.control.Try;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;
import static org.fundamentals.fp.latency.SimpleCurl.fetch;

/**
 * Problem 2
 * Greek gods are quite popular and they have presence in Wikipedia, the multilingual online encyclopedia.
 * If you try to find further information about Zeus you should visit the address: https://en.wikipedia.org/wiki/Zeus
 *
 * Load the list of Greek Gods and discover what is the God with more literature described on Wikipedia.
 *
 * Notes:
 * Every connection with any API has a Timeout of 2 seconds.
 * REST API 1: https://my-json-server.typicode.com/jabrena/latency-problems/greek
 * REST API 2: https://en.wikipedia.org/wiki/{greekGod}
 */
@Slf4j
public class LatencyProblem02 {

    final int TIMEOUT = 5;

    final String greekGods = "http://my-json-server.typicode.com/jabrena/latency-problems/greek";
    final String wikipediaPath = "https://en.wikipedia.org/wiki/";

    Function<String, URL> toURL = address -> Try.of(() ->
            new URL(address)).getOrElseThrow(ex -> {
                LOGGER.error(ex.getLocalizedMessage(), ex);
                throw new RuntimeException("Bad address", ex);
            });

    Function<String, Stream<String>> serialize = param -> Try.of(() -> {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> deserializedData = objectMapper.readValue(param, new TypeReference<List<String>>() {});
        return deserializedData.stream();
    }).getOrElse(() -> {
        LOGGER.error("Bad Serialization process");
        return Stream.of("BAD_SERIALIZED");
    });

    Function<String, URL> buildWikipediaAddress = god -> Try.of(() ->
            new URL(wikipediaPath + god)).get();

    Function<String, Tuple2<String, Integer>> getWikipediaContent = god ->
            new Tuple2<String, Integer>(god, fetch.apply(buildWikipediaAddress.apply(god)).length());

    public String JavaStreamSolution() {

            return Stream.of(greekGods)
                    .flatMap(toURL.andThen(fetch).andThen(serialize))
                    .map(getWikipediaContent)
                    .peek(System.out::println)
                    .max((i, j) -> i._2.compareTo(j._2))
                    .get()._1;
    }

    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    Function<String, CompletableFuture<Tuple2<String,Integer>>> fetchAsync = address -> {

        LOGGER.info("Thread: {}", Thread.currentThread().getName());
        return CompletableFuture
                .supplyAsync(() -> getWikipediaContent.apply(address), executor)
                .exceptionally(ex -> {
                    LOGGER.error(ex.getLocalizedMessage(), ex);
                    return new Tuple2<String,Integer>(address + "-ERROR", 0);
                })
                .completeOnTimeout(new Tuple2<String,Integer>(address + "-TIMEOUT", 0), TIMEOUT, TimeUnit.SECONDS);
    };

    public String JavaStreamSolutionAsync() {

        List<CompletableFuture<Tuple2<String, Integer>>> futureRequests = Stream.of(greekGods)
                .flatMap(toURL.andThen(fetch).andThen(serialize))
                .map(fetchAsync)
                .collect(toList());

        return futureRequests.stream()
                .map(CompletableFuture::join)
                .peek(System.out::println)
                .max((i, j) -> i._2.compareTo(j._2))
                .get()._1;
    }
}
