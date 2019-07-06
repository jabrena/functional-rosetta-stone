package org.fundamentals.fp.euler;

import io.vavr.Function1;
import io.vavr.collection.CharSeq;
import io.vavr.collection.Stream;
import java.math.BigInteger;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * https://projecteuler.net/problem=34
 *
 * Problem 34
 * Digit factorials
 *
 * 145 is a curious number, as 1! + 4! + 5! = 1 + 24 + 120 = 145.
 *
 * Find the sum of all numbers which are equal to the sum of the factorial of their digits.
 *
 * Note: as 1! = 1 and 2! = 2 are not sums they are not included.
 *
 */
public class EulerProblem34 {

    Function<Long, List<Long>> toDigits = value -> value.toString().chars()
            .mapToObj(c -> String.valueOf((char) c))
            .map(s -> Long.valueOf(s))
            .collect(Collectors.toList());

    Predicate<Long> isCurious = l -> toDigits.apply(l).stream()
            .map(ll -> Utils.JavaStreams.factorialStream.apply(ll))
            .reduce(BigInteger.ZERO,(l1, l2) -> l1.add(l2)).longValue() == l;

    public long javaStreamSolution() {
        return LongStream.rangeClosed(3L, 2_540_160L).boxed()
                .filter(isCurious)
                .peek(System.out::println)
                .reduce(0L, (l1, l2) -> l1 + l2);
    }

    public int VAVRSolution() {
        return Stream.rangeClosed(3, 2_540_160) // 9! * 7 = 2 540 160 is a seven digit number, as is 9! * 8, therefor 9! * 7 is the definitive upper limit we have to investigate.
                .filter(i -> i == sumOfDigitFactorial(i))
                .peek(System.out::println)
                .sum().intValue();
    }

    private int sumOfDigitFactorial(int num) {
        return CharSeq.of(Integer.toString(num))
                .map(c -> Character.digit(c, 10))
                .map(MEMOIZED_FACTORIAL)
                .sum().intValue();
    }

    final Function1<Integer, BigInteger> MEMOIZED_FACTORIAL = Function1.of(this::factorial).memoized();

    BigInteger factorial(int n) {
        return Stream.rangeClosed(1, n).map(BigInteger::valueOf).fold(BigInteger.ONE, BigInteger::multiply);
    }
}
