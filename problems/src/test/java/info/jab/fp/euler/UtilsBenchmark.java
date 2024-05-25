package info.jab.fp.euler;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import info.jab.fp.euler.Utils;

public class UtilsBenchmark {

    @State(Scope.Thread)
    public static class St {
        Utils problem = new Utils();
        Long limit = 1_000l;
    }

    @Benchmark
    public void JavaFactorialStreams_100(St st) {
        Utils.JavaStreams.factorialStream.apply(100L);
    }

    @Benchmark
    public void JavaFactorialTrampoline_100(St st) {
        Utils.JavaStreams.factorialTrampoline.apply(100L);
    }

    @Benchmark
    public void JavaFactorialStreams_1000(St st) {
        Utils.JavaStreams.factorialStream.apply(1000L);
    }

    @Benchmark
    public void JavaFactorialTrampoline_1000(St st) {
        Utils.JavaStreams.factorialTrampoline.apply(1000L);
    }

    @Benchmark
    public void JavaFactorialStreams_10000(St st) {
        Utils.JavaStreams.factorialStream.apply(10000L);
    }

    @Benchmark
    public void JavaFactorialTrampoline_10000(St st) {
        Utils.JavaStreams.factorialTrampoline.apply(10000L);
    }

}