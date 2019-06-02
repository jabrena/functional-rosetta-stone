package org.fundamentals.fp.euler.problem2;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

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
    public void given_JavaSolution2Problem2_when_generateFibonacciSequence_then_returnExpectedSequenceTest() {

        EulerProblem2 problem2 = new EulerProblem2();

        final List<BigInteger> expectedFibonacci10 = List.of(
                new BigInteger("1"),
                new BigInteger("2"),
                new BigInteger("3"),
                new BigInteger("5"),
                new BigInteger("8"),
                new BigInteger("13"),
                new BigInteger("21"),
                new BigInteger("34"),
                new BigInteger("55"),
                new BigInteger("89"));

        assertThat(problem2.getJavaFibonaccyTerms2(10L))
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
    public void given_JavaSolutionProblem2_when_sumEven_then_returnsExpectedValue() {

        EulerProblem2 problem2 = new EulerProblem2();

        assertThat(problem2.javaSolutionFibonacciEvenSum2(10L)).isEqualTo(new BigInteger("44"));
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
    public void given_VAVRSolutionProblem2_when_generateFibonacciSequence_then_returnExpectedSequenceTest() {

        EulerProblem2 problem2 = new EulerProblem2();

        final List<Long> expectedFibonacci10 = List.of(1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L, 55L, 89L);

        assertThat(problem2.getVAVRFibonaccyTerms(10L))
                .isEqualTo(expectedFibonacci10);
    }

    @Test
    public void given_VAVRSolutionProblem_when_sumEven_then_returnsExpectedValue() {

        EulerProblem2 problem2 = new EulerProblem2();

        assertThat(problem2.VAVRSolutionFibonacciEvenSum(10L)).isEqualTo(44L);
        assertThat(problem2.VAVRSolutionFibonacciEvenSum(4_000_000L))
                .isEqualTo(problem2.javaSolutionFibonacciEvenSum(4_000_000L));
    }

    @Test
    public void given_ReactorSolutionProblem2_when_generateFibonacciSequence_then_returnExpectedSequenceTest() {

        EulerProblem2 problem2 = new EulerProblem2();

        final List<Long> expectedFibonacci10 = List.of(1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L, 55L, 89L);

        StepVerifier
                .create(problem2.getReactorFibonaccyTerms(10))
                .expectNext(expectedFibonacci10.get(0))
                .expectNext(expectedFibonacci10.get(1))
                .expectNext(expectedFibonacci10.get(2))
                .expectNext(expectedFibonacci10.get(3))
                .expectNext(expectedFibonacci10.get(4))
                .expectNext(expectedFibonacci10.get(5))
                .expectNext(expectedFibonacci10.get(6))
                .expectNext(expectedFibonacci10.get(7))
                .expectNext(expectedFibonacci10.get(8))
                .expectNext(expectedFibonacci10.get(9))
                .expectComplete()
                .verify();
    }

    @Test
    public void given_ReactorSolutionProblem2_when_sumEven_then_returnExpectedValueTest() {

        EulerProblem2 problem2 = new EulerProblem2();

        StepVerifier
                .create(problem2.ReactorSolutionFibonacciEvenSum(10L))
                .expectNext(44L)
                .expectComplete()
                .verify();

        StepVerifier
                .create(problem2.ReactorSolutionFibonacciEvenSum(100L))
                .expectNext(problem2.javaSolutionFibonacciEvenSum(100L))
                .expectComplete()
                .verify();

        /*
        StepVerifier
                .create(problem2.ReactorSolutionFibonacciEvenSum(4_000_000L))
                .expectNext(problem2.javaSolutionFibonacciEvenSum(4_000_000L))
                .expectComplete()
                .verify();
        */
    }

}