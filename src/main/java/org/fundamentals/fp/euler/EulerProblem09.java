package org.fundamentals.fp.euler;

import io.vavr.Tuple;
import io.vavr.Tuple3;
import io.vavr.collection.List;
import java.util.stream.LongStream;
import org.apache.commons.lang3.NotImplementedException;

/**
 * Problem 9: Special Pythagorean triplet
 * A Pythagorean triplet is a set of three natural numbers,
 *
 * a b c, for which, a2 + b2 = c2
 *
 * For example, 32 + 42 = 9 + 16 = 25 = 52.
 *
 * There exists exactly one Pythagorean triplet for which a + b + c = 1000.
 * Find the product abc.
 *
 */
public class EulerProblem09 {

    public Long JavaSolution(long limit) {

        throw new NotImplementedException("¯\\_(ツ)_/¯");
    }

    public long JavaStreamSolution(long limit) {

        return LongStream.iterate(1, i -> i + 1)
                .mapToObj(i -> new Tuple3<>(i, i, i))
                .filter(t3 -> t3._1 + t3._2 + t3._3 == limit)
                .peek(System.out::println)
                .limit(1)
                .count();
    }

    public int VAVRSolution(long sum) {

        return List.rangeClosed(1, (int) sum)
                .crossProduct()
                .filter(t -> t._1 + t._2 < (int) sum)
                .map(t -> Tuple.of(t._1, t._2, (int) sum - t._1 - t._2))
                .filter(t -> t._1 * t._1 + t._2 * t._2 == t._3 * t._3)
                .map(t -> t._1 * t._2 * t._3)
                .head();
    }
}
