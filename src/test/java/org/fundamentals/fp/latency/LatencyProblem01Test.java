package org.fundamentals.fp.latency;

import java.math.BigInteger;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;
import org.fundamentals.fp.euler.IEulerTestable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class LatencyProblem01Test implements IEulerTestable {

    WireMockServer wireMockServer;

    @BeforeEach
    public void setup () {
        wireMockServer = new WireMockServer(8090);
        wireMockServer.start();
        setupStub();
    }

    @AfterEach
    public void teardown () {
        wireMockServer.stop();
    }

    @Test
    private void setupStub() {
        wireMockServer.stubFor(get(urlEqualTo("/an/endpoint"))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBodyFile("books.json")));
    }

    @Test
    public void given_RunningWiremock_when_receiveRequest_then_returnStubsTest() {
        given().
        when().
        get("http://localhost:8090/an/endpoint").
        then().
        assertThat().statusCode(200);
    }


    @Override
    public void given_JavaSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Test
    @Override
    public void given_JavaStreamSolution_when_executeMethod_then_expectedResultsTest() {

        LatencyProblem01 problem = new LatencyProblem01();

        assertThat(problem.JavaStreamSolution()).isEqualTo(new BigInteger("78179288397447443426"));
    }

    @Test
    public void given_JavaStreamSolutionAsync_when_executeMethod_then_expectedResultsTest() {

        LatencyProblem01 problem = new LatencyProblem01();

        assertThat(problem.JavaStreamSolutionAsync()).isEqualTo(new BigInteger("78179288397447443426"));
    }

    @Test
    public void given_JavaStreamSolutionAsync2_when_executeMethod_then_expectedResultsTest() {

        LatencyProblem01 problem = new LatencyProblem01();

        assertThat(problem.JavaStreamSolutionAsync2()).isEqualTo(new BigInteger("78179288397447443426"));
    }

    @Override
    public void given_VAVRSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Test
    @Override
    public void given_ReactorSolution_when_executeMethod_then_expectedResultsTest() {

        LatencyProblem01 problem = new LatencyProblem01();

        StepVerifier
                .create(problem.ReactorSolution())
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
