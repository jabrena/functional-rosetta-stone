package info.jab.fp.concepts;

import java.math.BigInteger;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Problem 20: Factorial digit sum
 * n! means n (n 1) ... 3 2 1
 *
 * For example, 10! = 10   9   ...   3   2   1 = 3628800,
 *
 * and the sum of the digits in the number 10! is
 * 3 + 6 + 2 + 8 + 8 + 0 + 0 = 27.
 *
 * Find the sum of the digits in the number 100!
 * 
 * Source: https://projecteuler.net/problem=20
 */
public class FunctionalCompositionExamples {

    private Function<Long, BigInteger> factorial = limit -> IntStream.iterate(limit.intValue(), i -> i - 1)
            .limit(limit)
            .mapToObj(BigInteger::valueOf)
            .reduce((n1, n2) -> n1.multiply(n2))
            .get();

    private Function<BigInteger, Long> sumDigits = value -> value.toString().chars()
            .mapToObj(c -> String.valueOf((char) c))
            .mapToLong(Long::valueOf)
            .sum();

    Long solution1(Long limit) {

        return factorial
                .andThen(sumDigits)
                .apply(limit);
    }

    Long solution2(Long limit) {

        return Stream.of(limit)
                .map(factorial)
                .map(sumDigits)
                .findAny()
                .get();
    }

    public static void main(String[] args) {
        FunctionalCompositionExamples compositionExamples = new FunctionalCompositionExamples();

        System.out.println("Right result: " + (compositionExamples.solution1(10L).compareTo(27L) == 0));
        System.out.println("Right result: " + (compositionExamples.solution2(10L).compareTo(27L) == 0));

        System.out.println("Right result: " + (compositionExamples.solution1(100L).compareTo(648L) == 0));
        System.out.println("Right result: " + (compositionExamples.solution2(100L).compareTo(648L) == 0));
    }

}
