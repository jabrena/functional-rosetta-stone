package org.fundamentals.fp.euler;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.apache.commons.lang3.NotImplementedException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.math.MathFlux;

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
public class EulerProblem6 {

    public Long javaSolution(long limit) {

        throw new NotImplementedException("¯\\_(ツ)_/¯");
    }

    Function<Long, Long> javaStreamsSumSquare = limit -> LongStream.rangeClosed(0, limit)
            .boxed()
            .mapToLong(number -> number * number)
            .sum();

    Function<Long, Long> javaStreamsSquareSum = limit -> Double.valueOf(
            Math.pow(
                    LongStream.rangeClosed(0, limit)
                        .boxed()
                        .mapToLong(x -> x)
                        .sum()
            , 2)).longValue();

    public Long javaStreamsSolution(long limit) {

        return Stream.of(javaStreamsSquareSum, javaStreamsSumSquare)
                    .parallel()
                    .map(fx -> fx.apply(limit))
                    .reduce((f1, f2) -> f1 - f2)
                    .get();

        //return javaStreamsSquareSum.apply(limit) - javaStreamsSumSquare.apply(limit);
    }

    public Long VAVRSolution(long limit) {

        throw new NotImplementedException("VAVR in this problem doesn´t offer a significant advantage in relation to Java");
    }

    private Flux<Long> getSequence(int limit) {
        return Flux.range(1, limit)
                .map(number -> Long.valueOf(number));
    }

    Mono<Long> reactorSumSquare(int limit) {
        return MathFlux.sumLong(getSequence(limit)
                .map(number -> number * number));
    }

    Function<Long, Long> square = number -> Double.valueOf(Math.pow(number, 2)).longValue();

    Mono<Long> reactorSquareSum(int limit) {
        return MathFlux.sumLong(getSequence(limit)).map(square);
    }

    public Mono<Long> reactorSolution(int limit) {
        return Mono.zip(
                reactorSumSquare(limit),
                reactorSquareSum(limit),
                (f1, f2) -> f2 - f1);
    }

    public Long kotlinSolution(long limit) {

        throw new NotImplementedException("Coming soon");
    }

}
