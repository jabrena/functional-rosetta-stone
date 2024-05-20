package org.fundamentals.fp.euler;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import io.reactivex.Single;
import reactor.core.publisher.Mono;

/**
 * Problem 35: Circular primes
 * The number, 197, is called a circular prime because all rotations of the digits:
 * 197, 971, and 719, are themselves prime.
 *
 * There are thirteen such primes below 100: 2, 3, 5, 7, 11, 13, 17, 31, 37, 71, 73, 79, and 97.
 *
 * How many circular primes are there below one million?
 *
 */
public class EulerProblem35 implements IEulerType1<Integer, Integer>  {


    @Override
    public Integer JavaSolution(Integer limit) {
        return null;
    }

    Predicate<Integer> isPrime = number ->
            (boolean) IntStream.rangeClosed(2, number / 2)
                    .noneMatch(i -> number % i == 0);

    Function<Integer, List<Long>> toDigits = value -> value.toString().chars()
            .mapToObj(c -> String.valueOf((char) c))
            .map(s -> Long.valueOf(s))
            .collect(Collectors.toList());

    Predicate<Integer> isCircular = number -> {
        Stream.of(number)
                .map(toDigits)
                //.map(ll -> {
                    //return new CartesianProduct().product(ll);
                //})
                .forEach(System.out::println);
        return true;
    };

    @Override
    public Integer JavaStreamSolution(Integer limit) {

        return (int) IntStream.iterate(limit, i -> i - 1)
                .takeWhile(i -> i > 1)
                .filter(i -> isPrime.test(i))
                .filter(i -> isCircular.test(i))
                .peek(System.out::println)
                .count();
    }

    @Override
    public Integer VAVRSolution(Integer limit) {
        return null;
    }

    @Override
    public Mono<Integer> ReactorSolution(Integer limit) {
        return null;
    }

    @Override
    public Single<Integer> RxJavaSolution(Integer limit) {
        return null;
    }
}
