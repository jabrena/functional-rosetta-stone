package org.fundamentals.fp.latency;

import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;
import org.fundamentals.fp.euler.IEulerTestable;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class LatencyProblem01Test implements IEulerTestable {

    @Override
    public void given_JavaSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Test
    @Override
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest() {

        LatencyProblem01 problem = new LatencyProblem01();

        assertThat(problem.JavaStreamSolution()).isEqualTo(new BigInteger("78179288397447443426"));
    }

    @Test
    public void given_JavaStreamSolutionAsync_when_executeMethod_then_expectedResultsTest() {

        LatencyProblem01 problem = new LatencyProblem01();

        assertThat(problem.JavaStreamSolutionAsync()).isEqualTo(new BigInteger("78179288397447443426"));
    }

    @Override
    public void given_VAVRSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Override
    public void given_ReactorSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Override
    public void given_RxJavaSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Override
    public void given_KotlinSolution_when_executeMethod_then_expectedResultsTest() {

    }

}
