package info.jab.fp.latency;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.WireMockServer;

import info.jab.fp.euler.IEulerTestable;
import info.jab.fp.latency.LatencyProblem01;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import reactor.test.StepVerifier;

public class LatencyProblem01Test implements IEulerTestable {

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

    @Override
    public void given_JavaSolution_when_executeMethod_then_expectedResultsTest() {

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

    @Test
    @Override
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest() {

        final int TIMEOUT = 2;

        ExecutorService executor = Executors.newFixedThreadPool(10,
                r -> {
                    Thread thread = new Thread(r);
                    thread.setName("MyExecutor");
                    return thread;
                });

        loadStubs();

        final List<String> listOfGods = List.of(
                "http://localhost:8090/greek",
                "http://localhost:8090/roman",
                "http://localhost:8090/nordic");

        LatencyProblem01 problem = new LatencyProblem01(listOfGods, executor, TIMEOUT);

        assertThat(problem.JavaStreamSolution()).isEqualTo(new BigInteger("78179288397447443426"));

        executor.shutdown();
    }

    @Disabled
    @Tag("endtoend")
    @Test
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsEndToEndTest() {

        final int TIMEOUT = 2;

        ExecutorService executor = Executors.newFixedThreadPool(10,
                r -> {
                    Thread thread = new Thread(r);
                    thread.setName("MyExecutor");
                    return thread;
                });

        final List<String> listOfGodsOriginal = List.of(
                "http://my-json-server.typicode.com/jabrena/latency-problems/greek",
                "http://my-json-server.typicode.com/jabrena/latency-problems/roman",
                "http://my-json-server.typicode.com/jabrena/latency-problems/nordic");

        LatencyProblem01 problem = new LatencyProblem01(listOfGodsOriginal, executor, TIMEOUT);

        assertThat(problem.JavaStreamSolution()).isEqualTo(new BigInteger("78179288397447443426"));

        executor.shutdown();
    }

}
