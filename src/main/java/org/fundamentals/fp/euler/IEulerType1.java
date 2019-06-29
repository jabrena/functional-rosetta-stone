package org.fundamentals.fp.euler;

import io.reactivex.Single;
import reactor.core.publisher.Mono;

public interface IEulerType1 {

    long JavaSolution(long limit);
    long JavaStreamSolution(long limit);
    long VAVRSolution(long limit);
    Mono<Long> ReactorSolution(long limit);
    long KotlinSolution(long limit);
    Single<Long> RxJavaSolution(long limit);

}
