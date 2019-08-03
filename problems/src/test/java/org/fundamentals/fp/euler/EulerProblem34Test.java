package org.fundamentals.fp.euler;

import org.assertj.core.api.Assertions;
import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem34Test extends BaseEulerProblemTest {

    @Test
    public void given_problem_when_javaStreamSolution_then_expectedResultsTest(){

        EulerProblem34 problem = new EulerProblem34();

        assertThat(problem.isCurious.test(145L)).isTrue();
        //assertThat(problem.isCurious.test(40585L)).isTrue();
        //assertThat(problem.javaStreamSolution()).isEqualTo(euler.getAnswerToLong(34));
    }

    @Test
    public void given_problem_when_VAVRSolution_then_expectedResultsTest() {

        EulerProblem34 problem = new EulerProblem34();

        //Assertions.assertThat(sumOfDigitFactorial(145)).isEqualTo(145);
        Assertions.assertThat(problem.VAVRSolution()).isEqualTo(40730);
    }

}