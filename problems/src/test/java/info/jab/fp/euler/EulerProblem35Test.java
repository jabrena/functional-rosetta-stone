package info.jab.fp.euler;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

import info.jab.fp.euler.EulerProblem35;
import info.jab.fp.euler.utils.BaseEulerProblemTest;

public class EulerProblem35Test extends BaseEulerProblemTest implements IEulerTestable {

    @Override
    public void given_JavaSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Test
    @Override
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem35 problem = new EulerProblem35();

        assertThat(Stream.of("functional programming")
                .flatMap(s -> List.of(s.split(" ")).stream())
                .map(s -> s.substring(0,1))
                .map(String::toUpperCase)
                .reduce("", (s1, s2) -> s1 + s2))
                .isEqualTo("FP");

        //assertThat(problem.JavaStreamSolution(100)).isEqualTo(13);
        //assertThat(problem.JavaStreamSolution(1000)).isEqualTo(euler.getAnswerToInt(35));
    }

}