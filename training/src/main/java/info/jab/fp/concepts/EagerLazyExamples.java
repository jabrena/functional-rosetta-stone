package info.jab.fp.concepts;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class EagerLazyExamples {

    public static void main(String[] args) {

        List<Integer> list = List.of(1,2,3);
        Consumer<Integer> print = System.out::println;

        System.out.println("First");
        Supplier<Integer> lazy = () -> list.stream()
            .peek(print)
            .reduce(0, (i1, i2) -> i1 + i2); // lazy

        System.out.println("Second");
        Integer eager = list.stream()
            .peek(print)
            .reduce(0, (i1, i2) -> i1 + i2); // eager

        System.out.println("Assert");
        System.out.println("Result: " + eager.equals(lazy.get()));
    }
}
