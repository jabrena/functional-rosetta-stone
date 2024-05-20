package org.fundamentals.fp.euler;

import io.reactivex.Single;
import reactor.core.publisher.Mono;

public interface IEulerType2<T, U, Z> {

    Z JavaSolution(T min, U max);
    Z JavaStreamSolution(T min, U max);
    Z VAVRSolution(T min, U max);
    Mono<Z> ReactorSolution(T min, U max);
    Single<Z> RxJavaSolution(T min, U max);

}
