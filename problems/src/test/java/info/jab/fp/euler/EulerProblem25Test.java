package info.jab.fp.euler;

import org.junit.jupiter.api.Test;

import info.jab.fp.euler.EulerProblem25;
import info.jab.fp.euler.utils.BaseEulerProblemTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem25Test extends BaseEulerProblemTest {

    @Test
    public void given_problem_when_javaStreamSolution_then_expectedResultsTest(){

        EulerProblem25 problem = new EulerProblem25();

        assertThat(problem.javaStreamSolution(3L)).isEqualTo(12);
        assertThat(problem.javaStreamSolution(1000L)).isEqualTo(euler.getAnswerToLong(25));
    }

}