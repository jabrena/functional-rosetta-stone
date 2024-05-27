package info.jab.fp.async;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import static java.util.function.Predicate.not;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(TestLoggerExtension.class)
public class CFExamplesTest {

    private static CFExamples cfExamples;

    @BeforeAll
    public static void setUp() {
        cfExamples = new CFExamples();
    }

    @Test
    public void should_call_singleAsyncTask() {

        //Given
        var expectedComputationTime = (cfExamples.getDelay() * 1) + 1;
        var expectedResult = 1+1;

        //When
        Callable demo = () -> cfExamples.callingSingleAsyncTask().join();

        //Then
        await()
                .atMost(Duration.ofSeconds(expectedComputationTime))
                .until(demo, equalTo(expectedResult));
    }

    @Test
    public void should_return_value() {

        //Given
        var expectedResult = 2;

        //When
        var result = cfExamples.callingSingleTask();

        //Then
        then(result).isEqualTo(expectedResult);
    }

    @Test
    public void should_call_twoAsyncTasks() {

        //Given
        var expectedComputationTime = (cfExamples.getDelay() * 2) + 1;
        var expectedResult = 2 + 1;

        //When
        Callable demo = () -> cfExamples.callingTwoAsyncTasks().join();

        //Then
        await()
                .atMost(Duration.ofSeconds(expectedComputationTime))
                .until(demo, equalTo(expectedResult));
    }

    @Test
    public void should_call_threeAsyncTasks() {

        //Given
        var expectedComputationTime = (cfExamples.getDelay() * 3) + 1;
        var expectedResult = 2 + 1 + 1;

        //When
        Callable demo = () -> cfExamples.callingThreeAsyncTasks().join();

        //Then
        await()
                .atMost(Duration.ofSeconds(expectedComputationTime))
                .until(demo, equalTo(expectedResult));
    }

    @Test
    public void should_call_fourAsyncTasks() {

        //Given
        var expectedComputationTime = (cfExamples.getDelay() * 4) + 1;
        var expectedResult = 2 + 2 + 2 + 2;

        //When
        Callable demo = () -> cfExamples.callingFourAsyncTasks();

        //Then
        await()
                .atMost(Duration.ofSeconds(expectedComputationTime))
                .until(demo, equalTo(expectedResult));
    }

    @Test
    public void given_CF4_when_Call_then_returnExpectedValue() {

        Callable demo = () -> cfExamples.myForthCF();

        await()
                .atMost(Duration.ofSeconds(7))
                .until(demo, equalTo(4));
    }

    @Disabled
    @Test
    public void given_CF5_when_Call_then_returnExpectedValue() throws Exception {

        then(cfExamples.myFifthCF()).isEqualTo(2);
    }

    @Test
    public void given_CF6_when_Call_then_returnExpectedValue() {


        Callable demo = () -> cfExamples.mySixthCF();

        await()
                .atMost(Duration.ofSeconds(7))
                .until(demo, equalTo(102));
    }

    @Test
    public void given_CF7_when_Call_then_returnExpectedValue() {

        Callable demo = () -> cfExamples.mySeventhCF();

        await()
            .atMost(Duration.ofSeconds(7))
            .until(demo, equalTo(0));

        //then(example.mySeventhCF()).isEqualTo(0);
    }

    @Disabled
    @Test
    public void testWithErrorHandledInStream() {

        CompletableFuture<Optional<String>> future1 = CompletableFuture.supplyAsync(() -> Optional.of("1"));
        CompletableFuture<Optional<String>> futureEx = CompletableFuture.supplyAsync(() -> Optional.of((2/0)+""));
        CompletableFuture<Optional<String>> future2 = CompletableFuture.supplyAsync(() -> Optional.of("2"));
        CompletableFuture<Optional<String>> future3 = CompletableFuture.supplyAsync(() -> Optional.of("3"));
        String output = Stream.of(future1, futureEx, future2, future3)
                //.filter(x -> !x.isCompletedExceptionally())
                .filter(not(CompletableFuture::isCompletedExceptionally))
                .map(CompletableFuture::join)
                .map(x -> x.get())
                .collect(Collectors.joining(" "));

        then(output).isEqualTo("1 2 3");
    }
}