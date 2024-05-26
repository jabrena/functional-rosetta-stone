package info.jab.fp.euler;

import java.math.BigInteger;

/**
 * Problem 16: Power digit sum
 *
 * 215 = 32768 and the sum of its digits is 3 + 2 + 7 + 6 + 8 = 26.
 *
 * What is the sum of the digits of the number 21000?
 */
public class EulerProblem16 {

    public long javaStreamSolution(long power) {

        return BigInteger.valueOf(2).pow((int) power).toString().chars()
                .mapToObj(c -> String.valueOf((char) c))
                .mapToLong(s -> Long.valueOf(s))
                .sum();
    }
}
