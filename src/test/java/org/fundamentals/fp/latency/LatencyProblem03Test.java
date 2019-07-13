package org.fundamentals.fp.latency;

import com.github.tomakehurst.wiremock.WireMockServer;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.fundamentals.fp.euler.IEulerTestable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.fundamentals.fp.latency.LatencyProblem03.GODS.GREEK;
import static org.fundamentals.fp.latency.LatencyProblem03.GODS.NORDIC;
import static org.fundamentals.fp.latency.LatencyProblem03.GODS.ROMAN;

@Slf4j
public class LatencyProblem03Test implements IEulerTestable {

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

        LatencyProblem03 problem = new LatencyProblem03(listOfGods);

        List<String> expectedGreekList = List.of(
                "Zeus",
                "Hera",
                "Poseidon",
                "Demeter",
                "Ares",
                "Athena",
                "Apollo",
                "Artemis",
                "Hephaestus",
                "Aphrodite",
                "Hermes",
                "Dionysus",
                "Hades",
                "Hypnos",
                "Nike",
                "Janus",
                "Nemesis",
                "Iris",
                "Hecate",
                "Tyche");

        List<String> expectedRomanList = List.of(
                "Venus",
                "Mars",
                "Neptun",
                "Mercury",
                "Pluto",
                "Jupiter"
        );

        List<String> expectedNordicList = List.of(
                "Baldur",
                "Freyja",
                "Heimdall",
                "Frigga",
                "Hel",
                "Loki",
                "Njord",
                "Odin",
                "Thor",
                "Tyr"
        );

        assertThat(problem.JavaStreamSolution(GREEK)).isEqualTo(expectedGreekList);
        assertThat(problem.JavaStreamSolution(ROMAN)).isEqualTo(expectedRomanList);
        assertThat(problem.JavaStreamSolution(NORDIC)).isEqualTo(expectedNordicList);
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
