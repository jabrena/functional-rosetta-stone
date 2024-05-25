package info.jab.fp.euler;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import info.jab.fp.euler.EulerProblem02;

public class EulerProblem02Benchmark {

    @State(Scope.Thread)
    public static class St {
        EulerProblem02 problem = new EulerProblem02();
        Long limit = 4_000_000L;
    }

    @Benchmark
    public void JavaSolution(St st) {
        st.problem.JavaSolution(st.limit);
    }

    @Benchmark
    public void JavaStreamSolution(St st) {
        st.problem.JavaStreamSolution(st.limit);
    }
}
