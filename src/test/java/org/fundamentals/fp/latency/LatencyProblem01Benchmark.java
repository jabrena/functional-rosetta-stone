package org.fundamentals.fp.latency;

import java.util.List;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

public class LatencyProblem01Benchmark {

    @State(Scope.Thread)
    public static class St {
        LatencyProblem01 problem = new LatencyProblem01();

        final List<String> listOfGods = List.of(
                "http://my-json-server.typicode.com/jabrena/latency-problems/greek",
                "http://my-json-server.typicode.com/jabrena/latency-problems/nordic",
                "http://my-json-server.typicode.com/jabrena/latency-problems/roman");

    }

    @Benchmark
    public void JavaStreamSolution(St st) {
        st.problem.JavaStreamSolution(st.listOfGods);
    }

    @Benchmark
    public void JavaStreamSolutionAsync(St st) {
        st.problem.JavaStreamSolutionAsync(st.listOfGods);
    }

    @Benchmark
    public void JavaStreamSolutionAsync2(St st) {
        st.problem.JavaStreamSolutionAsync2(st.listOfGods);
    }

}
