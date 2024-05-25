package info.jab.fp.euler;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import info.jab.fp.euler.EulerProblem22;
import info.jab.fp.euler.utils.BaseEulerProblemTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem22Test extends BaseEulerProblemTest {

    @Disabled
    @Test
    public void given_problem_when_javaStreamSolution_then_expectedResultsTest(){

        EulerProblem22 problem = new EulerProblem22();

        assertThat(problem.toDigits.andThen(problem.sumDigits).apply("COLIN")).isEqualTo(53);
        assertThat(problem.toDigits.andThen(problem.sumDigits).apply("COLIN") * 938).isEqualTo(49714);
        assertThat(problem.loadFile.apply("euler/p022_names.txt").stream().skip(4472).findFirst().get()).isEqualTo("COLIN");
        assertThat(problem.javaStreamSolution()).isEqualTo(euler.getAnswerToLong(22));
    }

}