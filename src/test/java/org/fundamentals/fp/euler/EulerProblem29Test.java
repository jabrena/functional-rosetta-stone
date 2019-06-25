package org.fundamentals.fp.euler;

import java.util.List;
import java.util.stream.Stream;
import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem29Test extends BaseEulerProblemTest {

    @Test
    public void given_problem_when_javaStreamSolution_then_expectedResultsTest(){

        EulerProblem29 problem = new EulerProblem29();

        List<Long> expectedList = Stream.of(4, 8, 9, 16, 25, 27, 32, 64, 81, 125, 243, 256, 625, 1024, 3125)
                .map(i -> Long.valueOf(i))
                .collect(toList());

        assertThat(problem.generateSerie.apply(5L).collect(toList())).isEqualTo(expectedList);
        assertThat(problem.javaStreamSolution(5L)).isEqualTo(15L);
        //assertThat(problem.javaStreamSolution(100L)).isEqualTo(euler.getAnswerToLong(29));
    }

    @Test
    public void given_problem_when_VAVRSolution_then_expectedResultsTest(){

        EulerProblem29 problem = new EulerProblem29();

        assertThat(problem.VAVRSolution(5L)).isEqualTo(15L);
        assertThat(problem.VAVRSolution(100L)).isEqualTo(euler.getAnswerToLong(29));
    }

}