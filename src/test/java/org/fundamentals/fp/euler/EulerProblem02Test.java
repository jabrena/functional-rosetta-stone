package org.fundamentals.fp.euler;

import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

public class EulerProblem02Test extends BaseEulerProblemTest {

    @Test
    public void given_JavaSolutionProblem2_WhenFibonacciSerie_ThenValueExpectedTest() {

        EulerProblem02 problem = new EulerProblem02();

        assertThat(problem.JavaSolution(100)).isEqualTo(2 + 8 + 34);
        assertThat(problem.JavaSolution(4_000_000)).isEqualTo(euler.getAnswerToLong(2));
    }

    @Test
    public void given_JavaStreamSolutionProblem_when_sumEven_then_returnsExpectedValue() {

        EulerProblem02 problem = new EulerProblem02();

        assertThat(problem.JavaStreamSolution(100)).isEqualTo(2 + 8 + 34);
        assertThat(problem.JavaStreamSolution(4_000_000)).isEqualTo(euler.getAnswerToLong(2));
    }

    @Test
    public void given_VAVRSolutionProblem_when_sumEven_then_returnsExpectedValue() {

        EulerProblem02 problem = new EulerProblem02();

        assertThat(problem.VAVRSolution(100)).isEqualTo(2 + 8 + 34);
        assertThat(problem.VAVRSolution(4_000_000L)).isEqualTo(euler.getAnswerToLong(2));
    }

    @Test
    public void given_ReactorSolutionProblem_when_sumEven_then_returnExpectedValueTest() {

        EulerProblem02 problem = new EulerProblem02();

        StepVerifier
                .create(problem.ReactorSolution(100))
                .expectNext(44L)
                .expectComplete()
                .verify();

        StepVerifier
                .create(problem.ReactorSolution(4_000_000))
                .expectNext(euler.getAnswerToLong(2))
                .expectComplete()
                .verify();
    }

    @Test
    public void given_KotlinSolutionProblem_when_sumEven_then_returnsExpectedValue() {

        EulerProblem02 problem = new EulerProblem02();

        assertThat(problem.KotlinSolution(100)).isEqualTo(2 + 8 + 34);
        assertThat(problem.KotlinSolution(4_000_000L)).isEqualTo(euler.getAnswerToLong(2));
    }

    @Test
    public void given_RxJavaSolution_whenExecute_then_expectedResultTest() {

        EulerProblem02 problem = new EulerProblem02();

        assertThat(problem.RxJavaSolution(100).test()
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

}