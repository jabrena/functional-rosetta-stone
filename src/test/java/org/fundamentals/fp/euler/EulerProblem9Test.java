package org.fundamentals.fp.euler;

import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem9Test extends BaseEulerProblemTest {

    @Disabled
    @Test
    public void given_problem9_when_javaStreamSolution_then_expectedResultsTest(){

        EulerProblem9 problem = new EulerProblem9();

        assertThat(problem.javaStreamSolution(1000L)).isEqualTo(euler.getAnswerToLong(9));
    }

    @Test
    public void given_problem9_when_javaVAVRSolution_then_expectedResultsTest(){

        EulerProblem9 problem = new EulerProblem9();

        assertThat(problem.VAVRSolution(1000)).isEqualTo(euler.getAnswerToLong(9));
    }

}