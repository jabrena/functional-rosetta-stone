package org.fundamentals.fp.latency;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.BDDAssertions.then;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import io.vavr.control.Option;

@Disabled
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
