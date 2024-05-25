package info.jab.fp.euler;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import info.jab.fp.euler.EulerProblem10;

public class EulerProblem10Benchmark {

    @State(Scope.Thread)
    public static class St {
        EulerProblem10 problem = new EulerProblem10();
        Long limit = 10l;
    }

    //@Benchmark
    //public void JavaSolution(St st) {
    //    st.problem.JavaSolution(st.limit);
    //}

    @Benchmark
    public void JavaStreamSolution(St st) {
        st.problem.JavaStreamSolution(st.limit);
    }

}
