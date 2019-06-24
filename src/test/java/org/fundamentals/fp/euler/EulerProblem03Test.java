package org.fundamentals.fp.euler;

import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem03Test extends BaseEulerProblemTest {

    @Test
    public void given_primeFactors_when_13195_then_expectedResultsTest(){

        EulerProblem03 problem3 = new EulerProblem03();

        assertThat(problem3.javaSolution(13195L)).isEqualTo(29);
        assertThat(problem3.javaSolution(600851475143L)).isEqualTo(euler.getAnswerToLong(3));

    }

    @Test
    public void given_primeFactorsStream_when_13195_then_expectedResultsTest(){

        EulerProblem03 problem3 = new EulerProblem03();

        assertThat(problem3.javaStreamSolution(13195L)).isEqualTo(29);
        assertThat(problem3.javaStreamSolution(600851475143L)).isEqualTo(euler.getAnswerToLong(3));

    }

}