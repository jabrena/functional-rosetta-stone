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
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.BDDAssertions.then;

@Slf4j
public class LatencyProblem08Test {

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

    private static LatencyProblem08.Config getDefaultConfig() {

        //Given
        final String address = "http://localhost:8090/indian-instance1";
        final ExecutorService executor = Executors.newFixedThreadPool(10,
                r -> {
                    Thread thread = new Thread(r);
                    thread.setName("MyExecutor");
                    return thread;
                });
        final int timeout = 2;

        //When
        return new LatencyProblem08.Config(address, executor, timeout);
    }

    @Test
    public void given_JavaStreamSolution_when_happyPath_then_expectedResultsTest() {

        //Given
        wireMockServer.stubFor(get(urlEqualTo("/indian-instance1"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                .withStatus(200)
                .withBodyFile("latency-problem8/indian.json")));

        LatencyProblem08 problem = new LatencyProblem08(getDefaultConfig());

        //When
        var result = problem.JavaStreamSolution();

        //Then
        then(result).isEqualTo(Option.of(
                List.of("Shiva", "Ganapati", "Krishna", "Saraswati", "Lakshmi", "Durga Devi", "Indra", "Agni")));
    }

    @Test
    public void given_JavaStreamSolution_when_forceRateLimiter_then_expectedResultsTest() {

        //Given

        final ExecutorService testExecutor = Executors.newFixedThreadPool(10,
                r -> {
                    Thread thread = new Thread(r);
                    thread.setName("MyTestExecutor");
                    return thread;
                });

        wireMockServer.stubFor(get(urlEqualTo("/indian-instance1"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("latency-problem8/indian.json")
                        .withLogNormalRandomDelay(900, 0.25)));

        LatencyProblem08.Config config = getDefaultConfig();
        config.setTimeout(2);
        LatencyProblem08 problem = new LatencyProblem08(config);

        //When

        Function<Integer, CompletableFuture<Option<List<String>>>> callAsync = (iteration) -> {

            LOGGER.info("Thread: {} {}", Thread.currentThread().getName(), iteration);
            return CompletableFuture
                    .supplyAsync(() -> problem.JavaStreamSolution(), testExecutor)
                    .orTimeout(5, TimeUnit.SECONDS)
                    .handle((response, ex) -> {
                        if(response.isDefined()) {
                            return response;
                        }
                        LOGGER.warn("kata {}", ex.getLocalizedMessage());
                        return Option.some(new ArrayList<>());
                    });
        };

        int limit = 10;
        List<CompletableFuture<Option<List<String>>>> requests = IntStream.rangeClosed(1, limit).boxed()
                .map(i -> callAsync.apply(i))
                .collect(Collectors.toUnmodifiableList());

        List<Option<List<String>>> list = requests.stream()
                .map(c -> {
                    return Try.of(() -> c.join())
                            //.onFailure(ex -> LOGGER.error(ex.getLocalizedMessage(), ex))
                            .getOrElse(Option.none());
                })
                .collect(Collectors.toList());

        list.stream()
                .forEach(o -> {
                    LOGGER.info(o.toString());
                });

        //Then
        //then(result).isEqualTo(Option.none());
    }

}
