package info.jab.fp.latency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Stream;

import static info.jab.fp.latency.SimpleCurl.fetch;
import static info.jab.fp.latency.SimpleCurl.log;
import static java.util.stream.Collectors.toList;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;

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
public class LatencyProblem02 {

    private static final Logger LOGGER = LoggerFactory.getLogger(LatencyProblem02.class);

    final int TIMEOUT = 15;

    final String greekGods = "http://my-json-server.typicode.com/jabrena/latency-problems/greek";
    final String wikipediaPath = "https://en.wikipedia.org/wiki/";

    Function<String, URL> toURL = (address) -> {
        try {
                return new URL(address);
        } catch(IOException ex) {
                LOGGER.error(ex.getLocalizedMessage(), ex);
                throw new RuntimeException("Bad address", ex);
        }
    };

    Function<String, Stream<String>> serialize = (param) -> {
        try {
                ObjectMapper objectMapper = new ObjectMapper();
                List<String> deserializedData = objectMapper.readValue(param, new TypeReference<List<String>>() {});
                return deserializedData.stream();
        } catch(IOException ex) {
                LOGGER.error("Bad Serialization process", ex);
                throw new RuntimeException("Bad Serialization process", ex);
        }
    };

    Function<String, URL> buildWikipediaAddress = god -> {
        try {
            return new URL(wikipediaPath + god);
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Bad address", ex);
        }
    };

    record Tuple2(String param1, Integer param2) {}

        Function<String, Tuple2> getWikipediaContent = god -> {
                String content = fetch.apply(buildWikipediaAddress.apply(god));
                return new Tuple2(god, content.length());
        };

    public String JavaStreamSolution() {

            return Stream.of(greekGods)
                .flatMap(toURL.andThen(fetch).andThen(serialize))
                .map(getWikipediaContent)
                .peek(System.out::println)
                .max((i, j) -> i.param2().compareTo(j.param2()))
                .get().param1();
    }

    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    Function<String, CompletableFuture<Tuple2>> fetchAsync = address -> {

        LOGGER.info("Thread: {}", Thread.currentThread().getName());
        return CompletableFuture
                .supplyAsync(() -> getWikipediaContent.apply(address), executor)
                .exceptionally(ex -> {
                    LOGGER.error(ex.getLocalizedMessage(), ex);
                    return new Tuple2(address + "-ERROR", 0);
                })
                .completeOnTimeout(new Tuple2(address + "-TIMEOUT", 0), TIMEOUT, TimeUnit.SECONDS);
    };

    public String JavaStreamSolutionAsync() {

        List<CompletableFuture<Tuple2>> futureRequests = Stream.of(greekGods)
                .flatMap(toURL.andThen(fetch).andThen(serialize))
                .map(fetchAsync)
                .collect(toList());

        return futureRequests.stream()
                .map(CompletableFuture::join)
                .peek(System.out::println)
                .max((i, j) -> i.param2().compareTo(j.param2()))
                .get().param1();
    }

    Function<String, Stream<String>> fetchGods = s -> Stream.of(s)
            .flatMap(toURL.andThen(fetch).andThen(log).andThen(serialize));

    Function<Stream<String>, Stream<Tuple2>> fetchWikipediaAsync = ls -> {
        List<CompletableFuture<Tuple2>> futureRequests = ls
                .map(fetchAsync)
                .collect(toList());

        return futureRequests.stream()
                .map(CompletableFuture::join)
                .map(s -> {
                    LOGGER.debug(s.param1());
                    return s;
                });
    };

    Function<Stream<Tuple2>, String> max = ls -> ls
        .max((i, j) -> i.param2().compareTo(j.param2()))
        .get().param1();

    public String JavaStreamSolutionAsync2() {

         return fetchGods
                .andThen(fetchWikipediaAsync)
                .andThen(max)
                .apply(greekGods);
    }
}
