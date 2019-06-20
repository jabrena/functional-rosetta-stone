package org.fundamentals.fp.euler;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.math.MathFlux;

public class EulerProblem6 {

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
}
