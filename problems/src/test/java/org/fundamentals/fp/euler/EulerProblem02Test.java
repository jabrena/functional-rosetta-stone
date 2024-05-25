package org.fundamentals.fp.euler;

import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;

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

}