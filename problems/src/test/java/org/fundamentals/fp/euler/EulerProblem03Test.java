package org.fundamentals.fp.euler;

import org.assertj.core.api.Assertions;
import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem03Test extends BaseEulerProblemTest implements IEulerTestable {

    @Test
    @Override
    public void given_JavaSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem03 problem = new EulerProblem03();

        assertThat(problem.JavaSolution(13195L)).isEqualTo(29);
        assertThat(problem.JavaSolution(600851475143L)).isEqualTo(euler.getAnswerToLong(3));
    }

    @Test
    @Override
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem03 problem = new EulerProblem03();

        assertThat(problem.JavaStreamSolution(13195L)).isEqualTo(29);
        assertThat(problem.JavaStreamSolution(600851475143L)).isEqualTo(euler.getAnswerToLong(3));
    }

    @Test
    @Override
    public void given_VAVRSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem03 problem = new EulerProblem03();

        assertThat(problem.VAVRSolution(13195L)).isEqualTo(29);
        assertThat(problem.VAVRSolution(600851475143L)).isEqualTo(euler.getAnswerToLong(3));
    }

    @Disabled
    @Test
    @Override
    public void given_ReactorSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem03 problem = new EulerProblem03();

        StepVerifier
                .create(problem.ReactorSolution(13195L))
                .expectNext(29L)
                .expectComplete()
                .verify();

        StepVerifier
                .create(problem.ReactorSolution(600851475143L))
                .expectNext(euler.getAnswerToLong(3))
                .expectComplete()
                .verify();
    }

    @Disabled
    @Test
    @Override
    public void given_RxJavaSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem03 problem = new EulerProblem03();

        Assertions.assertThat(problem.RxJavaSolution(13195L).test()
                .assertComplete()
                .assertNoErrors()
                .assertValueCount(1)
                .values().get(0)).isEqualTo(29L);

        Assertions.assertThat(problem.RxJavaSolution(600851475143L).test()
                .assertComplete()
                .assertNoErrors()
                .assertValueCount(1)
                .values().get(0)).isEqualTo(euler.getAnswerToLong(3));
    }

}