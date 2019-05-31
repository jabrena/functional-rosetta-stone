package org.fundamentals.fp.sixth;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.Function1;
import io.vavr.control.Either;
import io.vavr.control.Try;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.fundamentals.fp.fifth.WebAddressService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class NewDemo {

    private List<String> loadData() {

        return Try.of(() -> {
            ObjectMapper objectMapper = new ObjectMapper();
            String readContent = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                    .getResource("data/greekGods.json")
                    .toURI())));

            List<String> deserializedData = objectMapper.readValue(readContent, new TypeReference<List<String>>() {});
            return deserializedData;
        }).getOrElseThrow(ex -> {
            LOGGER.error(ex.getLocalizedMessage(), ex);
            throw new IllegalArgumentException("It was impossible to load the data");
        });

    }

    @FunctionalInterface
    interface Square {
        Integer calculate(Integer x);
    }

    @Test
    public void functionalInterfacesTest() {

        NewDemo obj = new NewDemo();
        obj.java8Features();
        obj.vavrFeatures();

    }

    private void java8Features() {

        //TODO Generics ?
        Consumer<String> separate = System.out::println;
        Consumer<Integer> header = System.out::println;

        Square power = x -> x * x;
        IntStream.rangeClosed(1, 10).boxed().map(power::calculate).forEach(header::accept);

        separate.accept("");

        Consumer<Integer> multiplicationTable = x -> IntStream.rangeClosed(1, 10).boxed().skip(1).forEach(y -> System.out.println(y * x));
        IntStream.rangeClosed(1, 10).boxed().forEach(header.andThen(multiplicationTable)::accept);

        separate.accept("");

        Consumer<String> print = System.out::println;
        Function<String, String> upppercase = x -> x.toUpperCase();
        Function<String, String> crop = x -> x.substring(Math.round(x.length()/2));
        Supplier<String> uuidSupplier = UUID.randomUUID()::toString;
        print.accept(crop.andThen(upppercase).apply(uuidSupplier.get()));

        separate.accept("");

        Predicate<Integer> isPair = x -> x % 2 == 0;
        IntStream.rangeClosed(1, 10).boxed().filter(isPair).forEach(header);
    }

    private void vavrFeatures() {

        Function1<String, String> toUpper = String::toUpperCase;
        Function1<String, String> trim = String::trim;
        Function1<String, String> cheers = (s) -> String.format("Hello %s", s);

        assertThat(trim
                .andThen(toUpper)
                .andThen(cheers)
                .apply("   john")).isEqualTo("Hello JOHN");


        NewDemo obj = new NewDemo();
        Function1<String, Either> toURL = obj::getURL;


        Function1<String, Either> toURL2 = (item) -> Try.of(() -> new URL(item)).toEither();

        WebAddressService webAddressService = new WebAddressService();
        webAddressService.getRawData()
                //Stream 1
                .stream()
                .filter(x -> !x.equals("http://www.pivotal.com"))
                .map(toURL2)
                .filter(Either::isRight)
                .map(Either::get)
                .collect(Collectors.toList())

                //Stream 2
                .stream()
                //.parallel()
                .map(x -> curlAsync((URL) x))
                .filter(x -> x.isRight())
                .map(Either::get)
                //.map(x -> getTitle)
                //.peek(System.out::println)
                .forEach(System.out::println);
                //.collect(Collectors.toList());
    }

    private Either<Throwable, String> curlAsync(URL address) {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> callURL(address));

        return Try.of(() -> future.get()).toEither();
    }

    private Either<Throwable, URL> getURL(String address) {

        return Try.of(() -> new URL(address)).toEither();

    }

    private String callURL(URL myURL) {
        System.out.println("Requeted URL:" + myURL);
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn = null;
        InputStreamReader in = null;
        try {
            URL url = myURL;
            urlConn = url.openConnection();
            if (urlConn != null)
                urlConn.setReadTimeout(5 * 1000);
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null) {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("Exception while calling URL:"+ myURL, e);
        }

        return sb.toString();
    }
}