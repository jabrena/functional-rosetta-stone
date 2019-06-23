package org.fundamentals.fp.euler;

import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem23Test extends BaseEulerProblemTest {

    @Disabled
    @Test
    public void given_problem_when_javaStreamSolution_then_expectedResultsTest(){

        EulerProblem23 problem = new EulerProblem23();

        assertThat(problem.javaStreamSolution()).isEqualTo(euler.getAnswerToLong(23));
    }

}