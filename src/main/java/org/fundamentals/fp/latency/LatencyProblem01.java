package org.fundamentals.fp.latency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Single;
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
import lombok.extern.slf4j.Slf4j;;
import org.fundamentals.fp.euler.IEulerType1;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

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
public class LatencyProblem01 implements IEulerType1<List<String>, BigInteger> {

    private ExecutorService executor;
    private int TIMEOUT;

    public LatencyProblem01(ExecutorService executor, int timeout) {
        this.executor = executor;
        this.TIMEOUT = timeout;
    }

    @Override
    public BigInteger JavaSolution(List<String> listOfGods) {
        return null;
    }

    Function<String, URL> toURL = address -> Try.of(() ->
            new URL(address)).getOrElseThrow(ex -> {
        LOGGER.error(ex.getLocalizedMessage(), ex);
        throw new RuntimeException("Bad address", ex);
    });

    Function<String, Stream<String>> serialize = param -> Try.of(() -> {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> deserializedData = objectMapper.readValue(param, new TypeReference<List<String>>() {});
        return deserializedData.stream();
    }).getOrElseThrow(ex -> {
        LOGGER.error("Bad Serialization process", ex);
        throw new RuntimeException(ex);
    });

    Predicate<String> godStartingByn = s -> s.toLowerCase().charAt(0) == 'n';

    Function<String, List<Integer>> toDigits = s -> s.chars()
            .mapToObj(is -> Integer.valueOf(is))
            .collect(Collectors.toList());

    Function<List<Integer>, String> concatDigits = li -> li.stream()
            .map(String::valueOf)
            .collect(Collectors.joining( "" ));

    Consumer<String> print = LOGGER::info;

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

    Function<List<String>, Stream<String>> fetchListAsync = s -> {
        List<CompletableFuture<String>> futureRequests = s.stream()
                .map(toURL.andThen(fetchAsync))
                .collect(toList());

        return futureRequests.stream()
                .map(CompletableFuture::join)
                .flatMap(serialize);
    };

    Function<Stream<String>, Stream<String>> filterGods = ls -> ls
            .filter(godStartingByn)
            .peek(print);

    Function<Stream<String>, BigInteger> sum = ls -> ls
            .map(toDigits.andThen(concatDigits).andThen(BigInteger::new))
            .reduce(BigInteger.ZERO, (l1, l2) -> l1.add(l2));

    @Override
    public BigInteger JavaStreamSolution(List<String> listOfGods) {

        return fetchListAsync
                .andThen(filterGods)
                .andThen(sum)
                .apply(listOfGods);
    }

    @Override
    public BigInteger VAVRSolution(List<String> listOfGods) {
        return null;
    }

    private Scheduler scheduler = Schedulers.newElastic("myThreads");

    @Override
    public Mono<BigInteger> ReactorSolution(List<String> listOfGods) {

        Flux.fromStream(fetchListAsync.apply(List.of(listOfGods.get(0))))
                .log();

        //Flux.fromStream(fetchListAsync.apply(List.of(listOfGods.get(0))))
        //        .doOnNext(System.out::println);

                /*
        Flux.zip(
                Flux.fromStream(fetchListAsync.apply(List.of(listOfGods.get(0)))),
                Flux.fromStream(fetchListAsync.apply(List.of(listOfGods.get(1)))),
                (a, b) -> a + b).doOnNext(System.out::println);
*/
        return Mono.just(BigInteger.ZERO);
    }

    @Override
    public Single<BigInteger> RxJavaSolution(List<String> listOfGods) {
        return null;
    }

    @Override
    public BigInteger KotlinSolution(List<String> listOfGods) {
        return null;
    }
}
