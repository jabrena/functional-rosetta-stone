package org.fundamentals.fp.euler;

import org.assertj.core.api.Assertions;
import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem04Test extends BaseEulerProblemTest {

    @Disabled
    @Test
    public void given_palindromic_when_2digits_then_expectedResultsTest(){

        EulerProblem04 problem4 = new EulerProblem04();

        assertThat(problem4.JavaSolution(10L, 99L)).isEqualTo(9009L);
        assertThat(problem4.JavaSolution(100L, 999L)).isEqualTo(euler.getAnswerToLong(4));
    }

    @Test
    public void given_palindromicStream_when_2digits_then_expectedResultsTest(){

        EulerProblem04 problem4 = new EulerProblem04();

        assertThat(problem4.JavaStreamSolution(10L, 99L)).isEqualTo(9009L);
        assertThat(problem4.JavaStreamSolution(100L, 999L)).isEqualTo(euler.getAnswerToLong(4));
    }

    @Test
    public void given_palindromicVAVR_when_2digits_then_expectedResultsTest(){

        EulerProblem04 problem4 = new EulerProblem04();

        assertThat(problem4.VAVRSolution(10L, 99L)).isEqualTo(9009L);
        assertThat(problem4.VAVRSolution(100L, 999L)).isEqualTo(euler.getAnswerToLong(4));
    }

    @Test
    public void given_ReactorSolution_when_2digits_then_expectdResultsTest() {

        EulerProblem04 problem4 = new EulerProblem04();

        StepVerifier
                .create(problem4.ReactorSolution(10L, 99L))
                .expectNext(9009L)
                .expectComplete()
                .verify();

        StepVerifier
                .create(problem4.ReactorSolution(100L, 999L))
                .expectNext(euler.getAnswerToLong(4))
                .expectComplete()
                .verify();
    }

    @Disabled
    @Test
    public void given_KotlinSolutionProblem_when_sumEven_then_returnsExpectedValue() {

        EulerProblem04 problem = new EulerProblem04();

        Assertions.assertThat(problem.KotlinSolution(10, 99)).isEqualTo(9009L);
        Assertions.assertThat(problem.KotlinSolution(100, 999)).isEqualTo(euler.getAnswerToLong(4));
    }

}