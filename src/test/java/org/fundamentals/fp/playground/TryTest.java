package org.fundamentals.fp.playground;

import io.vavr.control.Try;
import org.junit.Test;

import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/*
    Try is a container for a computation which may result in an exception.
    Try wraps a computation so that we don’t have to explicitly take care of exceptions with try-catch blocks.
 */
public class TryTest {

    @Test
    public void givenBadCode_whenTryHandles_thenCorrect1() {
        Try<Integer> result = Try.of(() -> 1 / 0);

        assertTrue(result.isFailure());
    }

    @Test
    public void givenBadCode_whenTryHandles_thenCorrect2() {
        Try<Integer> result = Try.of(() -> 1 / 0);
        int errorSentinel = result.getOrElse(-1);

        assertEquals(-1, errorSentinel);
    }

    @Test(expected = ArithmeticException.class)
    public void givenBadCode_whenTryHandles_thenCorrect3() {
        Try<Integer> result = Try.of(() -> 1 / 0);
        result.getOrElseThrow((Supplier<ArithmeticException>) ArithmeticException::new);
    }
}