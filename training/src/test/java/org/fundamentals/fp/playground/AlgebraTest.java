package org.fundamentals.fp.playground;

import io.vavr.Function1;
import org.junit.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class AlgebraTest {

    @Test
    public void andThenTest() {

        Function1<Integer, Integer> plusOne = a -> a + 1;
        Function1<Integer, Integer> multiplyByTwo = a -> a * 2;
        Function1<Integer, Integer> add1AndMultiplyBy2 = plusOne.andThen(multiplyByTwo);

        then(add1AndMultiplyBy2.apply(2)).isEqualTo(6);
    }

    @Test
    public void composeTest() {

        Function1<Integer, Integer> plusOne = a -> a + 1;
        Function1<Integer, Integer> multiplyByTwo = a -> a * 2;
        Function1<Integer, Integer> add1AndMultiplyBy2 = multiplyByTwo.compose(plusOne);

        then(add1AndMultiplyBy2.apply(2)).isEqualTo(6);
    }

}
