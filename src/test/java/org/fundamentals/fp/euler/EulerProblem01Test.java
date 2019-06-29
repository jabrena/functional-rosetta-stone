package org.fundamentals.fp.euler;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;
import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem01Test extends BaseEulerProblemTest {

    @Test
    public void given_JavaSolutionProblem1_WhenLimit10_ThenSumIsExpectedTest() {

        EulerProblem01 problem1 = new EulerProblem01();

        assertThat(problem1.JavaSolution(10)).isEqualTo(3 + 5 + 6 + 9);
        assertThat(problem1.JavaSolution(1000)).isEqualTo(euler.getAnswerToLong(1));
    }

    @Test
    public void given_JavaStreamSolutionProblem1_WhenLimit10_ThenSumIsExpectedTest() {

        EulerProblem01 problem1 = new EulerProblem01();

        assertThat(problem1.JavaStreamSolution(10)).isEqualTo(3 + 5 + 6 + 9);
        assertThat(problem1.JavaStreamSolution(1000)).isEqualTo(euler.getAnswerToLong(1));
    }

    @Test
    public void given_VAVRSolutionProblem1_WhenLimit10_ThenSumIsExpectedTest() {

        EulerProblem01 problem1 = new EulerProblem01();

        assertThat(problem1.VAVRSolution(10)).isEqualTo(3 + 5 + 6 + 9);
        assertThat(problem1.VAVRSolution(1000)).isEqualTo(euler.getAnswerToLong(1));
    }

    @Test
    public void given_ReactorSolutionProblem1_WhenLimit10_ThenSumIsExpectedTest() {

        EulerProblem01 problem1 = new EulerProblem01();

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

    @Test
    public void given_KotlinSolution_whenExecute_then_expectedResultTest() {

        EulerProblem01 problem1 = new EulerProblem01();

        assertThat(problem1.KotlinSolution(10)).isEqualTo(3 + 5 + 6 + 9);
        assertThat(problem1.KotlinSolution(1000)).isEqualTo(euler.getAnswerToLong(1));
    }

    @Test
    public void given_RXJavaSolution_whenExecute_then_expectedResultTest() {

        EulerProblem01 problem1 = new EulerProblem01();

        assertThat(problem1.RxJavaSolution(10).test()
                .assertComplete()
                .assertNoErrors()
                .assertValueCount(1)
                .values().get(0)).isEqualTo(3l + 5l + 6l + 9l);

        assertThat(problem1.RxJavaSolution(1000).test()
                .assertComplete()
                .assertNoErrors()
                .assertValueCount(1)
                .values().get(0)).isEqualTo(euler.getAnswerToLong(1));
    }

}