package org.fundamentals.fp.playground.vavr;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import org.junit.Test;

import static java.lang.Integer.valueOf;
import static org.junit.Assert.assertEquals;

/*
    Tuples are immutable and can hold multiple objects of different types in a type-safe manner.
 */
public class TupleTest {

    @Test
    public void whenCreatesTuple_thenCorrect1() {
        Tuple2<String, Integer> java8 = Tuple.of("Java", 8);

        assertEquals("Java", java8._1);
        assertEquals(valueOf(8), java8._2());
    }

    @Test
    public void whenCreatesTuple_thenCorrect2() {
        Tuple3<String, Integer, Double> java8 = Tuple.of("Java", 8, 1.8);

        assertEquals("Java", java8._1);
        assertEquals(valueOf(8), java8._2());
        assertEquals(1.8, java8._3(), 0.001);
    }
}