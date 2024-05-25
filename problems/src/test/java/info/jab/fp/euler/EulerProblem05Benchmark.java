package info.jab.fp.euler;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import info.jab.fp.euler.EulerProblem05;

public class EulerProblem05Benchmark {

    @State(Scope.Thread)
    public static class St {
        EulerProblem05 problem = new EulerProblem05();
        Long limit = 20l;
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
