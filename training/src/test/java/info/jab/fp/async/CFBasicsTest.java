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

public class CFBasicsTest {

    private static CFBasics example;

    @BeforeAll
    public static void setUp() {
        example = new CFBasics();
    }

    @Test
    public void should_myFirstCF_example() {

        //Given
        var expectedResult = 2;

        //When
        var result = example.myFirstCF();

        //Then
        then(result).isEqualTo(expectedResult);
    }

    @Test
    public void given_CF2_when_Call_then_returnExpectedValue() {

        //Given
        var expectedComputationTime = (example.getDelay() * 3) + 1;
        var expectedResult = 4;

        //When
        Callable demo = () -> example.mySecondCF().join();

        //Then
        await()
                .atMost(Duration.ofSeconds(expectedComputationTime))
                .until(demo, equalTo(expectedResult));
    }

    @Test
    public void given_CF3_when_Call_then_returnExpectedValue() {

        CFBasics example = new CFBasics();

        Callable demo = () -> example.myThirdCF();

        await()
                .atMost(Duration.ofSeconds(7))
                .until(demo, equalTo(6));
    }

    @Test
    public void given_CF4_when_Call_then_returnExpectedValue() {

        CFBasics example = new CFBasics();

        Callable demo = () -> example.myForthCF();

        await()
                .atMost(Duration.ofSeconds(7))
                .until(demo, equalTo(4));
    }

    @Disabled
    @Test
    public void given_CF5_when_Call_then_returnExpectedValue() throws Exception {

        CFBasics example = new CFBasics();

        then(example.myFifthCF()).isEqualTo(2);
    }

    @Test
    public void given_CF6_when_Call_then_returnExpectedValue() {

        CFBasics example = new CFBasics();

        Callable demo = () -> example.mySixthCF();

        await()
                .atMost(Duration.ofSeconds(7))
                .until(demo, equalTo(102));
    }

    @Test
    public void given_CF7_when_Call_then_returnExpectedValue() {

        CFBasics example = new CFBasics();

        Callable demo = () -> example.mySeventhCF();

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