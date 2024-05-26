package info.jab.fp.euler;

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

}
