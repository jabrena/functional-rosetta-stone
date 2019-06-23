package org.fundamentals.fp.euler;

import java.math.BigInteger;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;

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

    private Stream<Pair<BigInteger, Integer>> fibonaccyWithIndex() {
        AtomicInteger index = new AtomicInteger(0);
        return Utils.fibonacciSJavatream()
                .map(l -> Pair.of(l, index.incrementAndGet()));
    }

    public long javaStreamSolution(long limit) {
        return fibonaccyWithIndex()
                .filter(p -> String.valueOf(p.getLeft()).length() == limit)
                .findFirst()
                .get()
                .getRight();
    }

    public long VAVRSolution(long limit) {
        return Utils.fibonacciVAVRStream()
                .zipWithIndex()
                .find(t -> t._1.toString().length() == limit)
                .get()._2;
    }
}
