package org.fundamentals.fp.euler.problem4;

import io.vavr.collection.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.math.MathFlux;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.toList;

import org.apache.commons.lang3.NotImplementedException;

public class EulerProblem4 {

    private String reverse(Long number) {
        return new StringBuilder().append(number).reverse().toString();
    }

    private boolean isPalindrome(Long value) {
        return value.toString().equals(reverse(value));
    }

    Long javaSolution(long min, long max) {

        throw new NotImplementedException("¯\\_(ツ)_/¯");
    }

    private Function<Long, java.util.List<Long>> multipliedList(long min, long max) {

        return value -> LongStream.rangeClosed(min, max)
            .boxed()
            .map(element -> element * value)
            .collect(toList());
    }

    Long javaStreamSolution(long min, long max) {

        return LongStream.rangeClosed(min, max)
            .boxed()
            .map(multipliedList(min, max))
            .flatMap(Collection::stream)
            .filter(this::isPalindrome)
            .mapToLong(x -> x)
            .max()
            .getAsLong();
    }


    Long VAVRSolution(long min, long max) {

        return List.rangeClosed(min, max)
            .crossProduct()
            .filter(t -> t._1 <= t._2)
            .map(t -> t._1 * t._2)
            .filter(this::isPalindrome)
            .max()
            .get();
    }

    Mono<Long> getReactorSolution(int min, int max) {

        //TODO Use better Flux.generate
        Function<Long, Flux<Long>> crossProduct = element ->
            Flux.range(min, max - min + 1).map(element2 -> element * element2);

        //TODO Flux.range doesn´t support Long values
        return MathFlux.max(Flux.range(min, max - min + 1)
                .map(Long::valueOf)
                .flatMap(crossProduct)
                .filter(this::isPalindrome)
            //.log()
        );
    }
}
