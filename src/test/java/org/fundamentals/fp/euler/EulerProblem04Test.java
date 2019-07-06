package org.fundamentals.fp.euler;

import org.assertj.core.api.Assertions;
import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem04Test extends BaseEulerProblemTest implements IEulerTestable {

    @Disabled
    @Test
    @Override
    public void given_JavaSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem04 problem4 = new EulerProblem04();

        assertThat(problem4.JavaSolution(10L, 99L)).isEqualTo(9009L);
        assertThat(problem4.JavaSolution(100L, 999L)).isEqualTo(euler.getAnswerToLong(4));
    }

    @Test
    @Override
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem04 problem4 = new EulerProblem04();

        assertThat(problem4.JavaStreamSolution(10L, 99L)).isEqualTo(9009L);
        assertThat(problem4.JavaStreamSolution(100L, 999L)).isEqualTo(euler.getAnswerToLong(4));
    }

    @Test
    @Override
    public void given_VAVRSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem04 problem4 = new EulerProblem04();

        assertThat(problem4.VAVRSolution(10L, 99L)).isEqualTo(9009L);
        assertThat(problem4.VAVRSolution(100L, 999L)).isEqualTo(euler.getAnswerToLong(4));
    }

    @Test
    @Override
    public void given_ReactorSolution_when_executeMethod_then_expectedResultsTest() {

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

    @Test
    @Override
    public void given_RxJavaSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Disabled
    @Test
    @Override
    public void given_KotlinSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem04 problem = new EulerProblem04();

        Assertions.assertThat(problem.KotlinSolution(10, 99)).isEqualTo(9009L);
        Assertions.assertThat(problem.KotlinSolution(100, 999)).isEqualTo(euler.getAnswerToLong(4));
    }

}