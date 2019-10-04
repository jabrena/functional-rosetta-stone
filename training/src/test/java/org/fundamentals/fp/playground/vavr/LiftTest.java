package org.fundamentals.fp.playground.vavr;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.control.Option;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class LiftTest {

    @Test
    public void liftExample1Test() {

        Function2<Integer, Integer, Integer> divide = (a, b) -> a / b;
        Function2<Integer, Integer, Option<Integer>> safeDivide = Function2.lift(divide);

        Option<Integer> d1 = safeDivide.apply(1, 0);
        Option<Integer> d2 = safeDivide.apply(4, 2);

        then(d1).isEqualTo(Option.none());
        then(d2).isEqualTo(Option.some(2));
    }

    @Test
    public void liftExample2Test() {

        Function2<Integer, Integer, Integer> sum = (first, second) -> {
            if (first < 0 || second < 0) {
                throw new IllegalArgumentException("Only positive integers are allowed");
            }
            return first + second;
        };
        Function2<Integer, Integer, Option<Integer>> sumSafe = Function2.lift(sum);

        Option<Integer> s1 = sumSafe.apply(-1, 2);
        Option<Integer> s2 = sumSafe.apply(1, 2);

        then(s1).isEqualTo(Option.none());
        then(s2).isEqualTo(Option.some(3));
    }

    @Test
    public void liftExample3Test() {

        Function2<Integer, Integer, Integer> sum = (first, second) -> {
            if (first < 0 || second < 0) {
                throw new IllegalArgumentException("Only positive integers are allowed");
            }
            return first + second;
        };
        Function2<Integer, Integer, Option<Integer>> sumSafe = Function2.lift(sum);
        Function1<Integer, Option<Integer>> sumCurried = sumSafe.curried().apply(1);

        long validSumCounter = IntStream.iterate(-5, i -> i + 1)
                .takeWhile(i -> i < 10)
                .mapToObj(i -> sumSafe.apply(i, 1))
                //.mapToObj(i -> sumCurried.apply(i))
                .filter(Option::isDefined)
                .peek(System.out::println)
                .count();

        then(validSumCounter).isEqualTo(10);
    }

}
