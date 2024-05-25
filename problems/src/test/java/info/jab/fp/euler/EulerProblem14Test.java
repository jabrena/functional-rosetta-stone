package info.jab.fp.euler;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import info.jab.fp.euler.EulerProblem14;
import info.jab.fp.euler.utils.BaseEulerProblemTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem14Test extends BaseEulerProblemTest {

    @Disabled
    @Test
    public void given_problem10_when_10_then_expectedResultsTest(){

        EulerProblem14 problem = new EulerProblem14();

        assertThat(problem.javaStreamSolution(13L)).isEqualTo(10L);
        assertThat(problem.javaStreamSolution(1_000_000L)).isEqualTo(euler.getAnswerToLong(14));
    }

}