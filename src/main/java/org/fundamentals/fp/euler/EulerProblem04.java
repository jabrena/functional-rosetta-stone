package org.fundamentals.fp.euler;

import io.reactivex.Single;
import io.vavr.collection.List;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.LongStream;
import org.apache.commons.lang3.NotImplementedException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.math.MathFlux;

import static java.util.stream.Collectors.toList;

public class EulerProblem04 implements IEulerType2{

    @Override
    public long JavaSolution(long min, long max) {

        throw new NotImplementedException("¯\\_(ツ)_/¯");
    }

    Function<Long, String> reverse = number -> new StringBuilder().append(number).reverse().toString();

    Predicate<Long> isPalindrome = value -> reverse.apply(value).equals(value.toString());

    Function<Long, java.util.List<Long>> multipliedList(long min, long max) {

        return value -> LongStream.rangeClosed(min, max)
            .boxed()
            .map(element -> element * value)
            .collect(toList());
    }

    @Override
    public long JavaStreamSolution(long min, long max) {

        return LongStream.rangeClosed(min, max)
            .boxed()
            .map(multipliedList(min, max))
            .flatMap(Collection::stream)
            .filter(isPalindrome)
            .mapToLong(x -> x)
            .max()
            .getAsLong();
    }

    @Override
    public long VAVRSolution(long min, long max) {

        return List.rangeClosed(min, max)
            .crossProduct()
            .filter(t -> t._1 <= t._2)
            .map(t -> t._1 * t._2)
            .filter(isPalindrome)
            .max()
            .get();
    }

    @Override
    public Mono<Long> ReactorSolution(long min, long max) {

        //TODO Use better Flux.generate
        Function<Long, Flux<Long>> crossProduct = element ->
            Flux.range((int) min, (int) max - (int) min + 1).map(element2 -> element * element2);

        //TODO Flux.range doesn´t support Long values
        return MathFlux.max(Flux.range((int) min, (int) max - (int) min + 1)
                .map(Long::valueOf)
                .flatMap(crossProduct)
                .filter(isPalindrome)
        );
    }

    @Override
    public long KotlinSolution(long min, long max) {

        throw new NotImplementedException("Coming soon");
    }

    @Override
    public Single<Long> RxJavaSolution(long min, long max) {

        throw new NotImplementedException("Coming soon");
    }

}
