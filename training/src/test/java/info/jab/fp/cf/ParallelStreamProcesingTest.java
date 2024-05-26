package info.jab.fp.cf;

/*
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.stream.Collectors.toList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vavr.Function1;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import io.vavr.control.Try;

public class ParallelStreamProcesingTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParallelStreamProcesingTest.class);

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

    //Example 1: Exist cases where the endpoint doesn´t open a connection and the performance is poor
    @Disabled
    @Test
    public void fetchAddressInSequenceTest() {

        Consumer<String> print = System.out::println;
        Function1<URL, String> fetch = Function1.of(SimpleCurl::fetch);

        this.getValidAddressList().stream()
                .map(fetch)
                .map(this::getTitle)
                .forEach(print);
    }

    //Example 2: In this example, we are blocking the thread and it is not a good practice.
    @Test
    public void fetchAddressAsyncTest() {

        Consumer<String> print = System.out::println;

        this.getValidAddressList().stream()
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

    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    @Test
    public void fetchAddressAsync3Test() {

        LOGGER.info("Thread: {}", Thread.currentThread().getName());

        Consumer<Tuple2<URL, String>> print = System.out::println;

        List<Tuple2<URL, String>> result = this.getValidAddressList().stream()
                .map(this::curlAsync4)
                .map(CompletableFuture::join)
                .peek(print)
                .collect(toList());

        assertThat(result.size()).isEqualTo(4);
    }

    @Test
    public void fetchAddressAsync4Test() throws Exception {

        LOGGER.info("Thread: {}", Thread.currentThread().getName());

        Consumer<Tuple2<URL, String>> print = System.out::println;

        List<CompletableFuture<Tuple2<URL, String>>> futureRequests = this.getValidAddressList().stream()
                .map(x -> curlAsync4(x))
                .collect(toList());

        List<Tuple2<URL, String>> result2 = futureRequests.stream()
                .map(CompletableFuture::join)
                .peek(print)
                .collect(toList());

        assertThat(result2.size()).isEqualTo(4);
    }

    private CompletableFuture<Tuple2<URL,String>> curlAsync4(URL address) {

        LOGGER.info("Thread: {}", Thread.currentThread().getName());
        CompletableFuture<Tuple2<URL,String>> future = CompletableFuture
                .supplyAsync(() -> fetchWrapper(address), executor)
                .exceptionally(ex -> {
                    LOGGER.error(ex.getLocalizedMessage(), ex);
                    return Tuple.of(address, "FETCH_BAD_RESULT");
                })
                .completeOnTimeout(Tuple.of(address, "FETCH_BAD_RESULT"),5, TimeUnit.SECONDS);

        return future;
    }

    private Tuple2<URL, String> fetchWrapper(URL address) {
        return Tuple.of(address, getTitle(SimpleCurl.fetch(address)));
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
 */
