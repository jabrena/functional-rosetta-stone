package org.fundamentals.fp.playground;

import io.vavr.control.Option;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/*
    The main goal of Option is to eliminate null checks in our code by leveraging the Java type system
    Option is an object container in Vavr with a similar end goal like Optional in Java 8
    Vavr’s Option implements Serializable, Iterable, and has a richer API.
 */
public class OptionTest {

    @Test
    public void givenValue_whenCreatesOption_thenCorrect() {
        Option<Object> noneOption = Option.of(null);
        Option<Object> someOption = Option.of("val");

        assertEquals("None", noneOption.toString());
        assertEquals("Some(val)", someOption.toString());
    }

    @Test
    public void givenNull_whenCreatesOption_thenCorrect() {
        String name = null;
        Option<String> nameOption = Option.of(name);

        assertEquals("string", nameOption.getOrElse("string"));
    }
}