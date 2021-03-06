package org.fundamentals.fp.playground.cf;

import io.vavr.control.Try;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

@Slf4j
public class CFCompositionTest {

    private Integer method1(Integer param) {

        LOGGER.info("Thread: {}", Thread.currentThread().getName());

        delay(3);

        return 1 + param;
    }

    private Integer method2(Integer param) {

        LOGGER.info("Thread: {}", Thread.currentThread().getName());

        delay(1);

        return 1 + param;
    }

    private void delay(int seconds) {
         Try.run(() -> Thread.sleep(seconds * 1000));
    }

    private CompletableFuture<Integer> cf(Integer param)  {

        return new CompletableFuture<>()
                .supplyAsync(() -> 1 + param)
                .handle((result, ex) -> {
                    if(!Objects.isNull(ex)) {
                        LOGGER.info("{}", 99);
                        return 99;
                    }
                    LOGGER.info("{}", result);
                    return result;
                });
    }

    @Test
    public void composeTest() {

        then(this.cf(1)
                .thenCompose(cfResult -> cf(cfResult))
                .thenCompose(cfResult2 -> cf(cfResult2))
                .join())
                .isEqualTo(4);
    }

    Function<Integer, CompletableFuture<Integer>> cf1 = (param) ->  {

        return new CompletableFuture<>()
                .supplyAsync(() -> method1(param))
                .handle((result, ex) -> {
                    if(!Objects.isNull(ex)) {
                        LOGGER.info("{}", 99);
                        return 99;
                    }
                    LOGGER.info("{}", result);
                    return result;
                });
    };

    @Test
    public void composeTest2() {

        then(cf1.apply(1)
                .thenCompose(cfResult -> cf1.apply(cfResult))
                .thenCompose(cfResult2 -> cf1.apply(cfResult2))
                .join())
                .isEqualTo(4);
    }

    @Test
    public void composeTest3() {

        CompletableFuture<Integer> cf2 = new CompletableFuture<>()
                .supplyAsync(() -> 1)
                .handle((result, ex) -> {
                    if(!Objects.isNull(ex)) {
                        LOGGER.info("{}", 99);
                        return 99;
                    }
                    LOGGER.info("{}", result);
                    return result;
                });

        then(cf1.apply(1)
                .thenCompose(cfResult -> cf1.apply(cfResult))
                .thenCompose(cfResult2 -> cf2)
                .join())
                .isEqualTo(1);
    }

}