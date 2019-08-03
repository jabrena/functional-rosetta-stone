package org.fundamentals.fp.playground;

import io.vavr.control.Option;
import org.junit.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertEquals;

/*
    The main goal of Option is to eliminate null checks in our code by leveraging the Java type system
    Option is an object container in Vavr with a similar end goal like Optional in Java 8
    Vavr’s Option implements Serializable, Iterable, and has a richer API.
 */
public class OptionTest {

    @Test
    public void optionTest1() {
        Option<String> noneOption = Option.of(null);
        Option<String> someOption = Option.of("val");

        then(noneOption).isEqualTo(Option.none());
        then(someOption).isEqualTo(Option.some("val"));
    }

    @Test
    public void optionTest2() {
        String name = null;
        Option<String> nameOption = Option.of(name);

        then(nameOption.getOrElse("string")).isEqualTo("string");
    }
}