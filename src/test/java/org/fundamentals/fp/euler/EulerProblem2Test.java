package org.fundamentals.fp.euler;

import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

public class EulerProblem2Test extends BaseEulerProblemTest {

    @Test
    public void given_JavaSolutionProblem2_WhenFibonacciSerie_ThenValueExpectedTest() {

        EulerProblem2 problem2 = new EulerProblem2();
        assertThat(problem2.javaSolution(100)).isEqualTo(2 + 8 + 34);
        assertThat(problem2.javaSolution(4_000_000)).isEqualTo(euler.getAnswerToLong(2));
    }

    @Test
    public void given_JavaStreamSolutionProblem_when_sumEven_then_returnsExpectedValue() {

        EulerProblem2 problem2 = new EulerProblem2();

        assertThat(problem2.javaStreamSolution(100)).isEqualTo(2 + 8 + 34);
        assertThat(problem2.javaStreamSolution(4_000_000)).isEqualTo(euler.getAnswerToLong(2));
    }



    @Test
    public void given_VAVRSolutionProblem_when_sumEven_then_returnsExpectedValue() {

        EulerProblem2 problem2 = new EulerProblem2();

        assertThat(problem2.VAVRSolution(100)).isEqualTo(2 + 8 + 34);
        assertThat(problem2.VAVRSolution(4_000_000L)).isEqualTo(euler.getAnswerToLong(2));
    }

    @Test
    public void given_ReactorSolutionProblem2_when_sumEven_then_returnExpectedValueTest() {

        EulerProblem2 problem2 = new EulerProblem2();

        StepVerifier
                .create(problem2.ReactorSolution(100))
                .expectNext(44L)
                .expectComplete()
                .verify();

        StepVerifier
                .create(problem2.ReactorSolution(4_000_000))
                .expectNext(euler.getAnswerToLong(2))
                .expectComplete()
                .verify();

    }

}