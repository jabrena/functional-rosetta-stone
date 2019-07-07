package org.fundamentals.fp.euler;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.vavr.Function3;
import io.vavr.collection.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.apache.commons.lang3.NotImplementedException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * https://projecteuler.net/problem=4
 * Largest palindrome product
 * Problem 4
 *
 * A palindromic number reads the same both ways.
 *
 * The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 × 99.
 *
 * Find the largest palindrome made from the product of two 3-digit numbers.
 *
 */
public class EulerProblem04 implements IEulerType2<Integer, Integer, Long> {

    @Override
    public Long JavaSolution(Integer min, Integer max) {

        throw new NotImplementedException("¯\\_(ツ)_/¯");
    }

    Function<Integer, String> reverse = number -> new StringBuilder().append(number).reverse().toString();

    Predicate<Integer> isPalindrome = value -> reverse.apply(value).equals(value.toString());

    //Kind of Crossproduct
    Function3<Integer, Integer, Integer, Stream<Integer>> multipliedList = (min, max, value) ->
            IntStream.rangeClosed(min, max).boxed()
                .map(element -> element * value);

    @Override
    public Long JavaStreamSolution(Integer min, Integer max) {

        return IntStream.rangeClosed(min, max).boxed()
                .flatMap(l -> multipliedList.apply(min, max, l))
                .filter(isPalindrome)
                .mapToLong(x -> x)
                .max()
                .getAsLong();
    }

    @Override
    public Long VAVRSolution(Integer min, Integer max) {

        return List.rangeClosed(min, max)
                .crossProduct()
                .filter(t -> t._1 <= t._2)
                .map(t -> t._1 * t._2)
                .filter(isPalindrome)
                .max()
                .get()
                .longValue();
    }

    @Override
    public Mono<Long> ReactorSolution(Integer min, Integer max) {

        Function<Integer, Flux<Integer>> crossProduct = element ->
            Flux.range(min, max - min + 1)
                    .map(element2 -> element * element2);

        return Flux.range(min, max - min + 1)
                .flatMap(crossProduct)
                .filter(isPalindrome)
                .map(i -> Long.valueOf(i))
                .sort()
                .last();
    }

    @Override
    public Single<Long> RxJavaSolution(Integer min, Integer max) {

        io.reactivex.functions.Function<Integer, Observable<Integer>> crossProduct = element ->
                Observable.range(min, max - min + 1)
                        .map(element2 -> element * element2);

        return Observable.range(min, max - min + 1)
                .flatMap(crossProduct)
                .filter(i -> isPalindrome.test(i))
                .map(i -> Long.valueOf(i))
                .sorted()
                .last(1L);
    }

    @Override
    public Long KotlinSolution(Integer min, Integer max) {

        throw new NotImplementedException("Coming soon");
    }

}
