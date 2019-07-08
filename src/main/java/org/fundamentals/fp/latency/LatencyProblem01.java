package org.fundamentals.fp.latency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.Function1;
import io.vavr.control.Either;
import io.vavr.control.Try;
import java.math.BigInteger;
import java.net.URI;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;
import static java.util.stream.Collectors.toList;

@Slf4j
public class LatencyProblem01 {

    final List<String> listOfGods = List.of(
            "http://my-json-server.typicode.com/jabrena/latency-problems/greek",
            "http://my-json-server.typicode.com/jabrena/latency-problems/nordic",
            "http://my-json-server.typicode.com/jabrena/latency-problems/roman");

    Function<URL, String> curl = url -> Try.of(() -> {

        LOGGER.info("Thread: {}", Thread.currentThread().getName());
        LOGGER.info("Requeted URL: {}", url);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url.toString()))
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
        return Stream.of("");
    });

    Predicate<String> goodStartingByn = value -> value.toLowerCase().charAt(0) == 'n';

    Function<String, List<Integer>> toDigits = value -> value.chars()
            .mapToObj(s -> Integer.valueOf(s))
            .collect(Collectors.toList());

    Function<List<Integer>, String> concatDigits = li -> li.stream()
            .map(String::valueOf)
            .collect(Collectors.joining( "" ));

    public BigInteger JavaStreamSolution() {

        //Sequential Solution
        return listOfGods.stream()
                .map(toURL)
                .filter(validURL)
                .map(Either::get)
                .flatMap(curl.andThen(serialize))
                .filter(goodStartingByn)
                .map(toDigits.andThen(concatDigits).andThen(BigInteger::new))
                .reduce(BigInteger.ZERO, (l1, l2) -> l1.add(l2));
    }

    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    Function<URL, CompletableFuture<String>> curlAsync = address -> {

        LOGGER.info("Thread: {}", Thread.currentThread().getName());
        return CompletableFuture
                .supplyAsync(() -> curl.apply(address), executor)
                .exceptionally(ex -> {
                    LOGGER.error(ex.getLocalizedMessage(), ex);
                    return "FETCH_BAD_RESULT";
                })
                .completeOnTimeout("FETCH_BAD_RESULT_TIMEOUT",1, TimeUnit.SECONDS);
    };

    public BigInteger JavaStreamSolution2() {

        List<CompletableFuture<String>> futureRequests = listOfGods.stream()
                .map(toURL)
                .filter(validURL)
                .map(Either::get)
                .map(curlAsync)
                .collect(toList());

        return futureRequests.stream()
                .map(CompletableFuture::join)
                .flatMap(serialize)
                .filter(goodStartingByn)
                .map(toDigits.andThen(concatDigits).andThen(BigInteger::new))
                .reduce(BigInteger.ZERO, (l1, l2) -> l1.add(l2));
    }
}
