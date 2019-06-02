package org.fundamentals.fp.euler.problem2;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class EulerProblem2Test {

    @Test
    public void given_JavaSolutionProblem2_when_generateFibonacciSequence_then_returnExpectedSequenceTest() {

        EulerProblem2 problem2 = new EulerProblem2();

        final List<Integer> expectedFibonacci100 = List.of(1, 2, 3, 5, 8, 13, 21, 34, 55, 89);

        assertThat(problem2.getFibonaccySequence(100))
                .isEqualTo(expectedFibonacci100);
    }

}
