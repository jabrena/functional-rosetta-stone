package org.fundamentals.fp.euler.problem3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

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
public class EulerProblem3 {

    private List<Long> primeFactors(long number) {
        long n = number;
        List<Long> factors = new ArrayList<>();
        for (long i = 2; i <= n; i++) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }
        return factors;
    }

    public long javaSolution(long number) {

        List<Long> factorList = primeFactors(number);
        return factorList.get(factorList.size() -1);
    }

    static private LongStream primeFactorsIntStream(long n) {
        int characteristics = Spliterator.ORDERED | Spliterator.SORTED | Spliterator.IMMUTABLE | Spliterator.NONNULL;
        Spliterator.OfLong spliterator = new Spliterators.AbstractLongSpliterator(Long.MAX_VALUE, characteristics) {
            long val = n;
            int div = 2;

            @Override
            public boolean tryAdvance(LongConsumer action) {
                while (div <= val) {
                    if (val % div == 0) {
                        action.accept(div);
                        val /= div;
                        return true;
                    }
                    div += div == 2 ? 1 : 2;
                }
                return false;
            }

            @Override
            public Comparator<? super Long> getComparator() {
                return null;
            }
        };
        return StreamSupport.longStream(spliterator, false);
    }

    private List<Long> primeFactorsStream(long number) {
        return primeFactorsIntStream(number).boxed().collect(toList());
    }

    public long javaStreamSolution(long number) {

        return primeFactorsStream(number).stream().reduce((first, second) -> second).get();
    }
}
