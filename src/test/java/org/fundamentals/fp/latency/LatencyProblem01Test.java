package org.fundamentals.fp.latency;

import java.math.BigInteger;

import com.github.tomakehurst.wiremock.WireMockServer;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.fundamentals.fp.euler.IEulerTestable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
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

    @Test
    @Override
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest() {

        wireMockServer.stubFor(get(urlEqualTo("/greek"))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBodyFile("greek.json")));

        wireMockServer.stubFor(get(urlEqualTo("/nordic"))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBodyFile("nordic.json")));

        wireMockServer.stubFor(get(urlEqualTo("/roman"))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBodyFile("roman.json")));

        final List<String> listOfGods = List.of(
                "http://localhost:8090/greek",
                "http://localhost:8090/nordic",
                "http://localhost:8090/roman");

        LatencyProblem01 problem = new LatencyProblem01();

        assertThat(problem.JavaStreamSolution(listOfGods)).isEqualTo(new BigInteger("78179288397447443426"));
    }

    @Test
    public void given_JavaStreamSolutionAsync_when_executeMethod_then_expectedResultsTest() {

        wireMockServer.stubFor(get(urlEqualTo("/greek"))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBodyFile("greek.json")));

        wireMockServer.stubFor(get(urlEqualTo("/nordic"))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBodyFile("nordic.json")));

        wireMockServer.stubFor(get(urlEqualTo("/roman"))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBodyFile("roman.json")));

        final List<String> listOfGods = List.of(
                "http://localhost:8090/greek",
                "http://localhost:8090/nordic",
                "http://localhost:8090/roman");

        LatencyProblem01 problem = new LatencyProblem01();

        assertThat(problem.JavaStreamSolutionAsync(listOfGods)).isEqualTo(new BigInteger("78179288397447443426"));
    }

    @Test
    public void given_JavaStreamSolutionAsync2_when_executeMethod_then_expectedResultsTest() {

        wireMockServer.stubFor(get(urlEqualTo("/greek"))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBodyFile("greek.json")));

        wireMockServer.stubFor(get(urlEqualTo("/nordic"))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBodyFile("nordic.json")));

        wireMockServer.stubFor(get(urlEqualTo("/roman"))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBodyFile("roman.json")));

        final List<String> listOfGods = List.of(
                "http://localhost:8090/greek",
                "http://localhost:8090/nordic",
                "http://localhost:8090/roman");

        LatencyProblem01 problem = new LatencyProblem01();

        assertThat(problem.JavaStreamSolutionAsync2(listOfGods)).isEqualTo(new BigInteger("78179288397447443426"));
    }

    @Tag("endtoend")
    @Test
    public void given_JavaStreamSolutionAsync2_when_executeMethod_then_expectedResultsEndToEndTest() {

        final List<String> listOfGodsOriginal = List.of(
                "http://my-json-server.typicode.com/jabrena/latency-problems/greek",
                "http://my-json-server.typicode.com/jabrena/latency-problems/nordic",
                "http://my-json-server.typicode.com/jabrena/latency-problems/roman");

        LatencyProblem01 problem = new LatencyProblem01();

        assertThat(problem.JavaStreamSolutionAsync2(listOfGodsOriginal)).isEqualTo(new BigInteger("78179288397447443426"));
    }

    @Override
    public void given_VAVRSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Disabled
    @Test
    @Override
    public void given_ReactorSolution_when_executeMethod_then_expectedResultsTest() {

        wireMockServer.stubFor(get(urlEqualTo("/greek"))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBodyFile("greek.json")));

        wireMockServer.stubFor(get(urlEqualTo("/nordic"))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBodyFile("nordic.json")));

        wireMockServer.stubFor(get(urlEqualTo("/roman"))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBodyFile("roman.json")));

        final List<String> listOfGods = List.of(
                "http://localhost:8090/greek",
                "http://localhost:8090/nordic",
                "http://localhost:8090/roman");

        LatencyProblem01 problem = new LatencyProblem01();

        StepVerifier
                .create(problem.ReactorSolution(listOfGods))
                .expectNext(new BigInteger("78179288397447443426"))
                .expectComplete()
                .verify();
    }

    @Override
    public void given_RxJavaSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Override
    public void given_KotlinSolution_when_executeMethod_then_expectedResultsTest() {

    }

}
