package info.jab.fp.async;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import static java.util.function.Predicate.not;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * References
 * https://www.nurkiewicz.com/2013/05/java-8-definitive-guide-to.html
 * http://millross-consultants.com/completable-future-error-propagation.html
 *
 */
public class CFExamples {

    private static final Logger logger = LoggerFactory.getLogger(CFExamples.class);

    private final int DELAY_TIME = 2;

    public int getDelay() {
        return DELAY_TIME;
    }

    private void delay(Integer seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) { }
    }

    /** 
     * Simulates a computation with a delay.
     */
    Function<Integer, Integer> compute = param -> {
        logger.info("Compute {}", param);
        delay(DELAY_TIME);
        return param + 1;
    };

    /** 
     * Provide a way to execute compute in an async way. 
     */
    CompletableFuture<Integer> asyncCall(Integer param) {
        logger.info("Receiving {}", param);
        CompletableFuture<Integer> cf1 = CompletableFuture
                .supplyAsync(() -> this.compute.apply(param));
        return cf1;
    }

    public CompletableFuture<Integer> callingSingleAsyncTask() {
        return asyncCall(1);
    }

    public Integer callingSingleTask() {
        return asyncCall(1).join();
    }

    public CompletableFuture<Integer> callingTwoAsyncTasks() {
        int initialValue = 1;
        CompletableFuture<Integer> result = asyncCall(initialValue)
                .thenCompose(i -> asyncCall(i));
        return result;
    }

    public CompletableFuture<Integer> callingThreeAsyncTasks() {
        int initialValue = 1;
        CompletableFuture<Integer> result = asyncCall(initialValue)
                .thenCompose(i -> asyncCall(i))
                .thenCompose(i -> asyncCall(i));

        return result;
    }

    public Integer callingFourAsyncTasks() {

        CompletableFuture<Integer> request1 = asyncCall(1);
        CompletableFuture<Integer> request2 = asyncCall(1);
        CompletableFuture<Integer> request3 = asyncCall(1);
        CompletableFuture<Integer> request4 = asyncCall(1);

        List<CompletableFuture<Integer>> futuresList = List.of(request1, request2, request3, request4);

        return futuresList.stream()
                .map(CompletableFuture::join)
                .reduce(0 , (i1, i2) -> i1 + i2);
    }

    CompletableFuture<Integer> asyncCallFailed() {
        CompletableFuture<Integer> cf1 = CompletableFuture
                .supplyAsync(() -> {
                    throw new RuntimeException("Katakroker");
                });
        return cf1;
    }

    public Integer callingFourAsyncTasksAndOneFails() {

        CompletableFuture<Integer> request1 = asyncCall(1);
        CompletableFuture<Integer> request2 = asyncCallFailed();
        CompletableFuture<Integer> request3 = asyncCall(1);
        CompletableFuture<Integer> request4 = asyncCall(1);

        List<CompletableFuture<Integer>> futuresList = List.of(request1, request2, request3, request4);

        return futuresList.stream()
                .filter(cf -> !cf.isCompletedExceptionally())
                .map(CompletableFuture::join)
                .reduce(0 , (i1, i2) -> i1 + i2);
    }

    public Integer callingFourAsyncTasksAndThreeFails() {

        CompletableFuture<Integer> request1 = asyncCallFailed();
        CompletableFuture<Integer> request2 = asyncCallFailed();
        CompletableFuture<Integer> request3 = asyncCallFailed();
        CompletableFuture<Integer> request4 = asyncCall(1);

        List<CompletableFuture<Integer>> futuresList = List.of(request1, request2, request3, request4);

        return futuresList.stream()
                .filter(not(CompletableFuture::isCompletedExceptionally))
                .map(CompletableFuture::join)
                .reduce(0 , (i1, i2) -> i1 + i2);
    }

    CompletableFuture<Integer> asyncCallFailedWithDefaultResult() {
        CompletableFuture<Integer> cf1 = CompletableFuture
                .supplyAsync(() -> {
                    throw new RuntimeException("Katakroker");
                })
                .handle((result, ex) -> {
                    return 100;
                });
        return cf1;
    }

    public Integer callingFourAsyncTasksWithDefaultValues() {

        CompletableFuture<Integer> request1 = asyncCall(1);
        CompletableFuture<Integer> request2 = asyncCall(1);
        CompletableFuture<Integer> request3 = asyncCallFailed();
        CompletableFuture<Integer> request4 = asyncCallFailedWithDefaultResult();

        List<CompletableFuture<Integer>> futuresList = List.of(request1, request2, request3, request4);

        return futuresList.stream()
                .filter(not(CompletableFuture::isCompletedExceptionally))
                .map(CompletableFuture::join)
                .reduce(0 , (i1, i2) -> i1 + i2);
    }

    CompletableFuture<Optional<Integer>> asyncCallOptional(Integer param) {
        CompletableFuture<Optional<Integer>> cf1 = CompletableFuture
                .supplyAsync(() -> Optional.of(this.compute.apply(param)));
        return cf1;
    }

    Supplier<Integer> katakroker = () -> {
        delay(1);
        throw new RuntimeException("Katakroker");
    };

    CompletableFuture<Optional<Integer>> asyncCallFailedOptional() {
        CompletableFuture<Optional<Integer>> cf1 = CompletableFuture
                .supplyAsync(() -> Optional.of(katakroker.get()));
        return cf1;
    }

    public Integer callingFourAsyncTasksWithOptionals() {

        CompletableFuture<Optional<Integer>> request1 = asyncCallOptional(1);
        CompletableFuture<Optional<Integer>> request2 = asyncCallFailedOptional();
        CompletableFuture<Optional<Integer>> request3 = asyncCallFailedOptional();
        CompletableFuture<Optional<Integer>> request4 = asyncCallFailedOptional();

        List<CompletableFuture<Optional<Integer>>> futuresList = List.of(request1, request2, request3, request4);

        return futuresList.stream()
                .map(cf -> cf.handle((result, ex) -> {
                    if (ex != null) {
                        // Log the exception if needed
                        System.out.println("Exception: " + ex.getMessage());
                        return Optional.<Integer>empty();
                    } else {
                        return result;
                    }
                }))
                .map(CompletableFuture::join)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(0, Integer::sum);
    }
}
