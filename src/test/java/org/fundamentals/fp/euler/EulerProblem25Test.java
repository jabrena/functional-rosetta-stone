package org.fundamentals.fp.euler;

import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem25Test extends BaseEulerProblemTest {

    @Test
    public void given_problem_when_javaStreamSolution_then_expectedResultsTest(){

        EulerProblem25 problem = new EulerProblem25();

        assertThat(problem.javaStreamSolution(3L)).isEqualTo(144);
        //assertThat(problem.javaStreamSolution(1000L)).isEqualTo(euler.getAnswerToLong(25));
    }

}