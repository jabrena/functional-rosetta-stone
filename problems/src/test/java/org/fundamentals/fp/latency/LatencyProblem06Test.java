package org.fundamentals.fp.latency;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.http.Fault;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.fundamentals.fp.euler.IEulerTestable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.BDDAssertions.then;

@Slf4j
public class LatencyProblem06Test implements IEulerTestable {

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

        //Given
        final List<String> list = Collections.unmodifiableList(List.of(
                //"KATAKROKER",
                "http://localhost:8090/greek-instance1"
        ));
        final ExecutorService executor = Executors.newFixedThreadPool(10,
                r -> {
                    Thread thread = new Thread(r);
                    thread.setName("MyExecutor");
                    return thread;
                });
        final int timeout = 2;

        wireMockServer.stubFor(get(urlEqualTo("/greek-instance1"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        //.withBodyFile("latency-problem5/greek.json")));
                        .withBodyFile("latency-problem5/greek.json")
                        .withLogNormalRandomDelay(850, 0.25)));

        /*
        wireMockServer.stubFor(get(urlEqualTo("/greek-instance1"))
            .willReturn(aResponse().withFault(Fault.MALFORMED_RESPONSE_CHUNK)));
        */

        //When
        LatencyProblem06.Config config = new LatencyProblem06.Config(list, executor, timeout);
        LatencyProblem06 problem = new LatencyProblem06(config);

        //Then
        then(problem.JavaStreamSolution())
                .isEqualTo(List.of("Ares", "Athena", "Apollo", "Artemis", "Aphrodite"));

        executor.shutdown();
    }

    @Override
    public void given_VAVRSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Override
    public void given_ReactorSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Override
    public void given_RxJavaSolution_when_executeMethod_then_expectedResultsTest() {

    }

    @Override
    public void given_KotlinSolution_when_executeMethod_then_expectedResultsTest() {

    }

}
