package org.fundamentals.fp.playground.cf;

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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class CFBasicsTest {

    @Disabled
    @Test
    public void given_CF_when_Call_then_returnExpectedValue() {

        CFBasics example = new CFBasics();

        await()
                .atMost(Duration.ofSeconds(3))
                .until(example::myFirstCF, equalTo(5));
    }

    @Disabled
    @Test
    public void given_CF_when_Call_then_timeout() {

        CFBasics example = new CFBasics();

        //TODO Review
        Assertions.assertThrows(NoSuchMethodError.class, () -> {

            //False positive
            await()
                    .atLeast(Duration.ofSeconds(2))
                    .atMost(Duration.ofSeconds(2))
                    .until(example::myFirstCF, equalTo(2));
        });
    }

    @Disabled
    @Test
    public void given_CF_when_Call2_then_returnExpectedValue() {

        CFBasics example = new CFBasics();

        await()
                .atMost(Duration.ofSeconds(3))
                .until(example::myFirstCF, equalTo(3));
    }

    @Test
    public void given_CF2_when_Call_then_returnExpectedValue() {

        CFBasics example = new CFBasics();

        Callable demo = () -> example.mySecondCF().join();

        await()
                .atMost(Duration.ofSeconds(7))
                .until(demo, equalTo(4));
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