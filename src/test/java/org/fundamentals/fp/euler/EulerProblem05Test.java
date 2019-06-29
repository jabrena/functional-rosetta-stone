package org.fundamentals.fp.euler;

import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem05Test extends BaseEulerProblemTest {

    @Test
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest(){

        EulerProblem05 problem = new EulerProblem05();

        assertThat(problem.JavaStreamSolution(10L)).isEqualTo(2_520L);
        assertThat(problem.VAVRSolution(20L)).isEqualTo(euler.getAnswerToLong(5));
    }

    @Test
    public void given_VAVRSolution_when_executeMethod_then_expectedResultsTest(){

        EulerProblem05 problem = new EulerProblem05();

        assertThat(problem.VAVRSolution(10L)).isEqualTo(2_520L);
        assertThat(problem.VAVRSolution(20L)).isEqualTo(euler.getAnswerToLong(5));
    }

}