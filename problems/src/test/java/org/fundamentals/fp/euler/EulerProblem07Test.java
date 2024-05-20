package org.fundamentals.fp.euler;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;

public class EulerProblem07Test extends BaseEulerProblemTest implements IEulerTestable {

    @Override
    public void given_JavaSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Test
    @Override
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem07 problem7 = new EulerProblem07();

        assertThat(problem7.isPrime(7)).isEqualTo(true);
        assertThat(problem7.isPrime(9)).isEqualTo(false);
        assertThat(problem7.JavaStreamSolution(6L)).isEqualTo(13);
        assertThat(problem7.JavaStreamSolution(10001)).isEqualTo(euler.getAnswerToLong(7));

    }

    @Override
    public void given_VAVRSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Override
    public void given_ReactorSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Override
    public void given_RxJavaSolution_when_executeMethod_then_expectedResultsTest() {

    }
}