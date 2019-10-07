package org.fundamentals.fp.playground.cf;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Slf4j
public class CFTimeoutTest {

    private Integer method1() {

        LOGGER.info("Thread: {}", Thread.currentThread().getName());

        delay(2);

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
    public void timeoutTest() {

        CompletableFuture<Integer> cf1 = new CompletableFuture<>()
                .supplyAsync(this::method1);

        CompletableFuture<Integer> cf2 = new CompletableFuture<>()
                .supplyAsync(this::method2);

        var futureRequests = List.of(cf1, cf2);

        var list = futureRequests.stream()
                //.map(CompletableFuture::join)
                .map(cf -> {
                    try {
                        return cf.get(1, TimeUnit.SECONDS);
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        LOGGER.warn(e.getLocalizedMessage(), e);
                        return 99;
                    }
                }).collect(Collectors.toList());

        list.stream().forEach(System.out::println);
    }

}