package org.fundamentals.fp.euler;

import io.vavr.collection.Stream;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.apache.commons.lang3.NotImplementedException;

/**
 * Problem 5: Smallest multiple
 * 2520 is the smallest number that can be divided by each of the numbers
 * from 1 to 10 without any remainder.
 *
 * What is the smallest positive number that is evenly divisible
 * by all of the numbers from 1 to 20?
 *
 */
public class EulerProblem05 {

    public long JavaSolution(long limit) {

        throw new NotImplementedException("¯\\_(ツ)_/¯");
    }

    BiPredicate<Long, Long> checkDivisible = (value, limit) ->
            IntStream.rangeClosed(1, limit.intValue()).boxed()
                .map(j -> value % j == 0)
                .reduce(Boolean::logicalAnd).get();

    public long JavaStreamSolution(long limit) {

        return LongStream.rangeClosed(1, Long.MAX_VALUE).boxed()
                .filter(i -> checkDivisible.test(i, limit))
                .findFirst().get();
    }

    public long VAVRSolution(long limit) {

        return Stream.rangeClosed(2, limit)
                .map(PrimeNumbers::factorization)
                .reduce((m1, m2) -> m1.merge(m2, Math::max))
                .foldLeft(1L, (xs, x) -> xs * pow(x._1, x._2));
    }

    long pow(long a, long p) {
        return Stream.rangeClosed(1, p).fold(1L, (xs, x) -> xs * a);
    }

}
