package org.fundamentals.fp.euler;

import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem05Test extends BaseEulerProblemTest implements IEulerTestable {

    @Override
    public void given_JavaSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Test
    @Override
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest(){

        EulerProblem05 problem = new EulerProblem05();

        assertThat(problem.JavaStreamSolution(10L)).isEqualTo(2_520L);
        assertThat(problem.VAVRSolution(20L)).isEqualTo(euler.getAnswerToLong(5));
    }

    @Test
    @Override
    public void given_VAVRSolution_when_executeMethod_then_expectedResultsTest(){

        EulerProblem05 problem = new EulerProblem05();

        assertThat(problem.VAVRSolution(10L)).isEqualTo(2_520L);
        assertThat(problem.VAVRSolution(20L)).isEqualTo(euler.getAnswerToLong(5));
    }

    @Disabled
    @Test
    @Override
    public void given_ReactorSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem05 problem = new EulerProblem05();

        StepVerifier
                .create(problem.ReactorSolution(10L))
                .expectNext(2_520L)
                .expectComplete()
                .verify();

    }

    @Override
    public void given_RxJavaSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Override
    public void given_KotlinSolution_when_executeMethod_then_expectedResultsTest() {

    }

}