package info.jab.fp.euler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Problem 21: Amicable numbers
 *
 * Let d(n) be defined as the sum of proper divisors of n
 * (numbers less than n which divide evenly into n).
 *
 * If d(a) = b and d(b) = a, where a b, then a and b are an amicable pair
 * and each of a and b are called amicable numbers.
 *
 * For example, the proper divisors of 220 are
 * 1, 2, 4, 5, 10, 11, 20, 22, 44, 55 and 110; therefore d(220) = 284.
 *
 * The proper divisors of 284 are 1, 2, 4, 71 and 142; so d(284) = 220.
 * Evaluate the sum of all the amicable numbers under 10000.
 *
 */
public class EulerProblem21 {

    Function<Long, List<Long>> getDivisors = number -> {

        List<Long> list = new ArrayList<>();
        list.add(1L);

        for (long i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                list.add(i);
                if (i != number / i) {
                    list.add(number / i);
                }
            }
        }

        return list.stream().sorted().collect(Collectors.toList());
    };

    boolean isAmicable(long number) {

        long value1 = this.getDivisors.apply(number).stream().reduce(0L, Long::sum);
        long value2 = this.getDivisors.apply(value1).stream().reduce(0L, Long::sum);

        if(value2 == number) {
            return true;
        } else {
            return false;
        }
    }

    public long javaStreamSolution(long limit) {
        return LongStream.iterate(1, i -> i + 1)
                .limit(limit)
                .filter(this::isAmicable)
                .sorted()
                .peek(System.out::println)
                .sum();
    }
}
