package org.fundamentals.fp.euler;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.math.BigInteger;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class UtilsTest {


    @Test
    public void given_JavaStreamSolutionProblem2_when_generateFibonacciSequence_then_returnExpectedSequenceTest() {

        final List<Long> expectedFibonacci100 = List.of(1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L, 55L, 89L);

        assertThat(Utils.JavaStreams.fibonacci().skip(1)
                .mapToLong(BigInteger::longValue)
                .takeWhile(x -> x <= 100)
                .mapToObj(x -> Long.parseLong(String.valueOf(x))))
                .isEqualTo(expectedFibonacci100);
    }

    @Test void give_JavaStreamFactorialTest() {

        assertThat(Utils.JavaStreams.factorial.apply(10L)).isEqualTo(BigInteger.valueOf(3628800));
    }

    @Test
    public void given_VAVRSolutionProblem2_when_generateFibonacciSequence_then_returnExpectedSequenceTest() {

        final List<Long> expectedFibonacci100 = List.of(1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L, 55L, 89L);

        assertThat(Utils.VAVR.fibonacci()
                .map(BigInteger::longValue)
                .drop(2)
                .takeWhile(f -> f <= 100)
                .collect(toList()))
                .isEqualTo(expectedFibonacci100);
    }

    @Test
    public void given_ReactorSolutionProblem2_when_generateFibonacciSequence_then_returnExpectedSequenceTest() {

        final List<Long> expectedFibonacci10 = List.of(1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L, 55L, 89L);

        StepVerifier
                .create(Utils.Reactor.fibonacci().takeWhile(x-> x <= 100))
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

}