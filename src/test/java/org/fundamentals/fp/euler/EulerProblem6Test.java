package org.fundamentals.fp.euler;

import org.assertj.core.api.AssertionsForClassTypes;
import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class EulerProblem6Test extends BaseEulerProblemTest {

    @Test
    public void given_problem6_when_10_then_expectedResultsTest(){

        EulerProblem6 problem6 = new EulerProblem6();

        AssertionsForClassTypes.assertThat(problem6.javaStreamsSumSquare(10L)).isEqualTo(385);
        AssertionsForClassTypes.assertThat(problem6.javaStreamsSquareSum(10L)).isEqualTo(3025);
        AssertionsForClassTypes.assertThat(problem6.javaStreamsDiffsumSquare(10L)).isEqualTo(2640);
        AssertionsForClassTypes.assertThat(problem6.javaStreamsDiffsumSquare(100L)).isEqualTo(euler.getAnswerToLong(6));
    }


}