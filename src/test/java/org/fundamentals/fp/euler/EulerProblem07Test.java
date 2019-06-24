package org.fundamentals.fp.euler;

import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem07Test extends BaseEulerProblemTest {

    @Test
    public void given_problem6_when_10_then_expectedResultsTest(){

        EulerProblem07 problem7 = new EulerProblem07();

        assertThat(problem7.isPrime(7)).isEqualTo(true);
        assertThat(problem7.isPrime(9)).isEqualTo(false);
        assertThat(problem7.javaStreamsSolution(6L)).isEqualTo(13);
        assertThat(problem7.javaStreamsSolution(10001)).isEqualTo(euler.getAnswerToLong(7));
    }

}