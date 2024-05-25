package org.fundamentals.fp.playground.cf;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toUnmodifiableList;

import static org.assertj.core.api.BDDAssertions.then;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vavr.control.Try;

public class CFTimeoutTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CFTimeoutTest.class);

    private Integer method1() {

        LOGGER.info("Thread: {}", Thread.currentThread().getName());

        delay(3);

        return 1;
    }

    private Integer method2() {

        LOGGER.info("Thread: {}", Thread.currentThread().getName());

        delay(1);

        return 1;
    }

    private void delay(int seconds) {
         Try.run(() -> Thread.sleep(seconds * 1000));
    }

    @Test
    public void timeoutJava8Test() {

        var TIMEOUT = 2;

        CompletableFuture<Integer> cf1 = new CompletableFuture<>()
                .supplyAsync(this::method1);

        CompletableFuture<Integer> cf2 = new CompletableFuture<>()
                .supplyAsync(this::method2);

        var futureRequests = List.of(cf1, cf2);

        var list = futureRequests.stream()
                .map(cf -> {
                    try {
                        return cf.get(TIMEOUT, TimeUnit.SECONDS);
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        LOGGER.warn(e.getLocalizedMessage(), e);
                        return 99;
                    }
                }).collect(toUnmodifiableList());

        then(list).isEqualTo(List.of(99, 1));
    }

    @Test
    public void timeoutJava9Test() {

        var TIMEOUT = 2;

        CompletableFuture<Integer> cf1 = new CompletableFuture<>()
                .supplyAsync(this::method1)
                .orTimeout(TIMEOUT, TimeUnit.SECONDS)
                .handle((result, ex) -> {
                    if(!Objects.isNull(ex)) {
                        return 99;
                    }
                    return result;
                });

        CompletableFuture<Integer> cf2 = new CompletableFuture<>()
                .supplyAsync(this::method2)
                .orTimeout(TIMEOUT, TimeUnit.SECONDS)
                .handle((result, ex) -> {
                    if(!Objects.isNull(ex)) {
                        return 99;
                    }
                    return result;
                });

        var futureRequests = List.of(cf1, cf2);

        var list = futureRequests.stream()
                .map(CompletableFuture::join)
                .collect(toUnmodifiableList());

        then(list).isEqualTo(List.of(99, 1));
    }

    @Test
    public void timeoutJava9Test2() {

        var TIMEOUT = 2;

        Supplier<Integer> fm1 = () -> method1();
        Supplier<Integer> fm2 = () -> method2();
        Function<Supplier<Integer>, CompletableFuture<Integer>> toCF = s ->
                new CompletableFuture<Integer>()
                        .supplyAsync(s)
                        .orTimeout(TIMEOUT, TimeUnit.SECONDS)
                        .handle((result, ex) -> {
                            if(!Objects.isNull(ex)) {
                                return 99;
                            }
                            return result;
                        });

        var futureRequests = List.of(fm1, fm2)
                .stream()
                .map(toCF)
                .collect(Collectors.toUnmodifiableList());

        var list = futureRequests.stream()
                .map(CompletableFuture::join)
                .collect(toUnmodifiableList());

        then(list).isEqualTo(List.of(99, 1));
    }

}