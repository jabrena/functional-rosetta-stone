package info.jab.fp.euler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static java.util.stream.Collectors.toList;
import java.util.stream.LongStream;

/**
 * https://projecteuler.net/problem=3
 *
 * Original:
 *
 * The prime factors of 13195 are 5, 7, 13 and 29.
 *
 * What is the largest prime factor of the number 600851475143 ?
 *
 * Scenario 13195
 *
 * Given primeFactor
 * When 13195
 * Then 29 [5, 7, 13, 29]
 *
 * Scenario 600851475143
 *
 * Given primeFactor
 * When 600851475143
 * Then ?
 */
public class EulerProblem03 implements IEulerType1<Long, Long> {

    private List<Long> primeFactors(long limit) {

        List<Long> listOfFactors = new ArrayList<>();
        for (long i = 2; i <= limit; i++) {
            while (limit % i == 0) {
                listOfFactors.add(i);
                limit /= i;
            }
        }
        return listOfFactors;
    }

    @Override
    public Long JavaSolution(Long number) {

        List<Long> factorList = primeFactors(number);
        return primeFactors(number).get(factorList.size() -1);
    }

    LongStream factors(long lastFactor, long num) {

        return LongStream.rangeClosed(lastFactor, (long) Math.sqrt(num))
                .filter(x -> num % x == 0)
                .mapToObj(x -> LongStream.concat(LongStream.of(x), factors(x, num / x)))
                .findFirst()
                .orElse(LongStream.of(num));
    }

    @Override
    public Long JavaStreamSolution(Long limit) {

        return factors(2, limit)
                .mapToObj(l -> Long.valueOf(l))
                .collect(toList()).stream()
                .sorted(Comparator.reverseOrder()).findFirst()
                .orElseThrow(() -> new RuntimeException("¯\\_(ツ)_/¯"));
    }

}
