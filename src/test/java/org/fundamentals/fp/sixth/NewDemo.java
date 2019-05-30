package org.fundamentals.fp.sixth;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;

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

    public static void main(String[] args) {

        Consumer<Integer> header = System.out::println;
        Consumer<Integer> multiplicationTable = x -> IntStream.rangeClosed(1, 10).boxed().skip(1).forEach(y -> System.out.println(y * x));
        IntStream.rangeClosed(1, 10).boxed().forEach(header.andThen(multiplicationTable)::accept);

        Consumer<String> print = System.out::println;
        Function<String, String> upppercase = x -> x.toUpperCase();
        Function<String, String> crop = x -> x.substring(Math.round(x.length()/2));
        Supplier<String> uuidSupplier = UUID.randomUUID()::toString;
        print.accept(crop.andThen(upppercase).apply(uuidSupplier.get()));

        Predicate<Integer> isPair = x -> x % 2 == 0;
        IntStream.rangeClosed(1, 10).boxed().filter(isPair).forEach(header);
    }
}