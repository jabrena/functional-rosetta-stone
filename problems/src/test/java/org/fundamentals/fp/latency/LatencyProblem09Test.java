package org.fundamentals.fp.latency;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.vavr.control.Option;
import io.vavr.control.Try;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.BDDAssertions.then;

@Disabled
@Slf4j
public class LatencyProblem09Test {

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

    private static LatencyProblem09.Config getDefaultConfig() {

        //Given
        final String address = "http://localhost:8090/indian-instance1";
        final ExecutorService executor = Executors.newFixedThreadPool(10,
                r -> {
                    Thread thread = new Thread(r);
                    thread.setName("MyExecutor");
                    return thread;
                });
        final int timeout = 20;

        //When
        return new LatencyProblem09.Config(address, executor, timeout);
    }

    @Test
    public void given_JavaStreamSolution_when_happyPath_then_expectedResultsTest() {

        //Given
        wireMockServer.stubFor(get(urlEqualTo("/indian-instance1"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                .withStatus(200)
                .withFixedDelay(10000)
                .withBodyFile("latency-problem8/indian.json")));

        LatencyProblem09 problem = new LatencyProblem09(getDefaultConfig());

        //When
        var result = problem.JavaStreamSolution();

        //Then
        then(result).isEqualTo(Option.of(
                List.of("Shiva", "Ganapati", "Krishna", "Saraswati", "Lakshmi", "Durga Devi", "Indra", "Agni")));
    }

}
