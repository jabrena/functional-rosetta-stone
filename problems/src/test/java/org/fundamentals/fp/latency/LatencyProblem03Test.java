package org.fundamentals.fp.latency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.vavr.Tuple2;
import io.vavr.control.Try;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.fundamentals.fp.euler.IEulerTestable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.fundamentals.fp.latency.LatencyProblem03.GODS;
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

    private List<String> loadJsonFile(String file) {

        return Try.of(() -> {
            ObjectMapper objectMapper = new ObjectMapper();
            String readContent = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                    .getResource("__files/" + file)
                    .toURI())));

            List<String> deserializedData = objectMapper.readValue(readContent, new TypeReference<List<String>>() {});
            return deserializedData;
        })
        .getOrElseThrow(ex -> {
            LOGGER.error("Problem loading JSON file", ex);
            throw new RuntimeException(ex);
        });
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

        //Given
        wireMockServer.stubFor(get(urlEqualTo("/greek"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("greek.json")));

        wireMockServer.stubFor(get(urlEqualTo("/nordic"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("nordic.json")));

        wireMockServer.stubFor(get(urlEqualTo("/roman"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("roman.json")));

        EnumMap<GODS, List<String>> extectedGodListMap = new EnumMap<>(GODS.class);
        extectedGodListMap.put(GREEK, loadJsonFile("greek.json"));
        extectedGodListMap.put(ROMAN, loadJsonFile("roman.json"));
        extectedGodListMap.put(NORDIC, loadJsonFile("nordic.json"));

        //When
        EnumMap<GODS, String> godMap = new EnumMap<>(GODS.class);
        godMap.put(GREEK, "http://localhost:8090/greek");
        godMap.put(ROMAN, "http://localhost:8090/roman");
        godMap.put(NORDIC, "http://localhost:8090/nordic");

        LatencyProblem03 problem = new LatencyProblem03(godMap, executor, TIMEOUT);

        Function<GODS, CompletableFuture<Tuple2<GODS, List<String>>>> callAsync = god -> {

            LOGGER.info("Thread: {}", Thread.currentThread().getName());
            return CompletableFuture
                    .supplyAsync(() -> {
                        return new Tuple2<>(god, problem.JavaStreamSolutionAsync(god));
                    }, executor)
                    .exceptionally(ex -> {
                        LOGGER.error(ex.getLocalizedMessage(), ex);
                        return new Tuple2<>(god, List.of("FETCH_BAD_RESULT"));
                    })
                    .completeOnTimeout(
                            new Tuple2<>(god, List.of("FETCH_BAD_RESULT_TIMEOUT"))
                            ,TIMEOUT, TimeUnit.SECONDS);
        };

        Predicate<Tuple2<GODS, List<String>>> assertResult = t -> extectedGodListMap.get(t._1).equals(t._2);

        //Then
        IntStream.rangeClosed(1, 256).boxed()
                .forEach(i -> {
                    LOGGER.info("Test iteration: {}", i);
                    List<CompletableFuture<Tuple2<GODS, List<String>>>> futureCallList = List.of(GREEK, ROMAN, NORDIC).stream()
                            .map(callAsync)
                            .collect(toList());

                    assertThat(futureCallList.stream()
                            .map(CompletableFuture::join)
                            .filter(assertResult)
                            .count()).isEqualTo(GODS.values().length);
                });

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

}
