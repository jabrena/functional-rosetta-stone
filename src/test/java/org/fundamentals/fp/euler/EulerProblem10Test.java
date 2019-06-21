package org.fundamentals.fp.euler;

import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem10Test extends BaseEulerProblemTest {

    @Test
    public void given_problem6_when_10_then_expectedResultsTest(){

        EulerProblem10 problem = new EulerProblem10();

        assertThat(problem.javaStreamSolution(10L)).isEqualTo(17);
        assertThat(problem.javaStreamSolution(2_000_000L)).isEqualTo(euler.getAnswerToLong(10));
    }

}