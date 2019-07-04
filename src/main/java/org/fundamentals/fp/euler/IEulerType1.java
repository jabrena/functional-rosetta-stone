package org.fundamentals.fp.euler;

import io.reactivex.Single;
import reactor.core.publisher.Mono;

public interface IEulerType1<T, U> {

    U JavaSolution(T limit);
    U JavaStreamSolution(T limit);
    U VAVRSolution(T limit);
    Mono<U> ReactorSolution(T limit);
    Single<U> RxJavaSolution(T limit);
    U KotlinSolution(T limit);

}
