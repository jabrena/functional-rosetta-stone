package org.fundamentals.fp.euler.problem4;

import io.vavr.Function1;
import io.vavr.collection.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.LongStream;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.math.MathFlux;

import static java.util.stream.Collectors.toList;

public class EulerProblem4 {

    public Long javaSolution(long min, long max) {
        
        return null;
    }

    public Long javaStreamSolution(long min, long max) {

        Function<Long, String> reverse = number -> new StringBuilder().append(number).reverse().toString();
        Predicate<Long> isPalindrome = element -> element.toString().equals(reverse.apply(element));

        Function<Long, java.util.List<Long>> crossProduct = element ->
                LongStream.rangeClosed(min, max)
                    .boxed()
                    .map(element2 -> element * element2)
                    .collect(toList());

        return LongStream.rangeClosed(min,max)
                .boxed()
                .map(crossProduct)
                .flatMap(element -> element.stream())
                .filter(isPalindrome)
                .mapToLong(x -> x)
                .max()
                .getAsLong();
    }


    public Long VAVRSolution(long min, long max) {

        Function1<Long, String> reverse = number -> new StringBuilder().append(number).reverse().toString();
        Predicate<Long> isPalindrome = element -> element.toString().equals(reverse.apply(element));

        return List.rangeClosed(min, max)
                .crossProduct()
                .filter(t -> t._1 <= t._2)
                .map(t -> t._1 * t._2)
                .filter(isPalindrome)
                .max()
                .get();
    }

    public Mono<Long> getReactorSolution(int min, int max) {

        Function<Long, String> reverse = number -> new StringBuilder().append(number).reverse().toString();
        Predicate<Long> isPalindrome = element -> element.toString().equals(reverse.apply(element));

        Function<Long, Flux<Long>> crossProduct = element ->
                Flux.range(min, max - min + 1).map(element2 -> element * element2);

        //TODO Flux.range doesn´t support Long values
        return MathFlux.max(Flux.range(min, max - min + 1)
                .map(element -> Long.valueOf(element))
                .flatMap(crossProduct)
                .filter(isPalindrome)
                //.log()
        );
    }
}
