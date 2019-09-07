package org.fundamentals.fp.playground.cf;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;

@Slf4j
public class CFBasics {

    Function<Integer, Integer> compute = param -> {

        LOGGER.info("Compute {}", param);

        Delay(2);

        return param + 1;
    };

    public Integer myFirstCF() {
        return asyncCall(1).join();
    }

    CompletableFuture<Integer> asyncCall(Integer param) {
        CompletableFuture<Integer> cf1 = CompletableFuture
                .supplyAsync(() -> this.compute.apply(param));
        return cf1;
    }

    private static void Delay(Integer seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public CompletableFuture<Integer> mySecondCF() {
        int initialValue = 1;
        CompletableFuture<Integer> completableFuture = asyncCall(initialValue)
                .thenCompose(i -> asyncCall(i))
                .thenCompose(i -> asyncCall(i));

        return completableFuture;
    }

    public Integer myThirdCF() {

        CompletableFuture<Integer> request1 = asyncCall(1);
        CompletableFuture<Integer> request2 = asyncCall(1);
        CompletableFuture<Integer> request3 = asyncCall(1);

        List<CompletableFuture<Integer>> futuresList = List.of(request1, request2, request3);

        /*
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                futuresList.toArray(new CompletableFuture[futuresList.size()]));

        CompletableFuture<List<Integer>> allFuture = CompletableFuture.allOf(request1, request2)
                //Callback
                .thenApply(_void ->
                        Stream.of(request1, request2)
                                .map(CompletableFuture::join)
                                .collect(toList()));

         */

        return futuresList.stream()
                .map(CompletableFuture::join)
                .reduce(0 , (i1, i2) -> i1 + i2);
    }
}
