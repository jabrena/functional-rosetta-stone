package org.fundamentals.fp.playground.cf;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import org.awaitility.core.ConditionTimeoutException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;

public class CFBasicsTest {

    @Test
    public void given_CF_when_Call_then_returnExpectedValue() {

        CFBasics example = new CFBasics();

        await()
                .atMost(Duration.ofSeconds(3))
                .until(example::myFirstCF, equalTo(2));
    }

    @Test
    public void given_CF_when_Call_then_timeout() {

        CFBasics example = new CFBasics();

        Assertions.assertThrows(ConditionTimeoutException.class, () -> {

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
        ;
    }

    @Test
    public void given_CF4_when_Call_then_returnExpectedValue() {

        CFBasics example = new CFBasics();

        Callable demo = () -> example.myForthCF();

        await()
                .atMost(Duration.ofSeconds(7))
                .until(demo, equalTo(4));
    }

    @Test
    public void given_CF5_when_Call_then_returnExpectedValue() {

        CFBasics example = new CFBasics();

        Callable demo = () -> example.myFifthCF();

        await()
                //.atMost(Duration.ofSeconds(7))
                .until(demo, equalTo(0));
    }

    @Test
    public void given_CF6_when_Call_then_returnExpectedValue() {

        CFBasics example = new CFBasics();

        Callable demo = () -> example.mySixthCF();

        await()
                .atMost(Duration.ofSeconds(7))
                .until(demo, equalTo(102));
    }
}