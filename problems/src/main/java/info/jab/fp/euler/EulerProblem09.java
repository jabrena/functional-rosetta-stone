package info.jab.fp.euler;

import java.util.stream.LongStream;

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

    public long JavaStreamSolution(long limit) {

        record Tuple3(Long param1, Long param2, Long param3) {}

        return LongStream.iterate(1, i -> i + 1)
                .mapToObj(i -> new Tuple3(i, i, i))
                .filter(t3 -> t3.param1() + t3.param2() + t3.param3() == limit)
                .peek(System.out::println)
                .limit(1)
                .count();
    }
}
