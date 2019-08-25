package org.fundamentals.fp.latency;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.vavr.control.Option;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.BDDAssertions.then;

@Slf4j
public class LatencyProblem07Test {

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

    private static LatencyProblem07.Config getDefaultConfig() {

        //Given
        final String address = "http://localhost:8090/roman-instance1";
        final ExecutorService executor = Executors.newFixedThreadPool(10,
                r -> {
                    Thread thread = new Thread(r);
                    thread.setName("MyExecutor");
                    return thread;
                });
        final int timeout = 2;
        final int maxRetryAttempts = 3;

        //When
        return new LatencyProblem07.Config(address, executor, timeout, maxRetryAttempts);
    }

    @Test
    public void given_Predicate_when_execute_then_expectedResultsTest() {

        LatencyProblem07 problem = new LatencyProblem07(getDefaultConfig());

        then(problem.godStartingByS.test("venus")).isTrue();
        then(problem.godStartingByS.test("mars")).isTrue();
        then(problem.godStartingByS.test("Neptun")).isFalse();
    }

    @Test
    public void given_JavaStreamSolution_when_happyPath_then_expectedResultsTest() {

        //Given
        wireMockServer.stubFor(get(urlEqualTo("/roman-instance1"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                .withStatus(200)
                .withBodyFile("latency-problem7/roman.json")));

        LatencyProblem07 problem = new LatencyProblem07(getDefaultConfig());

        //When
        Option<List<String>> result = problem.JavaStreamSolution();

        //Then
        then(result).isEqualTo(Option.of(List.of("Venus", "Mars")));
    }

}