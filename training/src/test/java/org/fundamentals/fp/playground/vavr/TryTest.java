package org.fundamentals.fp.playground.vavr;

import io.vavr.Function0;
import io.vavr.Function2;
import io.vavr.control.Try;
import org.junit.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/*
    Try is a container for a computation which may result in an exception.
    Try wraps a computation so that we don’t have to explicitly take care of exceptions with try-catch blocks.
 */
public class TryTest {

    @Test
    public void given_unsafeMethod_when_executeMethod_thenKataKrokerTest() {

        Function2<Integer, Integer, Integer> unsafeDivide = (a, b) -> a / b;
        Try<Integer> result = Try.of(() -> unsafeDivide.apply(1, 0));

        then(result.isFailure()).isTrue();
    }

    @Test
    public void given_unsafeMethod_when_executeMethod_thenSameKataKrokerBehaviourTest() {

        Function2<Integer, Integer, Integer> unsafeDivide = (a, b) -> a / b;
        Try<Integer> result = Try.of(() -> unsafeDivide.apply(1, 0));
        result.getOrElse(-1);

        then(result.isFailure()).isTrue();
    }

    @Test
    public void given_unsafeMethod_when_executeMethod_thenRecoveryBehaviourTest() {

        Function2<Integer, Integer, Integer> unsafeDivide = (a, b) -> a / b;
        Try<Integer> result = Try.of(() -> unsafeDivide.apply(1, 0));
        int errorSentinel = result.getOrElse(-1);

        then(errorSentinel).isEqualTo(-1);
    }

    @Test(expected = ArithmeticException.class)
    public void givenBadCode_whenTryHandles_thenCorrect3() {
        Try<Integer> result = Try.of(() -> 1 / 0);
        result.getOrElseThrow((Supplier<ArithmeticException>) ArithmeticException::new);
    }
}