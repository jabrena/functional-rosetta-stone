package org.fundamentals.fp.euler;

import java.math.BigInteger;
import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem20Test extends BaseEulerProblemTest {

    @Test
    public void given_problem16_when_javaStreamSolution_then_expectedResultsTest(){

        EulerProblem20 problem = new EulerProblem20();

        assertThat(problem.factorial.apply(10L)).isEqualTo(BigInteger.valueOf(3628800));
        assertThat(problem.javaStreamSolution(10L)).isEqualTo(27L);
        assertThat(problem.javaStreamSolution(100L)).isEqualTo(euler.getAnswerToLong(20));
    }


}