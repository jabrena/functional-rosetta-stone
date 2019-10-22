package org.fundamentals.fp.playground.vavr;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.control.Either;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class LiftTest {

    @Test
    public void liftExample1Test() {

        Function2<Integer, Integer, Integer> divide = (a, b) -> a / b;
        Function2<Integer, Integer, Optional<Integer>> safeDivide = (i1, i2) ->
                Function2.lift(divide).apply(i1, i2).toJavaOptional();

        then(safeDivide.apply(1, 0)).isEqualTo(Optional.empty());
        then(safeDivide.apply(4, 2)).isEqualTo(Optional.of(2));
    }

    @Test
    public void liftExample2Test() {

        Function2<Integer, Integer, Integer> sum = (first, second) -> {
            if (first < 0 || second < 0) {
                throw new IllegalArgumentException("Only positive integers are allowed");
            }
            return first + second;
        };
        Function2<Integer, Integer, Optional<Integer>> sumSafe = (i1, i2) ->
                Function2.lift(sum).apply(i1, i2).toJavaOptional();

        then(sumSafe.apply(-1, 2)).isEqualTo(Optional.empty());
        then(sumSafe.apply(1, 2)).isEqualTo(Optional.of(3));
    }

    /*
    @Test
    public void liftExample21Test() {

        Function2<Integer, Integer, Either<String, Integer>> sum = (first, second) -> {
            if (first < 0 || second < 0) {
                Either.left("Bad Argument");
            }
            return Either.right(first + second);
        };
        Function2<Integer, Integer, Optional<Integer>> sumSafe = (i1, i2) ->
                Function2.lift(sum).apply(i1, i2).toJavaOptional();

        then(sumSafe.apply(-1, 2)).isEqualTo(Optional.empty());
        then(sumSafe.apply(1, 2)).isEqualTo(Optional.of(3));
    }
    */

    @Test
    public void liftExample3Test() {

        Function2<Integer, Integer, Integer> sum = (first, second) -> {
            if (first < 0 || second < 0) {
                throw new IllegalArgumentException("Only positive integers are allowed");
            }
            return first + second;
        };
        Function2<Integer, Integer, Optional<Integer>> sumSafe = (i1, i2) ->
                Function2.lift(sum).apply(i1, i2).toJavaOptional();

        long validSumCounter = IntStream.iterate(-5, i -> i + 1)
                .takeWhile(i -> i < 5)
                .mapToObj(i -> sumSafe.apply(i, 1))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .peek(System.out::println)
                .count();

        then(validSumCounter).isEqualTo(5);
    }

}
