package org.fundamentals.fp.euler;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

public class EulerProblem20Test extends BaseEulerProblemTest implements IEulerTestable {

    @Test
    @Override
    public void given_JavaSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem20 problem = new EulerProblem20();

        assertThat(problem.JavaSolution(10L)).isEqualTo(27L);
        assertThat(problem.JavaSolution(100L)).isEqualTo(euler.getAnswerToLong(20));
    }

    @Test
    @Override
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem20 problem = new EulerProblem20();

        assertThat(problem.JavaStreamSolution(10L)).isEqualTo(27L);
        assertThat(problem.JavaStreamSolution(100L)).isEqualTo(euler.getAnswerToLong(20));
    }

    @Test
    public void given_JavaStreamSolution2_when_executeMethod_then_expectedResultsTest() {

        EulerProblem20 problem = new EulerProblem20();

        assertThat(problem.JavaStreamSolution2(10L)).isEqualTo(27L);
        assertThat(problem.JavaStreamSolution2(100L)).isEqualTo(euler.getAnswerToLong(20));
    }


}