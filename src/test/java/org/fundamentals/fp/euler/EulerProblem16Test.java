package org.fundamentals.fp.euler;

import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem16Test extends BaseEulerProblemTest {

    @Test
    public void given_problem16_when_javaStreamSolution_then_expectedResultsTest(){

        EulerProblem16 problem = new EulerProblem16();

        assertThat(problem.javaStreamSolution(15L)).isEqualTo(26L);
        assertThat(problem.javaStreamSolution(1000L)).isEqualTo(euler.getAnswerToLong(16));
    }

    @Test
    public void given_problem16_when_VAVRSolution_then_expectedResultsTest(){

        EulerProblem16 problem = new EulerProblem16();

        assertThat(problem.VAVRSolution(15L)).isEqualTo(26L);
        assertThat(problem.VAVRSolution(1000L)).isEqualTo(euler.getAnswerToLong(16));
    }

}