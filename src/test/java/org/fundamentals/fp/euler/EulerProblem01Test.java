package org.fundamentals.fp.euler;

import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem01Test extends BaseEulerProblemTest {

    @Test
    public void given_JavaSolution_when_parameter_then_expectedResultTest() {

        EulerProblem01 problem = new EulerProblem01();

        assertThat(problem.JavaSolution(10)).isEqualTo(3 + 5 + 6 + 9);
        assertThat(problem.JavaSolution(1000)).isEqualTo(euler.getAnswerToInt(1));
    }

    @Test
    public void given_JavaStream_when_parameter_then_expectedResultTest() {

        EulerProblem01 problem = new EulerProblem01();

        assertThat(problem.isMultiple.test(10, 5)).isTrue();
        assertThat(problem.isMultiple.test(10, 3)).isFalse();
        assertThat(problem.isMultiple3.test(6)).isTrue();
        assertThat(problem.isMultiple5.test(10)).isTrue();

        assertThat(problem.JavaStreamSolution(10)).isEqualTo(3 + 5 + 6 + 9);
        assertThat(problem.JavaStreamSolution(1000)).isEqualTo(euler.getAnswerToInt(1));
    }

    @Test
    public void given_VAVRSolution_when_parameter_then_expectedResultTest() {

        EulerProblem01 problem = new EulerProblem01();

        assertThat(problem.VAVRSolution(10)).isEqualTo(3 + 5 + 6 + 9);
        assertThat(problem.VAVRSolution(1000)).isEqualTo(euler.getAnswerToInt(1));
    }

    @Test
    public void given_ReactorSolution_when_parameter_then_expectedResultTest() {

        EulerProblem01 problem = new EulerProblem01();

        StepVerifier
                .create(problem.ReactorSolution(10))
                .expectNext(23)
                .expectComplete()
                .verify();

        StepVerifier
                .create(problem.ReactorSolution(1000))
                .expectNext(euler.getAnswerToInt(1))
                .expectComplete()
                .verify();
    }

    @Test
    public void given_RxJavaSolution_when_parameter_then_expectedResultTest() {

        EulerProblem01 problem = new EulerProblem01();

        assertThat(problem.RxJavaSolution(10).test()
                .assertComplete()
                .assertNoErrors()
                .assertValueCount(1)
                .values().get(0)).isEqualTo(3l + 5l + 6l + 9l);

        assertThat(problem.RxJavaSolution(1000).test()
                .assertComplete()
                .assertNoErrors()
                .assertValueCount(1)
                .values().get(0)).isEqualTo(euler.getAnswerToInt(1));
    }

    @Test
    public void given_KotlinSolution_when_parameter_then_expectedResultTest() {

        EulerProblem01 problem = new EulerProblem01();

        assertThat(problem.KotlinSolution(10)).isEqualTo(3 + 5 + 6 + 9);
        assertThat(problem.KotlinSolution(1000)).isEqualTo(euler.getAnswerToInt(1));
    }

}