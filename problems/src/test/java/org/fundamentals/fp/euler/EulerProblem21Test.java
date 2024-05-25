package org.fundamentals.fp.euler;


import java.util.stream.Stream;
import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem21Test extends BaseEulerProblemTest {

    @Disabled
    @Test
    public void given_problem_when_javaStreamSolution_then_expectedResultsTest(){

        EulerProblem21 problem = new EulerProblem21();

        assertThat(problem.getDivisors.apply(220L)).asList()
                .isEqualTo(Stream.of(1, 2, 4, 5, 10, 11, 20, 22, 44, 55, 110).map(i -> Long.valueOf(i)).collect(toList()));
        assertThat(problem.getDivisors.apply(284L)).asList()
                .isEqualTo(Stream.of(1, 2, 4, 71, 142).map(i -> Long.valueOf(i)).collect(toList()));
        assertThat(problem.isAmicable(220L)).isTrue();

        assertThat(problem.javaStreamSolution(10000L)).isEqualTo(euler.getAnswerToLong(21));
    }

}