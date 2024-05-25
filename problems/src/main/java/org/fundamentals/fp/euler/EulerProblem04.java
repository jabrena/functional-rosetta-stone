package org.fundamentals.fp.euler;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.lang3.NotImplementedException;

/**
 * Problem 4: Largest palindrome product
 * https://projecteuler.net/problem=4
 *
 * A palindromic number reads the same both ways.
 *
 * The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 × 99.
 *
 * Find the largest palindrome made from the product of two 3-digit numbers.
 *
 */
public class EulerProblem04 implements IEulerType2<Integer, Integer, Integer> {

    @Override
    public Integer JavaSolution(Integer min, Integer max) {

        throw new NotImplementedException("¯\\_(ツ)_/¯");
    }

    Function<Integer, String> reverse = number -> new StringBuilder().append(number).reverse().toString();

    Predicate<Integer> isPalindrome = value -> reverse.apply(value).equals(value.toString());

    //Kind of Crossproduct
    Function3<Integer, Integer, Integer, Stream<Integer>> multipliedList = (min, max, value) ->
            IntStream.rangeClosed(min, max).boxed()
                .map(element -> element * value);

    @Override
    public Integer JavaStreamSolution(Integer min, Integer max) {

        return IntStream.rangeClosed(min, max).boxed()
                .flatMap(l -> multipliedList.apply(min, max, l))
                .filter(isPalindrome)
                .sorted(Comparator.reverseOrder())
                .findFirst()
                .get();

    }

}
