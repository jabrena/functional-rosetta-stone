package org.fundamentals.fp.euler;

import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem29Test extends BaseEulerProblemTest {

    @Test
    public void given_problem_when_javaStreamSolution_then_expectedResultsTest(){

        EulerProblem29 problem = new EulerProblem29();

        assertThat(problem.javaStreamSolution(5L)).isEqualTo(15L);
        assertThat(problem.javaStreamSolution(100L)).isEqualTo(euler.getAnswerToLong(29));
    }

}