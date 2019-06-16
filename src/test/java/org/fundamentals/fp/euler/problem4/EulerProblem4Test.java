package org.fundamentals.fp.euler.problem4;

import org.assertj.core.api.AssertionsForClassTypes;
import org.fundamentals.fp.euler.problem3.EulerProblem3;
import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;

public class EulerProblem4Test extends BaseEulerProblemTest {

    @Test
    public void given_palindromic_when_2digits_then_expectedResultsTest(){

        EulerProblem4 problem4 = new EulerProblem4();

        AssertionsForClassTypes.assertThat(problem4.javaSolution(2)).isEqualTo(9009);
        AssertionsForClassTypes.assertThat(problem4.javaSolution(3)).isEqualTo(euler.getAnswerToLong(4));
    }

    @Test
    public void given_palindromicStream_when_2digits_then_expectedResultsTest(){

        EulerProblem4 problem4 = new EulerProblem4();

        AssertionsForClassTypes.assertThat(problem4.javaStreamSolution(2)).isEqualTo(9009);
        AssertionsForClassTypes.assertThat(problem4.javaStreamSolution(3)).isEqualTo(euler.getAnswerToLong(4));
    }

}