package org.fundamentals.fp.euler;

import io.reactivex.Single;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.util.stream.Collectors.toList;

/**
 * Problem 20: Factorial digit sum
 * n! means n (n 1) ... 3 2 1
 *
 * For example, 10! = 10   9   ...   3   2   1 = 3628800,
 *
 * and the sum of the digits in the number 10! is
 * 3 + 6 + 2 + 8 + 8 + 0 + 0 = 27.
 *
 * Find the sum o9f the digits in the number 100!
 *
 */
public class EulerProblem20 implements IEulerType1<Long, Long> {

    @Override
    public Long JavaSolution(Long limit) {

        BigDecimal factorial = BigDecimal.valueOf(limit);
        for(long x = limit -1; x > 0; x--) {
            factorial = factorial.multiply(BigDecimal.valueOf(x));
        }

        String strFactorial = factorial.toString();
        long sum = 0L;
        for(int x = 0; x < strFactorial.length(); x++) {
            String digit = String.valueOf((char) strFactorial.charAt(x));
            sum += Long.valueOf(digit);
        }

        return sum;
    }

    Function<Long, BigInteger> factorial = limit -> IntStream.iterate(limit.intValue(), i -> i - 1)
            .limit(limit)
            .mapToObj(BigInteger::valueOf)
            .reduce((n1, n2) -> n1.multiply(n2)).get();

    Function<BigInteger, Long> sumDigits = value -> value.toString().chars()
            .mapToObj(c -> String.valueOf((char) c))
            .mapToLong(Long::valueOf)
            .reduce(0L, Long::sum);

    @Override
    public Long JavaStreamSolution(Long limit) {

        return factorial
                .andThen(sumDigits)
                .apply(limit);
    }

    public Long JavaStreamSolution2(Long limit) {

        return Stream.of(limit)
                .map(factorial)
                .map(sumDigits)
                .findAny()
                .get();
    }

    @Override
    public Long VAVRSolution(Long limit) {
        return null;
    }

    @Override
    public Mono<Long> ReactorSolution(Long limit) {

         return Mono.just(limit)
                 .map(factorial)
                 .map(sumDigits);
    }

    @Override
    public Single<Long> RxJavaSolution(Long limit) {
        return null;
    }

    @Override
    public Long KotlinSolution(Long limit) {
        return null;
    }
}
