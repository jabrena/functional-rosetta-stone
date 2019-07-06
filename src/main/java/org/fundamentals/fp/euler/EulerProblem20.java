package org.fundamentals.fp.euler;

import java.util.List;
import java.util.function.Function;

import static org.fundamentals.fp.euler.Utils.JavaStreams.factorialStream;
import static org.fundamentals.fp.euler.Utils.JavaStreams.toDigits;

/**
 * Problem 20: Factorial digit sum
 * n! means n (n 1) ... 3 2 1
 *
 * For example, 10! = 10   9   ...   3   2   1 = 3628800,
 *
 * and the sum of the digits in the number 10! is
 * 3 + 6 + 2 + 8 + 8 + 0 + 0 = 27.
 *
 * Find the sum o9f the digits in the number 100!
 *
 */
public class EulerProblem20 {

    Function<List<Long>, Long> sum = digits -> digits.stream()
            .reduce(0L, Long::sum);

    public long javaStreamSolution(long limit) {

        return factorialStream.andThen(toDigits).andThen(sum).apply(limit);
    }
}
