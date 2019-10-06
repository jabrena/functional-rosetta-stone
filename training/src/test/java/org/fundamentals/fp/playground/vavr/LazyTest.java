package org.fundamentals.fp.playground.vavr;

import io.vavr.Lazy;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.Test;

import static org.assertj.core.api.BDDAssertions.then;

/**
 *
 *   Lazy is a container which represents a value computed lazily
 *   i.e. computation is deferred until the result is required.
 *
 */
public class LazyTest {

    @Test
    public void given_JavaLazy_when_executeMultipleTimes_then_processExecutedTest() {

        System.out.println("First");
        List<Integer> list = List.of(1,2,3);
        Consumer<Integer> print = System.out::println;
        Integer eager = list.stream()
                .peek(print)
                .reduce(0, (i1, i2) -> i1 + i2); // eager

        System.out.println("Second");
        Supplier<Integer> lazy = () -> list.stream()
                .peek(print)
                .reduce(0, (i1, i2) -> i1 + i2); // lazy

        then(eager).isEqualTo(lazy.get());
    }

    @Test
    public void given_Lazy_when_executeMultipleTimes_then_memoizedResultTest() {

        Lazy<Double> lazy = Lazy.of(Math::random);
        then(lazy.isEvaluated()).isFalse();

        double val1 = lazy.get();
        then(lazy.isEvaluated()).isTrue();

        double val2 = lazy.get();
        then(val1).isEqualTo(val2);
    }
}