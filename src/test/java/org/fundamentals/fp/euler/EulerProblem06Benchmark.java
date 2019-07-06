package org.fundamentals.fp.euler;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

public class EulerProblem06Benchmark {

    @State(Scope.Thread)
    public static class St {
        EulerProblem06 problem = new EulerProblem06();
        Long limit = 20l;
    }

    //@Benchmark
    public void JavaSolution(St st) {
        st.problem.JavaSolution(st.limit);
    }

    @Benchmark
    public void JavaStreamSolution(St st) {
        st.problem.JavaStreamSolution(st.limit);
    }

    //@Benchmark
    public void VAVRSolution(St st) {
        st.problem.VAVRSolution(st.limit);
    }


    @Benchmark
    public void ReactorSolution(St st) {
        st.problem.ReactorSolution(st.limit);
    }

    /*
    @Benchmark
    public void KotlinSolution(St st) {
        st.problem.KotlinSolution(st.limit);
    }

    @Benchmark
    public void RxJavaSolution(St st) {
        st.problem.RxJavaSolution(st.limit);
    }
    */
}
