package info.jab.fp.latency;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.WireMockServer;

import info.jab.fp.euler.IEulerTestable;
import info.jab.fp.latency.LatencyProblem05;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class LatencyProblem05Test implements IEulerTestable {

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
                "KATAKROKER",
                "http://localhost:8090/greek-instance1",
                "http://localhost:8090/greek-instance2",
                "http://localhost:8090/greek-instance3",
                "http://localhost:8090/greek-instance4",
                "http://localhost:8090/greek-instance5"));
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
                        .withBodyFile("latency-problem5/greek.json")));

        wireMockServer.stubFor(get(urlEqualTo("/greek-instance2"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("latency-problem5/greek.json")));

        wireMockServer.stubFor(get(urlEqualTo("/greek-instance3"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("latency-problem5/greek.json")));

        wireMockServer.stubFor(get(urlEqualTo("/greek-instance4"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("latency-problem5/greek.json")));

        wireMockServer.stubFor(get(urlEqualTo("/greek-instance5"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("latency-problem5/greek.json")));

        //When
        LatencyProblem05.Config config = new LatencyProblem05.Config(list, executor, timeout);
        LatencyProblem05 problem = new LatencyProblem05(config);

        //Then
        then(problem.JavaStreamSolution())
                .isEqualTo(List.of("Ares", "Athena", "Apollo", "Artemis", "Aphrodite"));

        executor.shutdown();
    }

}
