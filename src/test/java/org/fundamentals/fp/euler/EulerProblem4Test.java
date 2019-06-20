package org.fundamentals.fp.euler;

import org.assertj.core.api.AssertionsForClassTypes;
import org.fundamentals.fp.euler.EulerProblem4;
import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class EulerProblem4Test extends BaseEulerProblemTest {

    @Disabled
    @Test
    public void given_palindromic_when_2digits_then_expectedResultsTest(){

        EulerProblem4 problem4 = new EulerProblem4();

        AssertionsForClassTypes.assertThat(problem4.javaSolution(10L, 99L)).isEqualTo(9009L);
        AssertionsForClassTypes.assertThat(problem4.javaSolution(100L, 999L)).isEqualTo(euler.getAnswerToLong(4));
    }

    @Test
    public void given_palindromicStream_when_2digits_then_expectedResultsTest(){

        EulerProblem4 problem4 = new EulerProblem4();

        AssertionsForClassTypes.assertThat(problem4.javaStreamSolution(10L, 99L)).isEqualTo(9009L);
        AssertionsForClassTypes.assertThat(problem4.javaStreamSolution(100L, 999L)).isEqualTo(euler.getAnswerToLong(4));
    }

    @Test
    public void given_palindromicVAVR_when_2digits_then_expectedResultsTest(){

        EulerProblem4 problem4 = new EulerProblem4();

        AssertionsForClassTypes.assertThat(problem4.VAVRSolution(10L, 99L)).isEqualTo(9009L);
        AssertionsForClassTypes.assertThat(problem4.VAVRSolution(100L, 999L)).isEqualTo(euler.getAnswerToLong(4));
    }

    @Test
    public void given_ReactorSolution_when_2digits_then_expectdResultsTest() {

        EulerProblem4 problem4 = new EulerProblem4();

        StepVerifier
                .create(problem4.reactorSolution(10, 99))
                .expectNext(9009L)
                .expectComplete()
                .verify();

        StepVerifier
                .create(problem4.reactorSolution(100, 999))
                .expectNext(euler.getAnswerToLong(4))
                .expectComplete()
                .verify();
    }

}