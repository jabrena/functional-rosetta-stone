package org.fundamentals.fp.playground.vavr;

import java.util.List;
import java.util.function.Supplier;

public class LazyEager {

    public static void main(String[] args) {

        System.out.println("First");
        Integer eager = List.of(1,2,3).stream()
                .peek(System.out::println)
                .reduce(0, (i1, i2) -> i1 + i2); // eager

        System.out.println("Second");
        Supplier<Integer> lazy = () -> List.of(1,2,3).stream()
                .peek(System.out::println)
                .reduce(0, (i1, i2) -> i1 + i2); // lazy

        System.out.println(eager);
        System.out.println(lazy.get());
    }

}
