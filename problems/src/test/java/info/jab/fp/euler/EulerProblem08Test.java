package info.jab.fp.euler;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

import info.jab.fp.euler.EulerProblem08;
import info.jab.fp.euler.utils.BaseEulerProblemTest;

public class EulerProblem08Test extends BaseEulerProblemTest implements IEulerTestable {

    @Test
    @Override
    public void given_JavaSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem08 problem = new EulerProblem08();

        assertThat(problem.JavaSolution(4)).isEqualTo(5_832);
        assertThat(problem.JavaSolution(13)).isEqualTo(euler.getAnswerToLong(8));
    }

    @Test
    @Override
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem08 problem = new EulerProblem08();

        assertThat(problem.JavaStreamSolution(4)).isEqualTo(5_832);
        assertThat(problem.JavaStreamSolution(13)).isEqualTo(euler.getAnswerToLong(8));
    }

}