package org.fundamentals.fp.euler.problem4;

import org.assertj.core.api.AssertionsForClassTypes;
import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class EulerProblem4Test extends BaseEulerProblemTest {

    @Disabled
    @Test
    public void given_palindromic_when_2digits_then_expectedResultsTest(){

        EulerProblem4 problem4 = new EulerProblem4();

        AssertionsForClassTypes.assertThat(problem4.javaSolution(10, 99)).isEqualTo(9009);
        AssertionsForClassTypes.assertThat(problem4.javaSolution(100, 999)).isEqualTo(euler.getAnswerToLong(4));
    }

    @Disabled
    @Test
    public void given_palindromicStream_when_2digits_then_expectedResultsTest(){

        EulerProblem4 problem4 = new EulerProblem4();

        AssertionsForClassTypes.assertThat(problem4.javaStreamSolution(10, 99)).isEqualTo(9009);
        AssertionsForClassTypes.assertThat(problem4.javaStreamSolution(100, 999)).isEqualTo(euler.getAnswerToLong(4));
    }

    @Test
    public void given_palindromicVAVR_when_2digits_then_expectedResultsTest(){

        EulerProblem4 problem4 = new EulerProblem4();

        AssertionsForClassTypes.assertThat(problem4.VAVRSolution(10, 99)).isEqualTo(9009);
        AssertionsForClassTypes.assertThat(problem4.VAVRSolution(100, 999)).isEqualTo(euler.getAnswerToInt(4));
    }

}