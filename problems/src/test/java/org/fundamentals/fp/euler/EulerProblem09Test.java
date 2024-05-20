package org.fundamentals.fp.euler;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;

public class EulerProblem09Test extends BaseEulerProblemTest implements IEulerTestable {

    @Override
    public void given_JavaSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Override
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Test
    @Override
    public void given_VAVRSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem09 problem = new EulerProblem09();

        assertThat(problem.VAVRSolution(1000)).isEqualTo(euler.getAnswerToLong(9));
    }

    @Override
    public void given_ReactorSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Override
    public void given_RxJavaSolution_when_executeMethod_then_expectedResultsTest() {

    }
}