package org.fundamentals.fp.euler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EulerProblem1Test {

    private EulerProblem1 problem1;

    @BeforeEach
    void init() {
        problem1 = new EulerProblem1();
    }

    @Test
    public void given_JavaSolutionProblem1_WhenLimit10_ThenSumIsExpectedTest() {

        assertThat(problem1.javaSolution(10)).isEqualTo(23L);
        assertThat(problem1.javaSolution(100)).isEqualTo(2318L);
        assertThat(problem1.javaSolution(1000)).isEqualTo(233168L);
    }

    @Test
    public void given_JavaStreamSolutionProblem1_WhenLimit10_ThenSumIsExpectedTest() {

        assertThat(problem1.javaStreamSolution(10)).isEqualTo(23L);
        assertThat(problem1.javaStreamSolution(100)).isEqualTo(2318L);
        assertThat(problem1.javaStreamSolution(1000)).isEqualTo(233168L);
    }

    @Test
    public void given_JavaStream2SolutionProblem1_WhenLimit10_ThenSumIsExpectedTest() {

        assertThat(problem1.javaStreamSolution2(10)).isEqualTo(23L);
        assertThat(problem1.javaStreamSolution2(100)).isEqualTo(2318L);
        assertThat(problem1.javaStreamSolution2(1000)).isEqualTo(233168L);
    }

    @Test
    public void given_ReactorSolutionProblem1_WhenLimit10_ThenSumIsExpectedTest() {

        StepVerifier
                .create(problem1.reactorSolution(10))
                .expectNext(23L)
                .expectComplete()
                .verify();

        StepVerifier
                .create(problem1.reactorSolution(100))
                .expectNext(2318L)
                .expectComplete()
                .verify();

        StepVerifier
                .create(problem1.reactorSolution(1000))
                .expectNext(233168L)
                .expectComplete()
                .verify();
    }

}