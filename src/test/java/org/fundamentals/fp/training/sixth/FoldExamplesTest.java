package org.fundamentals.fp.training.sixth;

import org.junit.jupiter.api.Test;

import static io.vavr.API.List;
import static org.assertj.core.api.Assertions.assertThat;

public class FoldExamplesTest {

    @Test
    public void given_foldOperator_when_simpleCombination_then_expectedBehavioursTest() {

        //a ^ (b ^ (c ^ 0))
        assertThat(List(3, 2, 1).foldRight(0d, (a, b) -> Math.pow(a, b))).isEqualTo(9.0);

        //a ^ (b ^ (c ^ 1))
        assertThat(List(1, 2, 3).foldRight(0d, (a, b) -> Math.pow(a, b))).isEqualTo(1.0);

        //a ^ (b ^ (c ^ 1))
        assertThat(List(1, 2, 3).foldRight(1d, (a, b) -> Math.pow(a, b))).isEqualTo(1.0);

        //((1 ^ a) ^ b) ^ c
        assertThat(List(1, 2, 3).foldLeft(1d, (a, b) -> Math.pow(a, b))).isEqualTo(1.0);

        //((1 ^ a) ^ b) ^ c
        assertThat(List(1, 2, 3).foldLeft(0d, (a, b) -> Math.pow(a, b))).isEqualTo(0.0);

        //a ^ (b ^ c))
        assertThat(List(1, 2, 3).map(x -> Double.valueOf(x))
                .reduce((a, b)-> Math.pow(a, b))).isEqualTo(1.0);

        //a ^ (b ^ c))
        assertThat(List(2, 2, 3).map(x -> Double.valueOf(x)).reduce((a, b)-> Math.pow(a, b))).isEqualTo(64.0);

        //a ^ (b ^ c))
        assertThat(List(3, 2, 1).map(x -> Double.valueOf(x)).reduce((a, b)-> Math.pow(a, b))).isEqualTo(9.0);
    }

}