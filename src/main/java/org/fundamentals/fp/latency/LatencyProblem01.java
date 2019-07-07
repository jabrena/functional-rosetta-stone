package org.fundamentals.fp.latency;

import io.vavr.Function1;
import io.vavr.control.Either;
import io.vavr.control.Try;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LatencyProblem01 {

    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    Function1<String, Either<Throwable, URL>> toURL =
            address -> Try.of(() -> new URL(address)).toEither();

    Predicate<Either<Throwable, URL>> filterWithLog = either -> {
        if(either.isLeft()) {
            LOGGER.error(either.getLeft().getLocalizedMessage(), either);
            return false;
        }
        return true;
    };

    public long JavaStreamSolution() {

        Stream.of(
                "https://my-json-server.typicode.com/jabrena/latency-problems/greek",
                "https://my-json-server.typicode.com/jabrena/latency-problems/nordic",
                "https://my-json-server.typicode.com/jabrena/latency-problems/roman")
                .map(toURL)
                .filter(filterWithLog)
                .forEach(System.out::println);

        return 0L;
    }
}
