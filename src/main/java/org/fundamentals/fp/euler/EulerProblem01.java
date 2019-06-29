package org.fundamentals.fp.euler;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.vavr.collection.Stream;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Problem 1: Multiples of 3 and 5
 * https://projecteuler.net/problem=1
 *
 * If we list all the natural numbers below 10 that are multiples of 3 or 5,
 * we get 3, 5, 6 and 9.
 * The sum of these multiples is 23.
 *
 * Find the sum of all the multiples of 3 or 5 below 1000.
 *
 */
@Solved
public class EulerProblem01 implements IEulerType1<Integer, Integer> {

    @Override
    public Integer JavaSolution(Integer limit) {

        int sum = 0;

        for(int counter = 1; counter < limit; counter++) {
            if ((counter % 3 ==0) || (counter % 5 ==0)) {
                sum += counter;
            }
        }

        return sum;
    }

    BiPredicate<Integer, Integer> isMultiple = (l, i) -> l % i == 0;
    Predicate<Integer> isMultiple3 = number -> isMultiple.test(number, 3);
    Predicate<Integer> isMultiple5 = number -> isMultiple.test(number, 5);

    @Override
    public Integer JavaStreamSolution(Integer limit) {

        return IntStream.range(1, limit).boxed()
                .filter(isMultiple3.or(isMultiple5))
                .reduce(0, Integer::sum);
    }

    @Override
    public Integer VAVRSolution(Integer limit) {

         return Stream.range(1, limit)
                 .filter(isMultiple3.or(isMultiple5))
                 .sum()
                 .intValue();
    }

    @Override
    public Mono<Integer> ReactorSolution(Integer limit) {

        return Flux.range(0, limit)
                    .filter(isMultiple3.or(isMultiple5))
                    .reduce(0, (l1, l2) -> l1 + l2);
    }

    @Override
    public Single<Integer> RxJavaSolution(Integer limit) {

        return Observable.range(1, limit - 1)
                .filter(l -> isMultiple3.test(l) || isMultiple5.test(l))
                .reduce(0, (a, b) -> a + b);
    }

    @Override
    public Integer KotlinSolution(Integer limit) {

        return EulerProblem01Kt.KotlinSolution01(limit);
    }

}
