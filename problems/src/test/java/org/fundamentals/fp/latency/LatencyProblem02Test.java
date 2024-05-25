package org.fundamentals.fp.latency;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.fundamentals.fp.euler.IEulerTestable;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class LatencyProblem02Test implements IEulerTestable {

    @Override
    public void given_JavaSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Test
    @Override
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest() {

        LatencyProblem02 problem = new LatencyProblem02();

        assertThat(problem.JavaStreamSolution()).isEqualTo("Apollo");
    }

    @Test
    public void given_JavaStreamSolutionAsync_when_executeMethod_then_expectedResultsTest() {

        LatencyProblem02 problem = new LatencyProblem02();

        assertThat(problem.JavaStreamSolutionAsync()).isEqualTo("Apollo");
    }

    @Test
    public void given_JavaStreamSolutionAsync2_when_executeMethod_then_expectedResultsTest() {

        LatencyProblem02 problem = new LatencyProblem02();

        assertThat(problem.JavaStreamSolutionAsync2()).isEqualTo("Apollo");
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

}
