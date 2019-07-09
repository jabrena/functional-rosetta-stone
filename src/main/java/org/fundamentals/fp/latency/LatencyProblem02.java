package org.fundamentals.fp.latency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.Function1;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import io.vavr.control.Try;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;
import static java.util.stream.Collectors.toList;

@Slf4j
public class LatencyProblem02 {

    final int TIMEOUT = 5;

    final String greekGods = "http://my-json-server.typicode.com/jabrena/latency-problems/greek";
    final String wikipediaPath = "https://en.wikipedia.org/wiki/";

    Function<URL, String> curl = url -> Try.of(() -> {

        LOGGER.info("Thread: {}", Thread.currentThread().getName());
        LOGGER.info("Requested URL: {}", url);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(url.toURI())
                //.timeout(Duration.ofSeconds(1))
                .build();

        return client
                .send(request, HttpResponse.BodyHandlers.ofString())
                .body();
    }).getOrElse("Bad request");

    Function1<String, Either<Throwable, URL>> toURL = address ->
            Try.of(() -> new URL(address)).toEither();

    Predicate<Either<Throwable, URL>> validURL = either ->
            Match(either).of(
                    Case($Right($()), true),
                    Case($Left($()), () -> {
                        LOGGER.error(either.getLeft().getLocalizedMessage(), either);
                        return false;
                    })
            );

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
            new Tuple2<String, Integer>(god, curl.apply(buildWikipediaAddress.apply(god)).length());

    public String JavaStreamSolution() {

            return Stream.of(greekGods)
                    .map(toURL)
                    .filter(validURL)
                    .map(Either::get)
                    .flatMap(curl.andThen(serialize))
                    .map(getWikipediaContent)
                    .peek(System.out::println)
                    .max((i, j) -> i._2.compareTo(j._2))
                    .get()._1;
    }

    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    Function<String, CompletableFuture<Tuple2<String,Integer>>> curlAsync = address -> {

        LOGGER.info("Thread: {}", Thread.currentThread().getName());
        return CompletableFuture
                .supplyAsync(() -> getWikipediaContent.apply(address), executor)
                .exceptionally(ex -> {
                    LOGGER.error(ex.getLocalizedMessage(), ex);
                    return new Tuple2<String,Integer>(address, 0);
                })
                .completeOnTimeout(new Tuple2<String,Integer>(address, 0), TIMEOUT, TimeUnit.SECONDS);
    };

    public String JavaStreamSolutionAsync() {

        List<CompletableFuture<Tuple2<String, Integer>>> futureRequests = Stream.of(greekGods)
                .map(toURL)
                .filter(validURL)
                .map(Either::get)
                .flatMap(curl.andThen(serialize))
                .map(curlAsync)
                .collect(toList());

        return futureRequests.stream()
                .map(CompletableFuture::join)
                .peek(System.out::println)
                .max((i, j) -> i._2.compareTo(j._2))
                .get()._1;
    }
}
