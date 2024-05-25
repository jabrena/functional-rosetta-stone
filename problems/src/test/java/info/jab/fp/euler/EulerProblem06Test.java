package info.jab.fp.euler;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

import info.jab.fp.euler.EulerProblem06;
import info.jab.fp.euler.utils.BaseEulerProblemTest;

public class EulerProblem06Test extends BaseEulerProblemTest implements IEulerTestable {

    @Override
    public void given_JavaSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Test
    @Override
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem06 problem6 = new EulerProblem06();

        assertThat(problem6.JavaStreamSumSquare.apply(10L)).isEqualTo(385);
        assertThat(problem6.JavaStreamSquareSum.apply(10L)).isEqualTo(3025);

        assertThat(problem6.JavaStreamSolution(10L)).isEqualTo(2640);
        assertThat(problem6.JavaStreamSolution(100L)).isEqualTo(euler.getAnswerToLong(6));
    }


}