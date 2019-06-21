package org.fundamentals.fp.euler;

import java.util.stream.LongStream;
import org.apache.commons.lang3.NotImplementedException;

public class EulerProblem10 {

    public Long javaSolution(long limit) {

        throw new NotImplementedException("¯\\_(ツ)_/¯");
    }

    boolean isPrime(long number) {
        return LongStream.rangeClosed(2, number / 2)
                .noneMatch(i -> number % i == 0);
    }

    public long javaStreamSolution(long limit) {
         return LongStream.iterate(1, i -> i + 1)
                .filter(this::isPrime)
                .skip(1)
                .takeWhile(number -> number <= limit)
                .sum();
    }
}
