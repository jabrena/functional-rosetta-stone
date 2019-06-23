package org.fundamentals.fp.euler;


import java.util.List;
import java.util.Map;
import java.util.function.LongSupplier;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * Problem 25: 1000­digit Fibonacci number
 * The Fibonacci sequence is defined by the recurrence relation: Fn=Fn 1+Fn 2,whereF1=1andF2=1.
 *
 * Hence the first 12 terms will be:
 *
 * F1 = 1
 * F2 = 1
 * F3 = 2
 * F4 = 3
 * F5 = 5
 * F6 = 8
 * F7 = 13
 * F8 = 21
 * F9 = 34
 * F10 = 55
 * F11 = 89
 * F12 = 144
 * The 12th term, F12, is the first term to contain three digits.
 *
 * What is the first term in the Fibonacci sequence to contain 1000 digits?
 *
 */
public class EulerProblem25 {

    private class FibonacciSupplier implements LongSupplier {

        long current = 1;
        long previous = 0;

        @Override
        public long getAsLong() {
            long result = current;
            current = previous + current;
            previous = result;
            return result;
        }
    }

    private LongStream getJavaStreamFibonaccyTerms() {

        return LongStream.generate(new EulerProblem25.FibonacciSupplier());
    }

    private List<Long> getFibonaccyWithNumberOfDigits(long limit) {
        return getJavaStreamFibonaccyTerms()
                .takeWhile(l -> String.valueOf(l).length() <= limit)
                .mapToObj(l -> Long.valueOf(l))
                .collect(toList());
    }

    private Map<Integer, List<String>> getFibonnacyGroupByDigits(long limit) {
        return getFibonaccyWithNumberOfDigits(limit).stream()
                .map(l -> String.valueOf(l))
                .collect(groupingBy(s -> s.length()));
    }

    public long javaStreamSolution(long limit) {
        return getFibonnacyGroupByDigits(limit)
                .get((int) limit).stream()
                .mapToLong(s -> Long.valueOf(s))
                .findFirst()
                .getAsLong();
    }
}
