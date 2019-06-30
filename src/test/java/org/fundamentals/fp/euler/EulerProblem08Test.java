package org.fundamentals.fp.euler;

import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem08Test extends BaseEulerProblemTest implements IEulerTestable {

    @Test
    @Override
    public void given_JavaSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem08 problem = new EulerProblem08();

        assertThat(problem.JavaSolution(4)).isEqualTo(5_832);
        assertThat(problem.JavaSolution(13)).isEqualTo(euler.getAnswerToLong(8));
    }

    @Test
    @Override
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem08 problem = new EulerProblem08();

        assertThat(problem.JavaStreamSolution(4)).isEqualTo(5_832);
        assertThat(problem.JavaStreamSolution(13)).isEqualTo(euler.getAnswerToLong(8));
    }

    @Test
    @Override
    public void given_VAVRSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem08 problem = new EulerProblem08();

        assertThat(problem.VAVRSolution(4)).isEqualTo(5_832);
        assertThat(problem.VAVRSolution(13)).isEqualTo(euler.getAnswerToLong(8));
    }

    @Override
    public void given_ReactorSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Override
    public void given_RxJavaSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Override
    public void given_KotlinSolution_when_executeMethod_then_expectedResultsTest() {

    }
}