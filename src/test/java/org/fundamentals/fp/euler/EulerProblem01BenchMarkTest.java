package org.fundamentals.fp.euler;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.results.BenchmarkResult;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.openjdk.jmh.runner.options.VerboseMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fundamentals.fp.training.first.BookServiceBenchMarkTest.St.AVERAGE_EXPECTED_TIME;

public class EulerProblem01BenchMarkTest {

    @State(Scope.Benchmark)
    public static class St {
        EulerProblem01 problem = new EulerProblem01();
        Long limit = 1000l;
        static final double AVERAGE_EXPECTED_TIME = 100; // expected max 100 milliseconds
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

    private Options initBench() {
        return new OptionsBuilder()
                .include(EulerProblem01BenchMarkTest.class.getSimpleName() + ".*") //
                .mode(Mode.AverageTime)
                .resultFormat(ResultFormatType.JSON)
                .result("target/jmh-results.json")
                .verbosity(VerboseMode.EXTRA)
                .timeUnit(TimeUnit.MILLISECONDS)
                .warmupTime(TimeValue.seconds(1))
                .measurementTime(TimeValue.milliseconds(1))
                .measurementIterations(50)
                .threads(10)
                .warmupIterations(5)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .forks(1)
                .build();
    }

    private Collection<RunResult> runBench(Options opt) throws RunnerException {
        return new Runner(opt).run();
    }

    private void assertOutputs(Collection<RunResult> results) {
        for (RunResult r : results) {
            for (BenchmarkResult rr : r.getBenchmarkResults()) {

                Mode mode = rr.getParams().getMode();
                double score = rr.getPrimaryResult().getScore();
                String methodName = rr.getPrimaryResult().getLabel();

                assertThat(mode).isEqualTo(Mode.AverageTime);
                assertThat(score).isLessThan(AVERAGE_EXPECTED_TIME);
            }
        }
    }

    @Tag("performance")
    @Test
    public void runTest() throws Exception {
        Options opt = initBench();
        Collection<RunResult> results = runBench(opt);
        assertOutputs(results);
    }

}
