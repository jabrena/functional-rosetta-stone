package org.fundamentals.fp.latency;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

public class LatencyProblem01Benchmark {

    @State(Scope.Thread)
    public static class St {
        LatencyProblem01 problem = new LatencyProblem01();
    }

    @Benchmark
    public void JavaStreamSolution(St st) {
        st.problem.JavaStreamSolution();
    }

    @Benchmark
    public void JavaStreamSolutionAsync(St st) {
        st.problem.JavaStreamSolutionAsync();
    }

}
