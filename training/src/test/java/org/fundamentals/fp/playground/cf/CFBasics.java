package org.fundamentals.fp.playground.cf;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import static java.util.function.Predicate.not;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * References
 * https://www.nurkiewicz.com/2013/05/java-8-definitive-guide-to.html
 * http://millross-consultants.com/completable-future-error-propagation.html
 *
 */
public class CFBasics {

    Function<Integer, Integer> compute = param -> {

        //LOGGER.info("Compute {}", param);

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

    CompletableFuture<Optional<Integer>> asyncCallOptional(Integer param) {
        CompletableFuture<Optional<Integer>> cf1 = CompletableFuture
                .supplyAsync(() -> Optional.of(this.compute.apply(param)));
        return cf1;
    }

    CompletableFuture<Integer> asyncCallFailed() {
        CompletableFuture<Integer> cf1 = CompletableFuture
                .supplyAsync(() -> {
                    throw new RuntimeException("Katakroker");
                });
        return cf1;
    }

    CompletableFuture<Integer> asyncCallFailedProtected() {
        CompletableFuture<Integer> cf1 = CompletableFuture
                .supplyAsync(() -> {
                    throw new RuntimeException("Katakroker");
                })
                .handle((result, ex) -> {
                    return 100;
                });
        return cf1;
    }

    Supplier<Integer> katakroker = () -> {
        Delay(1);
        throw new RuntimeException("Katakroker");
    };

    CompletableFuture<Optional<Integer>> asyncCallFailedOptional() {
        CompletableFuture<Optional<Integer>> cf1 = CompletableFuture
                .supplyAsync(() -> Optional.of(katakroker.get()));
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

    public Integer myForthCF() {

        CompletableFuture<Integer> request1 = asyncCall(1);
        CompletableFuture<Integer> request2 = asyncCallFailed();
        CompletableFuture<Integer> request3 = asyncCall(1);

        List<CompletableFuture<Integer>> futuresList = List.of(request1, request2, request3);

        return futuresList.stream()
                .filter(cf -> !cf.isCompletedExceptionally())
                .map(CompletableFuture::join)
                .reduce(0 , (i1, i2) -> i1 + i2);
    }

    /**
     * If an exception occurs in a stage and we do not do anything
     * to handle that exception then execution to the further stages is abandon.
     *
     * @return
     */
    public Integer myFifthCF() {

        System.out.println("Defining");

        CompletableFuture<Integer> request1 = asyncCallFailed();
        CompletableFuture<Integer> request2 = asyncCallFailed();
        CompletableFuture<Integer> request3 = asyncCallFailed();
        CompletableFuture<Integer> request4 = asyncCall(1);

        List<CompletableFuture<Integer>> futuresList = List.of(request1, request2, request3, request4);

        System.out.println("Running");

        var futuresList2 = Stream.of(request1, request2, request3, request4);


        return futuresList2
                .filter(not(CompletableFuture::isCompletedExceptionally))
                .map(CompletableFuture::join)
                .reduce(0 , (i1, i2) -> i1 + i2);
    }

    public Integer mySixthCF() {

        System.out.println("Defining");

        CompletableFuture<Integer> request1 = asyncCall(1);
        CompletableFuture<Integer> request2 = asyncCallFailed();
        CompletableFuture<Integer> request3 = asyncCallFailedProtected();

        List<CompletableFuture<Integer>> futuresList = List.of(request1, request2, request3);

        System.out.println("Running");

        return futuresList.stream()
                .filter(not(CompletableFuture::isCompletedExceptionally))
                .map(CompletableFuture::join)
                .reduce(0 , (i1, i2) -> i1 + i2);
    }

    public Integer mySeventhCF() {

        System.out.println("Defining");

        CompletableFuture<Optional<Integer>> request1 = asyncCallOptional(1);
        CompletableFuture<Optional<Integer>> request2 = asyncCallFailedOptional();
        CompletableFuture<Optional<Integer>> request3 = asyncCallFailedOptional();
        CompletableFuture<Optional<Integer>> request4 = asyncCallFailedOptional();

        List<CompletableFuture<Optional<Integer>>> futuresList = List.of(request1, request2, request3, request4);

        System.out.println("Running");

        return futuresList.stream()
                .filter(CompletableFuture::isCompletedExceptionally)
                .map(CompletableFuture::join)
                .map(o -> o.get())
                .reduce(0 , (i1, i2) -> i1 + i2);
    }
}
