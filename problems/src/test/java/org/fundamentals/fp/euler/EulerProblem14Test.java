package org.fundamentals.fp.euler;

import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem14Test extends BaseEulerProblemTest {

    @Disabled
    @Test
    public void given_problem10_when_10_then_expectedResultsTest(){

        EulerProblem14 problem = new EulerProblem14();

        assertThat(problem.javaStreamSolution(13L)).isEqualTo(10L);
        assertThat(problem.javaStreamSolution(1_000_000L)).isEqualTo(euler.getAnswerToLong(14));
    }

}