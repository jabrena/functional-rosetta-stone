package org.fundamentals.fp.euler;

import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem01Test extends BaseEulerProblemTest implements IEulerTestable {

    @Test
    @Override
    public void given_JavaSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem01 problem = new EulerProblem01();

        assertThat(problem.JavaSolution(10)).isEqualTo(3 + 5 + 6 + 9);
        assertThat(problem.JavaSolution(1000)).isEqualTo(euler.getAnswerToInt(1));
    }

    @Test
    @Override
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest() {

        EulerProblem01 problem = new EulerProblem01();

        assertThat(problem.isMultiple.test(10, 5)).isTrue();
        assertThat(problem.isMultiple.test(10, 3)).isFalse();
        assertThat(problem.isMultiple3.test(6)).isTrue();
        assertThat(problem.isMultiple5.test(10)).isTrue();

        assertThat(problem.JavaStreamSolution(10)).isEqualTo(3 + 5 + 6 + 9);
        assertThat(problem.JavaStreamSolution(1000)).isEqualTo(euler.getAnswerToInt(1));
    }

}