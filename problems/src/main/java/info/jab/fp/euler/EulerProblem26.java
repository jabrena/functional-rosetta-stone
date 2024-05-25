package info.jab.fp.euler;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Comparator;
import java.util.stream.LongStream;

/**
 * Problem 26: Reciprocal cycles
 * A unit fraction contains 1 in the numerator.
 * The decimal representation of the unit fractions with denominators 2 to 10 are given:
 *
 * 1/2 = 0.5
 * 1/3 = 0.(3)
 * 1/4 = 0.25
 * 1/5 = 0.2
 * 1/6 = 0.1(6)
 * 1/7 = 0.(142857)
 * 1/8 = 0.125
 * 1/9 = 0.(1)
 * 1/10 = 0.1
 *
 * Where 0.1(6) means 0.166666..., and has a 1-digit recurring cycle.
 * It can be seen that 1/7 has a 6-digit recurring cycle.
 *
 * Find the value of d   1000 for which 1/d contains the longest recurring cycle
 * in its decimal fraction part.
 *
 */
public class EulerProblem26 {

    public long javaStreamSolution(long limit) {
        return Long.parseLong(LongStream.rangeClosed(1, limit).boxed()
                .skip(1)
                .map(l -> new BigDecimal(l))
                .map(bd -> BigDecimal.ONE.divide(bd, MathContext.DECIMAL64))
                .map(bd -> bd.remainder(BigDecimal.ONE).toString())
                .sorted(Comparator.comparing(String::length))
                .peek(System.out::println)
                .findFirst()
                .get());
    }
}
