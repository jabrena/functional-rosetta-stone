package org.fundamentals.fp.playground.java8;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class FunctionalJava8FeaturesTest {

    @FunctionalInterface
    interface Square {
        Integer calculate(Integer x);
    }

    @Test
    public void functionalInterfaceTest() {

        Square power = x -> x * x;
        Consumer<Integer> print = System.out::println;
        IntStream.rangeClosed(1, 10).boxed().map(power::calculate).forEach(print::accept);

    }

    @Test
    public void consumerInterfaceTest() {

        Consumer<Integer> print = System.out::println;
        Consumer<Integer> multiplicationTable = x -> IntStream.rangeClosed(1, 10).boxed().skip(1).forEach(y -> System.out.println(y * x));
        IntStream.rangeClosed(1, 10).boxed().forEach(print.andThen(multiplicationTable)::accept);
    }

    @Test
    public void functionalCompositionWithJavaTest() {

        Function<String, String> upppercase = x -> x.toUpperCase();
        Function<String, String> crop = x -> x.substring(Math.round(x.length()/2));
        Supplier<String> uuidSupplier = UUID.randomUUID()::toString;

        assertThat(crop
                .andThen(upppercase)
                .apply(uuidSupplier.get())).isNotEmpty();
    }

    @Test
    public void predicateInterfaceTest() {

        List<Integer> expectedList = Arrays.asList(2,4,6,8,10);

        Predicate<Integer> isPair = x -> x % 2 == 0;

        assertThat(IntStream.rangeClosed(1, 10).boxed()
                .filter(isPair)
                .collect(Collectors.toList()))
                .isEqualTo(expectedList);
    }


}