package org.fundamentals.fp.latency;

import com.github.tomakehurst.wiremock.WireMockServer;
import java.util.concurrent.TimeUnit;
import org.fundamentals.fp.euler.JmhFlightRecorderProfiler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class LatencyProblemBenchmarkTest {

    WireMockServer wireMockServer;

    @BeforeEach
    public void setup () {
        wireMockServer = new WireMockServer(8090);
        wireMockServer.start();
    }

    @AfterEach
    public void teardown () {
        wireMockServer.stop();
    }

    private void loadStubs() {

        wireMockServer.stubFor(get(urlEqualTo("/greek"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("greek.json")));

        wireMockServer.stubFor(get(urlEqualTo("/roman"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("roman.json")));

        wireMockServer.stubFor(get(urlEqualTo("/nordic"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("nordic.json")));
    }

    @Tag("performance")
    @Test
    public void given_latencyProblemSolutions_when_runMethods_then_expectedBehavioursTest() throws RunnerException {

        loadStubs();

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
                .forks(5)
                .jvmArgs("-Xmx6144m", "-Xms6144m",
                        "-Xlog:gc+stats",
                        //"-XX:+UnlockExperimentalVMOptions", "-XX:+UseShenandoahGC", //"-XX:+ShenandoahAllocationTrace",
                        "-XX:-UseBiasedLocking",
                        "-XX:+ExplicitGCInvokesConcurrent")
                        //"-XX:+DisableExplicitGC")
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
