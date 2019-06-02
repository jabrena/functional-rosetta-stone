package org.fundamentals.fp.euler.problem2;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EulerProblem2Test {

    @Test
    public void given_JavaSolutionProblem2_when_generateFibonacciSequence_then_returnExpectedSequenceTest() {

        EulerProblem2 problem2 = new EulerProblem2();

        final List<Long> expectedFibonacci10 = List.of(1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L, 55L, 89L);

        assertThat(problem2.getJavaFibonaccyTerms(10L))
                .isEqualTo(expectedFibonacci10);
    }

    @Test
    public void given_JavaSolutionProblem_when_sumEven_then_returnsExpectedValue() {

        EulerProblem2 problem2 = new EulerProblem2();

        Consumer<Long> print = System.out::println;
        Predicate<Long> isEven = number -> (number % 2) == 0;

        final List<Long> expectedFibonacci10 = List.of(1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L, 55L, 89L);

        final long expectedFibonnaciEvenSum10 = expectedFibonacci10.stream()
                .filter(isEven)
                //.peek(print)
                .collect(Collectors.summingLong(Long::longValue));

        assertThat(problem2.javaSolutionFibonacciEvenSum(10L)).isEqualTo(44L);
        assertThat(problem2.javaSolutionFibonacciEvenSum(10L)).isEqualTo(expectedFibonnaciEvenSum10);
    }

    @Test
    public void given_JavaStreamSolutionProblem2_when_generateFibonacciSequence_then_returnExpectedSequenceTest() {

        EulerProblem2 problem2 = new EulerProblem2();

        final List<Long> expectedFibonacci10 = List.of(1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L, 55L, 89L);

        assertThat(problem2.getJavaStreamFibonaccyTerms(10L))
                .isEqualTo(expectedFibonacci10);
    }

    @Test
    public void given_JavaStreamSolutionProblem_when_sumEven_then_returnsExpectedValue() {

        EulerProblem2 problem2 = new EulerProblem2();

        assertThat(problem2.javaStreamSolutionFibonacciEvenSum(10L)).isEqualTo(44L);
        assertThat(problem2.javaStreamSolutionFibonacciEvenSum(4_000_000L))
                .isEqualTo(problem2.javaSolutionFibonacciEvenSum(4_000_000L));
    }

    @Test
    public void given_JavaVAVRSolutionProblem2_when_generateFibonacciSequence_then_returnExpectedSequenceTest() {

        EulerProblem2 problem2 = new EulerProblem2();

        final List<Long> expectedFibonacci10 = List.of(1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L, 55L, 89L);

        assertThat(problem2.getJavaVAVRFibonaccyTerms(10L))
                .isEqualTo(expectedFibonacci10);
    }
}