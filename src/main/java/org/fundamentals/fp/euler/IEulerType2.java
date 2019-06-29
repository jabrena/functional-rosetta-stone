package org.fundamentals.fp.euler;

import io.reactivex.Single;
import reactor.core.publisher.Mono;

public interface IEulerType2 {

    long JavaSolution(long min, long max);
    long JavaStreamSolution(long min, long max);
    long VAVRSolution(long min, long max);
    Mono<Long> ReactorSolution(long min, long max);
    long KotlinSolution(long min, long max);
    Single<Long> RxJavaSolution(long min, long max);

}
