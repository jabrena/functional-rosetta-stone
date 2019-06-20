package org.fundamentals.fp.euler;

import org.fundamentals.fp.euler.utils.BaseEulerProblemTest;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem6Test extends BaseEulerProblemTest {

    @Test
    public void given_problem6_when_10_then_expectedResultsTest(){

        EulerProblem6 problem6 = new EulerProblem6();

        assertThat(problem6.javaStreamsSumSquare.apply(10L)).isEqualTo(385);
        assertThat(problem6.javaStreamsSquareSum.apply(10L)).isEqualTo(3025);
        assertThat(problem6.javaStreamsSolution(10L)).isEqualTo(2640);
        assertThat(problem6.javaStreamsSolution(100L)).isEqualTo(euler.getAnswerToLong(6));
    }

    @Test
    public void given_problem6_when_reactor10_then_expectedResultsTest(){

        EulerProblem6 problem6 = new EulerProblem6();

        assertThat(problem6.square.apply(2L)).isEqualTo(4L);

        StepVerifier
                .create(problem6.reactorSumSquare(10))
                .expectNext(385L)
                .expectComplete()
                .verify();

        StepVerifier
                .create(problem6.reactorSquareSum(10))
                .expectNext(3025L)
                .expectComplete()
                .verify();

        StepVerifier
                .create(problem6.reactorSolution(10))
                .expectNext(2640L)
                .expectComplete()
                .verify();

        StepVerifier
                .create(problem6.reactorSolution(100))
                .expectNext(euler.getAnswerToLong(6))
                .expectComplete()
                .verify();
    }

}