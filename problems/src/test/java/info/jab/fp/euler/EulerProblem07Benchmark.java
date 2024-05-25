package info.jab.fp.euler;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import info.jab.fp.euler.EulerProblem07;

public class EulerProblem07Benchmark {

    @State(Scope.Thread)
    public static class St {
        EulerProblem07 problem = new EulerProblem07();
        Integer limit = 10001;
    }

    //@Benchmark
    public void JavaSolution(St st) {
        st.problem.JavaSolution(st.limit);
    }

    @Benchmark
    public void JavaStreamSolution(St st) {
        st.problem.JavaStreamSolution(st.limit);
    }

}
