package org.fundamentals.fp.euler;

import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem30Test extends BaseEulerProblemTest {

    @Test
    public void given_problem_when_javaStreamSolution_then_expectedResultsTest(){

        EulerProblem30 problem = new EulerProblem30();

        assertThat(problem.javaStreamSolution(4L)).isEqualTo(19316L);
        //assertThat(problem.javaStreamSolution(5L)).isEqualTo(euler.getAnswerToLong(30));
    }

}