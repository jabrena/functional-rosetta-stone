package org.fundamentals.fp.sixth;

import io.vavr.Function1;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import io.vavr.control.Try;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.fundamentals.fp.fifth.WebAddressService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class ParallelStreamProcesingTest {

    private List<URL> getValidAddressList() {

        //In another context, the object is instanced by DI
        WebAddressService obj = new WebAddressService();

        Function1<String, Either<Throwable, URL>> toURL =
                address -> Try.of(() -> new URL(address)).toEither();

        return obj.getRawData().stream()
                .map(toURL)
                .filter(Either::isRight)
                .map(Either::get)
                .collect(toList());
    }

    @Test
    public void getValidAddressListTest() {

        assertThat(this.getValidAddressList().size()).isGreaterThan(0);
    }

    @Disabled
    @Test
    public void fetchAddressInSequenceTest() {

        Consumer<String> print = System.out::println;
        Function1<URL, String> fetch = Function1.of(SimpleCurl::fetch);
        Either<Throwable, String> safeFetch = Try.of(() -> fetch.toString()).toEither();

        long startTime = System.nanoTime();

        this.getValidAddressList().stream()
                .map(fetch)
                //.filter(Either::isRight)
                //.map(Either::get)
                .map(this::getTitle)
                .forEach(print);

        long endTime = (System.nanoTime() - startTime) / 1_000_000;
        LOGGER.info("Process time: {} ms", endTime);
    }

    @Disabled
    @Test
    public void fetchAddressParallelTest() {

        Consumer<String> print = System.out::println;
        Function1<URL, String> fetch = Function1.of(SimpleCurl::fetch);
        Either<Throwable, String> safeFetch = Try.of(() -> fetch.toString()).toEither();

        long startTime = System.nanoTime();

        getValidAddressList().stream()
                .parallel()
                .map(fetch)
                //.map(x -> safeFetch)
                //.filter(Either::isRight)
                //.map(Either::get)
                .map(this::getTitle)
                .forEach(print);

        long endTime = (System.nanoTime() - startTime) / 1_000_000;
        LOGGER.info("Process time: {} ms", endTime);
    }

    @Test
    public void fetchAddressAsyncTest() {

        Consumer<String> print = System.out::println;

        this.getValidAddressList().stream()
            .parallel()
            .map(x -> curlAsync((URL) x))
            .filter(Either::isRight)
            .map(Either::get)
            .map(this::getTitle)
            .forEach(System.out::println);
    }

    private Either<Throwable, String> curlAsync(URL address) {

        ExecutorService executor = Executors.newFixedThreadPool(100);
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> SimpleCurl.fetch(address), executor);

        return Try.of(() -> future.get(5, TimeUnit.SECONDS)).toEither();
    }

    private Tuple2<String, String> curlAsync2(URL address) {

        ExecutorService executor = Executors.newFixedThreadPool(100);
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> SimpleCurl.fetch(address), executor);

        Either<Throwable, String> result = Try.of(() -> future.get(5, TimeUnit.SECONDS)).toEither();
        return Match(result).of(
                Case($Right($()), Tuple.of(address.toString(), result.get())),
                Case($Left($()), Tuple.of(address.toString(), "FETCH_BAD_ERROR"))
        );
    }

    private String getTitle(String html) {

        Pattern p = Pattern.compile("<title>(.*?)</title>");
        Matcher m = p.matcher(html);

        if (m.find()) {
            return m.group(1);
        }else {
            return "No title";
        }
    }

}
