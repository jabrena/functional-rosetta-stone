package org.fundamentals.fp.euler;

import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;

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

}