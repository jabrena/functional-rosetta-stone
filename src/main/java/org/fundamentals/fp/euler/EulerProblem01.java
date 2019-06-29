package org.fundamentals.fp.euler;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.vavr.collection.List;
import java.util.function.Predicate;
import java.util.stream.LongStream;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * https://projecteuler.net/problem=1
 *
 * Original:
 *
 * If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9.
 * The sum of these multiples is 23.
 * Find the sum of all the multiples of 3 or 5 below 1000.
 *
 * Scenario 10:
 *
 * Given
 * When
 * If we list all the natural numbers below 10
 * that are multiples of 3 or 5, we get 3, 5, 6 and 9.
 *
 * Then
 * The sum of these multiples is 23.
 *
 * Scenario 1000:
 *
 * Given
 * When
 * Then
 * Find the sum of all the multiples of 3 or 5 below 1000.
 *
 */
@Solved
public class EulerProblem01 implements IEulerType1 {

    @Override
    public long JavaSolution(long limit) {

        long sum = 0;

        for(int counter = 1; counter < limit; counter++) {
            if ((counter % 3 ==0) || (counter % 5 ==0)) {
                sum += counter;
            }
        }

        return sum;
    }

    Predicate<Long> isMultiple3 = number -> number % 3 == 0;
    Predicate<Long> isMultiple5 = number -> number % 5 == 0;

    @Override
    public long JavaStreamSolution(long limit) {

        return LongStream.range(1, limit).boxed()
                .filter(isMultiple3.or(isMultiple5))
                .reduce(0L, Long::sum);
    }

    @Override
    public long VAVRSolution(long limit) {

         return List.range(1, limit)
                 .filter(isMultiple3.or(isMultiple5))
                 .sum()
                 .longValue();
    }

    @Override
    public Mono<Long> ReactorSolution(long limit) {

        return Flux.range(0, (int) limit)
                    .map(x -> Long.valueOf(x))
                    .filter(isMultiple3.or(isMultiple5))
                    .reduce(0l, (l1, l2) -> l1 + l2);
    }

    @Override
    public long KotlinSolution(long limit) {

        return EulerProblem01Kt.KotlinSolution01(limit);
    }

    io.reactivex.functions.Predicate<Long> RxIsMultiple3 = number -> number % 3 == 0;
    io.reactivex.functions.Predicate<Long> RxIsMultiple5 = number -> number % 5 == 0;

    @Override
    public Single<Long> RxJavaSolution(long limit) {

        return Observable.rangeLong(1, limit - 1)
                .filter(l -> RxIsMultiple3.test(l) || RxIsMultiple5.test(l))
                .reduce(0L, (a, b) -> a + b);
    }

}
