package org.fundamentals.fp.euler;

import java.util.concurrent.TimeUnit;
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

public class EulerProblemBenchmarkTest {

    @Tag("performance")
    @Test
    public void given_eulerProblemSolutions_when_runMethods_then_expectedBehavioursTest() throws RunnerException {

        Options options = new OptionsBuilder()
                .include(EulerProblem01BenchMark.class.getSimpleName())
                .include(EulerProblem02BenchMark.class.getSimpleName())
                .include(EulerProblem03BenchMark.class.getSimpleName())
                .include(EulerProblem04BenchMark.class.getSimpleName())
                .include(EulerProblem05BenchMark.class.getSimpleName())
                .include(EulerProblem06BenchMark.class.getSimpleName())
                .include(EulerProblem07BenchMark.class.getSimpleName())
                .include(EulerProblem08BenchMark.class.getSimpleName())
                //.include(EulerProblem09BenchMark.class.getSimpleName())
                .include(EulerProblem10BenchMark.class.getSimpleName())
                .resultFormat(ResultFormatType.JSON)
                .result("target/jmh-results.json")
                //.verbosity(VerboseMode.EXTRA)
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.MILLISECONDS)
                .warmupTime(TimeValue.seconds(5))
                .measurementTime(TimeValue.milliseconds(1))
                .measurementIterations(20)
                .threads(Runtime.getRuntime().availableProcessors())
                .warmupIterations(1)
                //.shouldFailOnError(true)
                .shouldDoGC(true)
                .forks(5)
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
