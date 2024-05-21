package org.fundamentals.fp.latency;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.BDDAssertions.then;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import com.github.tomakehurst.wiremock.http.Fault;

import io.vavr.control.Option;

public class LatencyProblem06Test {

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

    @Test
    public void given_JavaStreamSolution_when_happyPath_then_expectedResultsTest() {

        //Given
        final String address = "http://localhost:8090/greek-instance1";
        final ExecutorService executor = Executors.newFixedThreadPool(10,
                r -> {
                    Thread thread = new Thread(r);
                    thread.setName("MyExecutor");
                    return thread;
                });
        final int timeout = 2;
        final int maxRetryAttempts = 3;

        wireMockServer.stubFor(get(urlEqualTo("/greek-instance1"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                .withStatus(200)
                .withBodyFile("latency-problem6/greek.json")));

        //When
        LatencyProblem06.Config config = new LatencyProblem06.Config(address, executor, timeout, maxRetryAttempts);
        LatencyProblem06 problem = new LatencyProblem06(config);

        //Then
        then(problem.JavaStreamSolution())
                .isEqualTo(Option.of(List.of("Ares", "Athena", "Apollo", "Artemis", "Aphrodite")));

        executor.shutdown();
    }

    @Test
    public void given_JavaStreamSolution_when_forceRetry_then_expectedResultsTest() {

        //Given
        final String address = "http://localhost:8090/greek-instance1";
        final ExecutorService executor = Executors.newFixedThreadPool(10,
                r -> {
                    Thread thread = new Thread(r);
                    thread.setName("MyExecutor");
                    return thread;
                });
        final int timeout = 2;
        final int maxRetryAttempts = 3;

        wireMockServer.stubFor(get(urlEqualTo("/greek-instance1"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("latency-problem6/greek.json")
                        .withLogNormalRandomDelay(900, 0.25)));

        //When
        LatencyProblem06.Config config = new LatencyProblem06.Config(address, executor, timeout, maxRetryAttempts);
        LatencyProblem06 problem = new LatencyProblem06(config);

        //Then
        then(problem.JavaStreamSolution())
                .isEqualTo(Option.of(List.of("Ares", "Athena", "Apollo", "Artemis", "Aphrodite")));

        executor.shutdown();
    }

    @Test
    public void given_JavaStreamSolution_when_badResponse_then_expectedResultsTest() {

        //Given
        final String address = "http://localhost:8090/greek-instance1";
        final ExecutorService executor = Executors.newFixedThreadPool(10,
                r -> {
                    Thread thread = new Thread(r);
                    thread.setName("MyExecutor");
                    return thread;
                });
        final int timeout = 2;
        final int maxRetryAttempts = 3;

        wireMockServer.stubFor(get(urlEqualTo("/greek-instance1"))
            .willReturn(aResponse().withFault(Fault.MALFORMED_RESPONSE_CHUNK)));

        //When
        LatencyProblem06.Config config = new LatencyProblem06.Config(address, executor, timeout, maxRetryAttempts);
        LatencyProblem06 problem = new LatencyProblem06(config);

        //Then
        then(problem.JavaStreamSolution()).isEqualTo(Option.none());

        executor.shutdown();
    }

    @Test
    public void given_JavaStreamSolution_when_corruptedResponse_then_expectedResultsTest() {

        //Given
        final String address = "http://localhost:8090/greek-instance1";
        final ExecutorService executor = Executors.newFixedThreadPool(10,
                r -> {
                    Thread thread = new Thread(r);
                    thread.setName("MyExecutor");
                    return thread;
                });
        final int timeout = 2;
        final int maxRetryAttempts = 3;

        wireMockServer.stubFor(get(urlEqualTo("/greek-instance1"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("latency-problem6/bad_greek_response.json")));

        //When
        LatencyProblem06.Config config = new LatencyProblem06.Config(address, executor, timeout, maxRetryAttempts);
        LatencyProblem06 problem = new LatencyProblem06(config);

        //Then
        then(problem.JavaStreamSolution()).isEqualTo(Option.none());

        executor.shutdown();
    }

    @Test
    public void given_JavaStreamSolution_when_badInternalConfiguration_then_expectedResultsTest() {

        //Given
        final String address = "KATAKROKER";
        final ExecutorService executor = Executors.newFixedThreadPool(10,
                r -> {
                    Thread thread = new Thread(r);
                    thread.setName("MyExecutor");
                    return thread;
                });
        final int timeout = 2;
        final int maxRetryAttempts = 3;

        //When
        LatencyProblem06.Config config = new LatencyProblem06.Config(address, executor, timeout, maxRetryAttempts);
        LatencyProblem06 problem = new LatencyProblem06(config);

        //Then
        then(problem.JavaStreamSolution()).isEqualTo(Option.none());

        executor.shutdown();
    }

}
