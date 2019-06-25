package org.fundamentals.fp.euler;

import java.math.BigInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EulerProblem29 {

    BiFunction<Long, Integer, Stream<Long>> square = (limit, i) ->
            IntStream.rangeClosed(2, limit.intValue())
                .boxed()
                .map(l -> Math.round(Math.pow(i, l)));

    Function<Long, Stream<Long>> generateSerie = limit -> IntStream.rangeClosed(2, limit.intValue())
            .boxed()
            .flatMap(i -> square.apply(limit, i))
            .distinct()
            .sorted();

    public long javaStreamSolution(long limit) {

         return generateSerie.apply(limit).count();
    }

    public long VAVRSolution(long limit) {

        int max = (int) limit;
        return io.vavr.collection.Stream.rangeClosed(2, max)
                .map(BigInteger::valueOf)
                .flatMap(a -> io.vavr.collection.Stream.rangeClosed(2, max).map(a::pow))
                .distinct()
                .length();
    }

}
