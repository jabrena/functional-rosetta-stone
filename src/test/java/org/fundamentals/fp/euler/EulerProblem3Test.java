package org.fundamentals.fp.euler;

import org.assertj.core.api.AssertionsForClassTypes;
import org.fundamentals.fp.euler.EulerProblem3;
import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;

public class EulerProblem3Test extends BaseEulerProblemTest {

    @Test
    public void given_primeFactors_when_13195_then_expectedResultsTest(){

        EulerProblem3 problem3 = new EulerProblem3();

        AssertionsForClassTypes.assertThat(problem3.javaSolution(13195L)).isEqualTo(29);
        AssertionsForClassTypes.assertThat(problem3.javaSolution(600851475143L)).isEqualTo(euler.getAnswerToLong(3));

    }

    @Test
    public void given_primeFactorsStream_when_13195_then_expectedResultsTest(){

        EulerProblem3 problem3 = new EulerProblem3();

        AssertionsForClassTypes.assertThat(problem3.javaStreamSolution(13195L)).isEqualTo(29);
        AssertionsForClassTypes.assertThat(problem3.javaStreamSolution(600851475143L)).isEqualTo(euler.getAnswerToLong(3));

    }

}