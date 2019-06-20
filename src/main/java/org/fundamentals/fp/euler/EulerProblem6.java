package org.fundamentals.fp.euler;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import reactor.core.publisher.Mono;

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

    public Mono<Long> reactorSolution(long l) {
        return Mono.just(0L);
    }
}
