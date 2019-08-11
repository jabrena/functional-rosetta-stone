package org.fundamentals.fp;

import static io.vavr.control.Option.none;

import io.vavr.control.Option;
import io.vavr.control.Try;
import java.net.URL;
import javax.annotation.Nullable;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread) @Fork(1)
@Warmup(iterations = 3, time = 3)
@Measurement(iterations = 5, time = 5)
public class OptionalBenchmark {
    private URL url;
    @Setup public void prepare() throws Exception { url = new URL("http://localhost:1234");}
    @TearDown public void cleanUp() { url = null;}

    @Benchmark public Try<Option<URL>> using_try_optional() {
        return Try.of(() -> url).map(Option::some).onFailure(Throwable::printStackTrace).recover(ex -> none());
    }

    @Benchmark public Try<URL> using_try() {
        return Try.of(() -> url).onFailure(Throwable::printStackTrace).recover(ex -> null);
    }

    @Benchmark public @Nullable URL using_nullable() {
        try { return url; } catch (Exception ex) { ex.printStackTrace(); return null; }
    }

    public static void main(String[] args) throws Exception {
        new Runner(new OptionsBuilder()
                .include(OptionalBenchmark.class.getName())
                .resultFormat(ResultFormatType.JSON)
                .result("target/jmh-results-optional.json")
                .addProfiler(GCProfiler.class)
                .build()).run();
    }
}
