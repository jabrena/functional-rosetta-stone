package org.fundamentals.fp.euler;

import java.util.stream.LongStream;
import org.apache.commons.lang3.NotImplementedException;

/**
 * By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13,
 * we can see that the 6th prime is 13.
 *
 * What is the 10 001st prime number?
 */
public class EulerProblem07 {

    public Long javaSolution(long limit) {

        throw new NotImplementedException("¯\\_(ツ)_/¯");
    }

    boolean isPrime(long number) {
        return LongStream.rangeClosed(2, number / 2)
                .noneMatch(i -> number % i == 0);
    }

    public long javaStreamsSolution(long limit) {

        return LongStream.iterate(1, i -> i + 1)
                .filter(this::isPrime)
                .skip(limit)
                .mapToObj(x -> Long.valueOf(x))
                .findFirst()
                .get();
    }
}
