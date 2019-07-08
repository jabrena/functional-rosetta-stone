package org.fundamentals.fp.latency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.Function1;
import io.vavr.control.Either;
import io.vavr.control.Try;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

import org.fundamentals.fp.training.sixth.SimpleCurl;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;

@Slf4j
public class LatencyProblem01 {

    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    Function1<String, Either<Throwable, URL>> toURL = address ->
            Try.of(() -> new URL(address)).toEither();

    Predicate<Either<Throwable, URL>> filterWithLog = either ->
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
        return Stream.of(
                "https://my-json-server.typicode.com/jabrena/latency-problems/greek",
                "https://my-json-server.typicode.com/jabrena/latency-problems/nordic",
                "https://my-json-server.typicode.com/jabrena/latency-problems/roman")
                .map(toURL)
                .filter(filterWithLog)
                .map(Either::get)
                .map(SimpleCurl::fetch)
                .flatMap(serialize)
                .filter(goodStartingByn)
                .map(toDigits.andThen(concatDigits).andThen(BigInteger::new))
                .reduce(BigInteger.ZERO, (l1, l2) -> l1.add(l2));
    }
}
