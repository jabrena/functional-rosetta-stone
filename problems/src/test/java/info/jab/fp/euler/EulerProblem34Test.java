package info.jab.fp.euler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import info.jab.fp.euler.EulerProblem34;
import info.jab.fp.euler.utils.BaseEulerProblemTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem34Test extends BaseEulerProblemTest {

    @Test
    public void given_problem_when_javaStreamSolution_then_expectedResultsTest(){

        EulerProblem34 problem = new EulerProblem34();

        assertThat(problem.isCurious.test(145L)).isTrue();
        //assertThat(problem.isCurious.test(40585L)).isTrue();
        //assertThat(problem.javaStreamSolution()).isEqualTo(euler.getAnswerToLong(34));
    }

}