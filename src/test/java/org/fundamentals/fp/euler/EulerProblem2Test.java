package org.fundamentals.fp.euler;

import java.util.List;
import org.assertj.core.api.AssertionsForClassTypes;
import org.fundamentals.fp.euler.EulerProblem2;
import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

public class EulerProblem2Test extends BaseEulerProblemTest {

    @Test
    public void given_JavaSolutionProblem2_when_generateFibonacciSequence_then_returnExpectedSequenceTest() {

        EulerProblem2 problem2 = new EulerProblem2();

        final List<Long> expectedFibonacci100 = List.of(1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L, 55L, 89L);

        assertThat(problem2.getJavaFibonaccyTerms(100L))
                .isEqualTo(expectedFibonacci100);
    }

    @Test
    public void given_JavaSolutionProblem2_WhenFibonacciSerie_ThenValueExpectedTest() {

        EulerProblem2 problem2 = new EulerProblem2();
        AssertionsForClassTypes.assertThat(problem2.javaSolution(100)).isEqualTo(2 + 8 + 34);
        AssertionsForClassTypes.assertThat(problem2.javaSolution(4_000_000)).isEqualTo(euler.getAnswerToLong(2));
    }

    @Test
    public void given_JavaStreamSolutionProblem2_when_generateFibonacciSequence_then_returnExpectedSequenceTest() {

        EulerProblem2 problem2 = new EulerProblem2();

        final List<Long> expectedFibonacci100 = List.of(1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L, 55L, 89L);

        assertThat(problem2.getJavaStreamFibonaccyTerms(100))
                .isEqualTo(expectedFibonacci100);
    }

    @Test
    public void given_JavaStreamSolutionProblem_when_sumEven_then_returnsExpectedValue() {

        EulerProblem2 problem2 = new EulerProblem2();

        assertThat(problem2.javaStreamSolution(100)).isEqualTo(2 + 8 + 34);
        assertThat(problem2.javaStreamSolution(4_000_000))
                .isEqualTo(euler.getAnswerToLong(2));
    }

    @Test
    public void given_VAVRSolutionProblem2_when_generateFibonacciSequence_then_returnExpectedSequenceTest() {

        EulerProblem2 problem2 = new EulerProblem2();

        final List<Long> expectedFibonacci100 = List.of(1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L, 55L, 89L);

        assertThat(problem2.getVAVRFibonaccyTerms(100))
                .isEqualTo(expectedFibonacci100);
    }

    @Test
    public void given_VAVRSolutionProblem_when_sumEven_then_returnsExpectedValue() {

        EulerProblem2 problem2 = new EulerProblem2();

        assertThat(problem2.VAVRSolution(100)).isEqualTo(2 + 8 + 34);
        assertThat(problem2.VAVRSolution(4_000_000L))
                .isEqualTo(euler.getAnswerToLong(2));
    }

    @Test
    public void given_ReactorSolutionProblem2_when_generateFibonacciSequence_then_returnExpectedSequenceTest() {

        EulerProblem2 problem2 = new EulerProblem2();

        final List<Long> expectedFibonacci10 = List.of(1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L, 55L, 89L);

        StepVerifier
                .create(problem2.getReactorFibonaccyTerms(100))
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
                .create(problem2.ReactorSolution(100))
                .expectNext(44L)
                .expectComplete()
                .verify();

        StepVerifier
                .create(problem2.ReactorSolution(4_000_000))
                .expectNext(euler.getAnswerToLong(2))
                .expectComplete()
                .verify();

    }

}