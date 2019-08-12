package org.fundamentals.fp.playground;

import io.vavr.control.Option;
import io.vavr.control.Try;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@Fork(2)
@Warmup(iterations = 3, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 20, time = 5, timeUnit = TimeUnit.SECONDS)
public class OptionalBenchmark2 {

    private String getAddressForBenchmark() {
        return (new Random().nextInt(10) % 2 == 0)
                ? "https://as.com"
                : "http://localhost:20123/pepe-el-marismenho";
    }

    private @Nullable URL getURLImperative(String address) {
        try {
            return new URL(address);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Try<URL> getTryURL(String address) {
        return Try.of(() -> new URL(address))
                .onFailure(Throwable::printStackTrace)
                .recover(ex -> null);
    }

    private Option<URL> getOptionURL(String address) {
        return Try.of(() -> new URL(address))
                .map(u -> Option.some(u))
                .onFailure(Throwable::printStackTrace)
                .recover(ex -> Option.none())
                .get();
    }

    private Optional<URL> getOptionalTryVAVR(String address) {
        return Try.of(() -> new URL(address))
                .map(u -> Optional.of(u))
                .onFailure(Throwable::printStackTrace)
                .recover(ex -> Optional.empty())
                .get();
    }

    private Optional<URL> getOptionalURL(String address) {
        try {
            return Optional.of(new URL(address));
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    @Benchmark
    public void using_nullable() {
        getURLImperative(getAddressForBenchmark());
    }

    @Benchmark
    public void getTryURL() {
        getTryURL(getAddressForBenchmark());
    }

    @Benchmark
    public void using_try_option() {
        getOptionURL(getAddressForBenchmark());
    }

    @Benchmark
    public void using_try_optional() {
        getOptionalTryVAVR(getAddressForBenchmark());
    }

    @Benchmark
    public void using_optional() {
        getOptionalURL(getAddressForBenchmark());
    }

    public static void main(String[] args) throws Exception {

        new Runner(new OptionsBuilder()
                .include(OptionalBenchmark2.class.getName())
                .shouldDoGC(true)
                .mode(Mode.Throughput)
                .resultFormat(ResultFormatType.JSON)
                .result("jmh-results-optional.json")
                .addProfiler(GCProfiler.class)
                .addProfiler(JmhFlightRecorderProfiler.class)
                .build()).run();
    }
}