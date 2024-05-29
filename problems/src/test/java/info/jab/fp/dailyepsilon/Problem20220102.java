///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS org.assertj:assertj-core:3.21.0

package info.jab.fp.dailyepsilon;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class Problem20220102 {

    public static void main(String[] args) {

        var iterations = 10000;

        Predicate<Integer> isPerfect = n -> {
            var sum = IntStream.range(1, n - 1)
                .filter(i -> (n % i == 0) ? true : false)
                .reduce(0, Integer::sum);
            return (sum == n) ? true : false;
        };

        Function<Integer, List<Integer>> extractDivisors = n -> {
            return IntStream.rangeClosed(1, n).boxed()
                .filter(i -> (n % i == 0) ? true : false)
                .toList();
        };

        Function<List<Integer>, BigDecimal> sumReciprocals = list -> {
            var roundingMode = RoundingMode.HALF_UP;

            return list.stream()
                .map(BigDecimal::valueOf)
                .map(bd -> BigDecimal.ONE.divide(bd, roundingMode))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        };

        Stream.iterate(1, i -> i + 1) //Infinite Stream
            .limit(iterations)
            .filter(isPerfect)
            .map(extractDivisors)
            .peek(System.out::println)
            .map(sumReciprocals)
            .forEach(li -> {    
                assertThat(li).isEqualTo(BigDecimal.valueOf(2));
            });
    }
}

