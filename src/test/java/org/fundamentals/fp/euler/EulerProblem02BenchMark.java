package org.fundamentals.fp.euler;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

public class EulerProblem02BenchMark {

    @State(Scope.Thread)
    public static class St {
        EulerProblem02 problem = new EulerProblem02();
        Long limit = 4_000_000L;
    }

    @Benchmark
    public void javaSolution(St st) {
        st.problem.javaSolution(st.limit);
    }

    @Benchmark
    public void javaStreamSolution(St st) {
        st.problem.javaStreamSolution(st.limit);
    }

    @Benchmark
    public void VAVRSolution(St st) {
        st.problem.VAVRSolution(st.limit);
    }

    @Benchmark
    public void ReactorSolution(St st) {
        st.problem.ReactorSolution(st.limit);
    }

    @Benchmark
    public void KotlinSolution(St st) {
        st.problem.KotlinSolution(st.limit);
    }

}
