package org.fundamentals.fp.latency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.control.Option;
import io.vavr.control.Try;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toUnmodifiableList;
import static org.fundamentals.fp.latency.SimpleCurl.fetch;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class LatencyProblem09 {

    private static final Logger LOGGER = LoggerFactory.getLogger(LatencyProblem08.class);

    public static record Config(String address,Executor executor, int timeout) {};

    /*
    @Data
    @AllArgsConstructor
    public static class Config {

        private String address;
        private ExecutorService executor;
        private int timeout;
    }
     */

    private final LatencyProblem09.Config config;

    public LatencyProblem09(org.fundamentals.fp.latency.LatencyProblem09.Config config) {
        this.config = config;
    }

    Function1<String, URL> toURL = address -> Try
            .of(() -> new URL(address))
            .onFailure(ex -> LOGGER.error(ex.getLocalizedMessage(), ex))
            .getOrElseThrow(ex -> new RuntimeException("Bad URL", ex));

    Function1<String, List<String>> serialize = param -> Try
            .of(() -> {
                ObjectMapper objectMapper = new ObjectMapper();
                List<String> deserializedData = objectMapper.readValue(param, new TypeReference<List<String>>() {});
                return deserializedData;
            })
            .onFailure(ex -> LOGGER.error(ex.getLocalizedMessage(), ex))
            .getOrElseThrow(ex -> new RuntimeException("Bad Serialization process", ex));

    Function1<Config, CompletableFuture<Option<List<String>>>> fetchAsync = config ->

            CompletableFuture
                    .supplyAsync(() -> toURL.andThen(fetch).apply(config.address()), config.executor())
                    .orTimeout(config.timeout(), TimeUnit.SECONDS)
                    .thenApply(serialize)
                    .handle((response, ex) -> {
                        if(Objects.isNull(ex)) {
                            return Option.some(response);
                        }
                        LOGGER.warn(ex.getLocalizedMessage(), ex);
                        return Option.none();
                    });

    Function1<Config, Option<List<String>>> fetchAsyncWithMetrics = config -> {

        double startTime = System.currentTimeMillis();

        CompletableFuture<Option<List<String>>> request = fetchAsync.apply(config);

        request.join();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        request.cancel(true);

        /*
        ExecutorService executor = config.getExecutor();
        //executor.
        Future<?> submit = executor.submit(new Runnable() {
            @Override
            public void run() {
                request.join();
            }
        });

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        submit.cancel(true);

        LOGGER.info("cancelled");
        LOGGER.info("{}", submit.isCancelled());


         */

        LOGGER.info("cancelled");
        LOGGER.info("{}", request.isCancelled());

        Option<List<String>> result;
        /*
        = fetchAsync
                .andThen(CompletableFuture::join)
                .apply(config);
         */

        double stopTime = System.currentTimeMillis();
        double elapsedTime = (stopTime - startTime) / 1000d;
        LOGGER.debug("Async execution: {} seconds", elapsedTime);

        result = Option.none();

        return result;
    };

    public Option<List<String>> JavaStreamSolution() {

        final Supplier<Option<List<String>>> supplier = () -> fetchAsyncWithMetrics.apply(config);

        return Try.ofSupplier(supplier)
                .onFailure(ex -> LOGGER.warn(ex.getLocalizedMessage(), ex))
                .recover(ex -> Option.none())
                .get();
    }

}
