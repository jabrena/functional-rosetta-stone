package org.fundamentals.fp.euler.problem2;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EulerProblem2Test {

    @Test
    public void given_JavaSolutionProblem2_when_generateFibonacciSequence_then_returnExpectedSequenceTest() {

        EulerProblem2 problem2 = new EulerProblem2();

        final List<Integer> expectedFibonacci10 = List.of(1, 2, 3, 5, 8, 13, 21, 34, 55, 89);

        assertThat(problem2.getJavaFibonaccyTerms(10))
                .isEqualTo(expectedFibonacci10);
    }

    @Test void given_JavaSolutionProblem_when_sumEven_then_returnsExpectedValue() {

        EulerProblem2 problem2 = new EulerProblem2();

        Consumer<Integer> print = System.out::println;
        Predicate<Integer> isEven = number -> (number % 2) == 0;

        final List<Integer> expectedFibonacci10 = List.of(1, 2, 3, 5, 8, 13, 21, 34, 55, 89);

        final long expectedFibonnaciEvenSum10 = expectedFibonacci10.stream()
                .filter(isEven)
                .peek(print)
                .reduce(0, Integer::sum);

        assertThat(problem2.javaSolutionFibonacciEvenSum(10)).isEqualTo(44);
    }

}
