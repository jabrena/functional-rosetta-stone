package info.jab.fp.euler;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import info.jab.fp.euler.EulerProblem05;
import info.jab.fp.euler.utils.BaseEulerProblemTest;
import reactor.test.StepVerifier;

public class EulerProblem05Test extends BaseEulerProblemTest implements IEulerTestable {

    @Override
    public void given_JavaSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Test
    @Override
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest(){

        EulerProblem05 problem = new EulerProblem05();

        assertThat(problem.JavaStreamSolution(10L)).isEqualTo(2_520L);
        //assertThat(problem.VAVRSolution(20L)).isEqualTo(euler.getAnswerToLong(5));
    }

}