package info.jab.fp.euler;

import io.vavr.Tuple;
import io.vavr.collection.CharSeq;
import io.vavr.collection.Stream;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * Problem 30: Digit fifth powers
 * Surprisingly there are only three numbers that can be
 * written as the sum of fourth powers of their digits:
 *
 * 1634=14 +64 +34 +44
 * 8208=84 +24 +04 +84
 * 9474=94 +44 +74 +44
 *
 * As 1 = 14 is not a sum it is not included.
 *
 * The sum of these numbers is 1634 + 8208 + 9474 = 19316.
 *
 * Find the sum of all the numbers that can be written
 * as the sum of fifth powers of their digits.
 */
public class EulerProblem30 {

    Function<Long, Long> from = limit -> Long.valueOf("1" + IntStream.rangeClosed(1, limit.intValue() -1).boxed()
            .map(i -> "0")
            .collect(Collectors.joining("")));

    Function<Long, Long> to = limit -> Long.valueOf(IntStream.rangeClosed(1, limit.intValue()).boxed()
            .map(i -> "9")
            .collect(Collectors.joining("")));

    Function<Long, List<Long>> toDigits = value -> value.toString().chars()
            .mapToObj(c -> String.valueOf((char) c))
            .map(s -> Long.valueOf(s))
            .collect(Collectors.toList());

    BiPredicate<Long, Long> isTheSame = (limit, l) ->  toDigits.apply(l).stream()
            .map(bi -> Math.round(Math.pow(bi,limit)))
            .reduce((bi1, bi2) -> bi1 + (bi2))
            .get().longValue() == l;

    public long javaStreamSolution(long limit) {

        return LongStream.rangeClosed(from.apply(limit).intValue(), to.apply(limit).intValue()).boxed()
                .filter(l -> isTheSame.test(limit, l))
                .reduce(0l, (l1, l2) -> l1 + l2);
    }

    public long VAVRSolution(long powers) {
        return io.vavr.collection.List.rangeClosed(10, maximalSumForPowers(powers))
                .filter(i -> sumOfPowersOfDigits(powers, i) == i)
                .sum().longValue();
    }

    private long maximalSumForPowers(long powers) {
        return Stream.from(1)
                .map(i -> Tuple.of((long) Math.pow(10, i) - 1, io.vavr.collection.List.fill(i, () -> Math.pow(9, powers)).sum().longValue()))
                .find(t -> t._1 > t._2)
                .map(t -> t._1).get();
    }

    private long sumOfPowersOfDigits(long powers, long num) {
        return CharSeq.of(Long.toString(num))
                .map(c -> Character.digit(c, 10))
                .map(d -> (long) Math.pow(d, powers))
                .sum().longValue();
    }

}
