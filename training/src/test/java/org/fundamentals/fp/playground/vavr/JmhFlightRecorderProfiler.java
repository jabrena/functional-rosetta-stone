package org.fundamentals.fp.playground.vavr;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.profile.ExternalProfiler;
import org.openjdk.jmh.results.AggregationPolicy;
import org.openjdk.jmh.results.Aggregator;
import org.openjdk.jmh.results.BenchmarkResult;
import org.openjdk.jmh.results.Result;
import org.openjdk.jmh.results.ResultRole;

public final class JmhFlightRecorderProfiler implements ExternalProfiler {

    private static final String DUMP_FOLDER = System.getProperty("jmh.stack.profiles", System.getProperty("user.dir") + "/");

    private static final String DEFAULT_OPTIONS = System.getProperty("jmh.fr.options", "settings=profile");

    private static volatile String benchmarkName;

    private volatile String dumpFile;

    @Override
    public Collection<String> addJVMInvokeOptions(final BenchmarkParams params) {
        return Collections.emptyList();
    }

    public static String benchmarkName() {
        return benchmarkName;
    }


    @Override
    public Collection<String> addJVMOptions(final BenchmarkParams params) {
        final String id = params.id();
        benchmarkName = id;
        dumpFile = DUMP_FOLDER + '/' + id + ".jfr";
        String flightRecorderOptions = DEFAULT_OPTIONS + ",dumponexit=true,filename=" + dumpFile;
        return Arrays.asList(
                "-XX:+FlightRecorder",
                "-XX:+UnlockDiagnosticVMOptions",
                "-XX:+DebugNonSafepoints",
                "-XX:StartFlightRecording=" + flightRecorderOptions);
    }

    @Override
    public void beforeTrial(final BenchmarkParams benchmarkParams) {
        final List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
    }

    @Override
    public boolean allowPrintOut() {
        return true;
    }

    @Override
    public boolean allowPrintErr() {
        return false;
    }


    @Override
    public String getDescription() {
        return "Java Flight Recording profiler runs for every benchmark.";
    }

    @Override
    public Collection<? extends Result> afterTrial(final BenchmarkResult bp, final long l,
                                                   final File file, final File file1) {
        NoResult r = new NoResult("Profile saved to " + dumpFile + ", results: " + bp
                                          + ", stdOutFile = " + file + ", stdErrFile = " + file1);
        return Collections.singleton(r);
    }

    private static final class NoResult extends Result<NoResult> {
        private static final long serialVersionUID = 1L;

        private final String output;

        NoResult(final String output) {
            super(ResultRole.SECONDARY, "JFR", of(Double.NaN), "N/A", AggregationPolicy.SUM);
            this.output = output;
        }

        @Override
        protected Aggregator<NoResult> getThreadAggregator() {
            return new NoResultAggregator();
        }

        @Override
        protected Aggregator<NoResult> getIterationAggregator() {
            return new NoResultAggregator();
        }

        private static class NoResultAggregator implements Aggregator<NoResult> {

            @Override
            public NoResult aggregate(final Collection<NoResult> results) {
                StringBuilder agg = new StringBuilder();
                for (NoResult r : results) {
                    agg.append(r.output);
                }
                return new NoResult(agg.toString());
            }
        }
    }

    @Override
    public String toString() {
        return "JmhFlightRecorderProfiler{" + "dumpFile=" + dumpFile + '}';
    }

}