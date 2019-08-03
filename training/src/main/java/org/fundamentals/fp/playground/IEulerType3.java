package org.fundamentals.fp.playground;

import io.reactivex.Single;
import reactor.core.publisher.Mono;

public interface IEulerType3<T> {

    T JavaSolution();
    T JavaStreamSolution();
    T VAVRSolution();
    Mono<T> ReactorSolution();
    Single<T> RxJavaSolution();
    T KotlinSolution();

}
