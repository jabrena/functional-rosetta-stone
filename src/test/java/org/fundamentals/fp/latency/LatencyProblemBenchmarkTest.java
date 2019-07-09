package org.fundamentals.fp.latency;

import java.util.concurrent.TimeUnit;
import org.fundamentals.fp.euler.JmhFlightRecorderProfiler;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

public class LatencyProblemBenchmarkTest {

    @Tag("performance")
    @Test
    public void given_latencyProblemSolutions_when_runMethods_then_expectedBehavioursTest() throws RunnerException {

        Options options = new OptionsBuilder()
                .include(LatencyProblem01Benchmark.class.getSimpleName())
                .resultFormat(ResultFormatType.JSON)
                .result("target/jmh-results-latency.json")
                //.verbosity(VerboseMode.EXTRA)
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.MILLISECONDS)
                .warmupTime(TimeValue.seconds(5))
                .measurementTime(TimeValue.milliseconds(1))
                .measurementIterations(10)
                .threads(Runtime.getRuntime().availableProcessors())
                .warmupIterations(1)
                //.shouldFailOnError(true)
                .shouldDoGC(true)
                .forks(2)
                .jvmArgs("-Xmx6144m", "-Xms6144m")
                //.addProfiler(StackProfiler.class)
                .addProfiler(GCProfiler.class)
                //.addProfiler(LinuxPerfProfiler.class)
                //.addProfiler(ClassloaderProfiler.class)
                //.addProfiler(CompilerProfiler.class)
                .addProfiler(JmhFlightRecorderProfiler.class)
                .build();

        new Runner(options).run();
    }

}
