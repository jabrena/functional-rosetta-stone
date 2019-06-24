package org.fundamentals.fp.euler;

import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem1Test extends BaseEulerProblemTest {

    @Test
    public void given_JavaSolutionProblem1_WhenLimit10_ThenSumIsExpectedTest() {

        EulerProblem1 problem1 = new EulerProblem1();

        assertThat(problem1.javaSolution(10)).isEqualTo(3 + 5 + 6 + 9);
        assertThat(problem1.javaSolution(1000)).isEqualTo(euler.getAnswerToLong(1));
    }

    @Test
    public void given_JavaStreamSolutionProblem1_WhenLimit10_ThenSumIsExpectedTest() {

        EulerProblem1 problem1 = new EulerProblem1();

        assertThat(problem1.javaStreamSolution(10)).isEqualTo(3 + 5 + 6 + 9);
        assertThat(problem1.javaStreamSolution(1000)).isEqualTo(euler.getAnswerToLong(1));
    }

    @Test
    public void given_VAVRSolutionProblem1_WhenLimit10_ThenSumIsExpectedTest() {

        EulerProblem1 problem1 = new EulerProblem1();

        assertThat(problem1.VAVRSolution(10)).isEqualTo(3 + 5 + 6 + 9);
        assertThat(problem1.VAVRSolution(1000)).isEqualTo(euler.getAnswerToLong(1));
    }

    @Test
    public void given_ReactorSolutionProblem1_WhenLimit10_ThenSumIsExpectedTest() {

        EulerProblem1 problem1 = new EulerProblem1();

        StepVerifier
                .create(problem1.ReactorSolution(10))
                .expectNext(23L)
                .expectComplete()
                .verify();

        StepVerifier
                .create(problem1.ReactorSolution(1000))
                .expectNext(euler.getAnswerToLong(1))
                .expectComplete()
                .verify();
    }

}