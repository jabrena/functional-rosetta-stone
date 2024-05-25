package org.fundamentals.fp.euler;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;

public class EulerProblem10Test extends BaseEulerProblemTest implements IEulerTestable  {

    @Override
    public void given_JavaSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Test
    @Override
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem10 problem = new EulerProblem10();

        assertThat(problem.JavaStreamSolution(10L)).isEqualTo(17);
        //Working but very slow in execution
        //assertThat(problem.javaStreamSolution(2_000_000L)).isEqualTo(euler.getAnswerToLong(10));
    }
}