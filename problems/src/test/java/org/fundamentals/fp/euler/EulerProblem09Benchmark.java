package org.fundamentals.fp.euler;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

public class EulerProblem09Benchmark {

    @State(Scope.Thread)
    public static class St {
        EulerProblem09 problem = new EulerProblem09();
        Long limit = 1000l;
    }

    //@Benchmark
    public void JavaSolution(St st) {
        st.problem.JavaSolution(st.limit);
    }

    //@Benchmark
    public void JavaStreamSolution(St st) {
        st.problem.JavaStreamSolution(st.limit);
    }

}
