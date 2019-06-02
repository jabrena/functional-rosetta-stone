package org.fundamentals.fp.training.first;

import java.math.BigDecimal;
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

public class BookServiceBenchMarkTest {

    @State(Scope.Benchmark)
    public static class St {
        final BigDecimal priceLimit = new BigDecimal("30.00");
        BookService bookStore = new BookService();
        static final double AVERAGE_EXPECTED_TIME = 100; // expected max 100 milliseconds
    }

    /**
     * Benchmark run with Junit
     * @throws Exception
     */
    @Tag("performance")
    @Test
    public void runTest() throws Exception {
        Options opt = initBench();
        Collection<RunResult> results = runBench(opt);
        assertOutputs(results);
    }

    /**
     * JMH benchmark
     * @param st
     */
    @Benchmark
    public void classicSearch(St st) {
        st.bookStore.search(st.priceLimit);
    }

    /**
     * JMH benchmark
     * @param st
     */
    @Benchmark
    public void newSearch(St st) {
        st.bookStore.searchNew(st.priceLimit);
    }

    /**
     *
     * @param opt
     * @return
     * @throws RunnerException
     */
    private Collection<RunResult> runBench(Options opt) throws RunnerException {
        return new Runner(opt).run();
    }

    /**
     * Assert benchmark results that are interesting for us
     * Asserting test mode and average test time
     * @param results
     */
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

    private Options initBench() {
        return new OptionsBuilder()
                .include(BookServiceBenchMarkTest.class.getSimpleName() + ".*") //
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

}
