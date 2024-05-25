package info.jab.fp.euler;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import info.jab.fp.euler.EulerProblem04;

public class EulerProblem04Benchmark {

    @State(Scope.Thread)
    public static class St {
        EulerProblem04 problem = new EulerProblem04();
        int min = 100;
        int max = 999;
    }

    //@Benchmark
    public void JavaSolution(St st) {
        st.problem.JavaSolution(st.min, st.max);
    }

    @Benchmark
    public void JavaStreamSolution(St st) {
        st.problem.JavaStreamSolution(st.min, st.max);
    }

}
