package org.fundamentals.fp.euler;

import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

public class EulerProblem02Test extends BaseEulerProblemTest implements IEulerTestable {

    @Test
    @Override
    public void given_JavaSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem02 problem = new EulerProblem02();

        assertThat(problem.JavaSolution(100L)).isEqualTo(2 + 8 + 34);
        assertThat(problem.JavaSolution(4_000_000L)).isEqualTo(euler.getAnswerToLong(2));
    }

    @Test
    @Override
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem02 problem = new EulerProblem02();

        assertThat(problem.JavaStreamSolution(100L)).isEqualTo(2 + 8 + 34);
        assertThat(problem.JavaStreamSolution(4_000_000L)).isEqualTo(euler.getAnswerToLong(2));
    }

    @Test
    @Override
    public void given_VAVRSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem02 problem = new EulerProblem02();

        assertThat(problem.VAVRSolution(100L)).isEqualTo(2 + 8 + 34);
        assertThat(problem.VAVRSolution(4_000_000L)).isEqualTo(euler.getAnswerToLong(2));
    }

    @Test
    @Override
    public void given_ReactorSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem02 problem = new EulerProblem02();

        StepVerifier
                .create(problem.ReactorSolution(100L))
                .expectNext(44L)
                .expectComplete()
                .verify();

        StepVerifier
                .create(problem.ReactorSolution(4_000_000L))
                .expectNext(euler.getAnswerToLong(2))
                .expectComplete()
                .verify();
    }

    @Test
    @Override
    public void given_RxJavaSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem02 problem = new EulerProblem02();

        assertThat(problem.RxJavaSolution(100L).test()
                .assertComplete()
                .assertNoErrors()
                .assertValueCount(1)
                .values().get(0)).isEqualTo(2 + 8 + 34);

        assertThat(problem.RxJavaSolution(4_000_000L).test()
                .assertComplete()
                .assertNoErrors()
                .assertValueCount(1)
                .values().get(0)).isEqualTo(euler.getAnswerToLong(2));
    }

    @Test
    @Override
    public void given_KotlinSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem02 problem = new EulerProblem02();

        assertThat(problem.KotlinSolution(100L)).isEqualTo(2 + 8 + 34);
        assertThat(problem.KotlinSolution(4_000_000L)).isEqualTo(euler.getAnswerToLong(2));
    }

}