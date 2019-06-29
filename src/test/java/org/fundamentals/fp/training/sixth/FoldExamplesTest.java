package org.fundamentals.fp.training.sixth;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.vavr.API.List;
import static org.assertj.core.api.Assertions.assertThat;

public class FoldExamplesTest {

    @Disabled
    @Test
    public void given_foldOperator_when_simpleCombination_then_expectedBehavioursTest() {

        //((a + b) + c)
        assertThat(List("a", "b", "c").foldLeft("", (a, b) -> a + b))
                .isEqualTo("abc");

        //a + (b + c)
        assertThat(List("a", "b", "c").foldRight("", (a, b) -> a + b))
                .isEqualTo("abc");

        //(a ^ b) ^ c
        assertThat(List(1, 2, 3).foldLeft(0d, (a, b) -> Math.pow(a, b)))
                .isEqualTo(1.0);

        //a ^ (b ^ c)
        assertThat(List(1, 2, 3).foldRight(0d, (a, b) -> Math.pow(a, b)))
                .isEqualTo("8.0");

    }

}