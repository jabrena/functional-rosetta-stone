package info.jab.fp.euler;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.apache.commons.lang3.NotImplementedException;

/**
 * The sum of the squares of the first ten natural numbers is,
 *
 * 12 + 22 + ... + 102 = 385
 * The square of the sum of the first ten natural numbers is,
 *
 * (1 + 2 + ... + 10)2 = 552 = 3025
 * Hence the difference between the sum of the squares of the first ten natural numbers
 * and the square of the sum is 3025 − 385 = 2640.
 *
 * Find the difference between the sum of the squares of the first one hundred natural numbers
 * and the square of the sum.
 */
public class EulerProblem06 {

    public Long JavaSolution(long limit) {

        throw new NotImplementedException("¯\\_(ツ)_/¯");
    }

    Function<Long, Long> JavaStreamSumSquare = limit -> LongStream.rangeClosed(0, limit)
            .boxed()
            .mapToLong(number -> number * number)
            .sum();

    Function<Long, Long> JavaStreamSquareSum = limit -> Double.valueOf(
            Math.pow(
                    LongStream.rangeClosed(0, limit)
                        .boxed()
                        .mapToLong(x -> x)
                        .sum()
            , 2)).longValue();

    public Long JavaStreamSolution(long limit) {

        return Stream.of(JavaStreamSquareSum, JavaStreamSumSquare)
                    .parallel()
                    .map(fx -> fx.apply(limit))
                    .reduce((f1, f2) -> f1 - f2)
                    .get();

        //return javaStreamsSquareSum.apply(limit) - javaStreamsSumSquare.apply(limit);
    }

}
