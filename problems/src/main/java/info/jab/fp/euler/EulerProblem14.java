package info.jab.fp.euler;

import io.vavr.Function1;
import io.vavr.collection.Stream;
import java.util.stream.LongStream;

public class EulerProblem14 {

    public long javaStreamSolution(long limit) {

        return LongStream.iterate(limit, i -> i - 1)
                .map(number -> {
                    if (number % 2 == 0) {
                        return number / 2;
                    } else {
                        return 3 * number + 1;
                    }
                })
                .takeWhile(number -> number >= 1)
                .peek(System.out::println)
                .count();
    }

    public long VAVRSsolution(long limit) {
        return Stream.from(limit)
                .take((int) limit)
                .maxBy(collatzSequenceLength)
                .get();
    }

    private final static Function1<Long, Long> collatzRecursive = n -> {
        if (n == 1) {
            return 1L;
        } else {
            if (n % 2 == 0) {
                return EulerProblem14.collatzRecursive.apply(n / 2) + 1;
            } else {
                return EulerProblem14.collatzRecursive.apply(3 * n + 1) + 1;
            }
        }
    };

    private final static Function1<Long, Long> collatzSequenceLength = collatzRecursive.memoized();

}
