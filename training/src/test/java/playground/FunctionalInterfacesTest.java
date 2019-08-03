package org.fundamentals.fp.playground;

import io.vavr.Function0;
import io.vavr.Function1;
import io.vavr.Function2;
import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

/*
    Java 8 provides only two basic functional interfaces: Function, BiFunction
    Vavr extends the idea of functional interfaces in Java further by supporting up to a maximum of eight parameters
    and spicing up the API with methods for memoization, composition, and currying.
 */
public class FunctionalInterfacesTest {

    @Test
    public void whenCreatesFunction_thenCorrect0() {
        Function0<String> getClazzName = () -> this.getClass().getName();

        assertEquals("org.am061.java.vavr.FunctionalInterfacesTest", getClazzName.apply());
    }

    @Test
    public void givenVavrFunction_whenWorks_thenCorrect() {
        Function1<Integer, Integer> squareVavr = (num) -> num * num;
        Function<Integer, Integer> squareJava = (num) -> num * num;

        assertEquals((Integer) 4, squareVavr.apply(2));
        assertEquals((Integer) 4, squareJava.apply(2));
    }

    @Test
    public void givenVavrBiFunction_whenWorks_thenCorrect() {
        Function2<Integer, Integer, Integer> sumVavr = (num1, num2) -> num1 + num2;
        BiFunction<Integer, Integer, Integer> sumJava = (num1, num2) -> num1 + num2;

        assertEquals((Integer) 12, sumVavr.apply(5, 7));
        assertEquals((Integer) 12, sumJava.apply(5, 7));
    }

    private int sum(int a, int b) {
        System.out.println("Summing up " + a + " and " + b);
        return a + b;
    }

    @Test
    public void whenCreatesFunctionFromMethodRef_thenCorrect() {
        Function2<Integer, Integer, Integer> sum = Function2.of(this::sum);
        Function2<Integer, Integer, Integer> memoizedSum = sum.memoized();
        Function1<Integer, Integer> sumWith6 = sum.curried().apply(6);
        Function2<Integer, Integer, Integer> sumWith12 = sum.andThen(sumWith6).andThen(sumWith6);

        assertEquals((Integer) 11, sum.apply(5, 6));

        assertEquals((Integer) 11, memoizedSum.apply(5, 6));
        assertEquals((Integer) 11, memoizedSum.apply(5, 6));
        assertEquals((Integer) 11, memoizedSum.apply(5, 6));

        assertEquals((Integer) 11, memoizedSum.apply(6, 5));
        assertEquals((Integer) 11, memoizedSum.apply(6, 5));

        System.out.println();
        
        assertEquals((Integer) 11, sumWith6.apply(5));
        assertEquals((Integer) 22, sumWith12.apply(5, 5));
    }
}