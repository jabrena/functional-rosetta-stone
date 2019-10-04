package org.fundamentals.fp.playground.vavr;

import org.junit.Test;

import static io.vavr.API.*;
import static io.vavr.Predicates.isIn;
import static junit.framework.TestCase.assertEquals;

public class PatternMatchingTest {

    @Test
    public void whenMatchworks_thenCorrect_1() {
        int input = 2;

        String output = Match(input).of(
                Case($(1), "one"),
                Case($(2), "two"),
                Case($(3), "three"),
                Case($(), "?"));

        assertEquals("two", output);
    }

    @Test
    public void whenMatchworks_thenCorrect_2() {
        String arg = "-h";

        String output = (String) Match(arg).of(
                Case($(isIn("-h", "--help")), "HELP"),
                Case($(isIn("-v", "--version")), "VERSION"),
                Case($(), o -> run(() -> {
                    throw new IllegalArgumentException(arg);
                }))
        );

        assertEquals("HELP", output);
    }
}