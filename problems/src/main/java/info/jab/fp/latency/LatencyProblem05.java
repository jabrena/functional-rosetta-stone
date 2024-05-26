package info.jab.fp.latency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import info.jab.fp.euler.IEulerType3;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;

import static info.jab.fp.latency.SimpleCurl.fetch;
import static info.jab.fp.latency.SimpleCurl.log;
import static java.util.stream.Collectors.toList;

import java.io.IOException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Feature: Load Balancing
 *
 * Scenario: Consume a REST Greek God Service
 *     Given a 5 instances of the same REST API about Greek gods
 *     When  the client sends the request
 *     And   execute a load balancing policy to distribute the traffic
 *     Then  return all gods starting with `a`
 *
 * - Try to test the solution without any Internet call
 * - Review the timeout for Every connection.
 * - Review the load balancing options
 * - REST API 1: https://my-json-server.typicode.com/jabrena/latency-problems/greek
 *
 */
public class LatencyProblem05 implements IEulerType3<List<String>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LatencyProblem04.class);

    public static record Config(List<String> list,Executor executor, int timeout) {};

    private final Config config;

    public LatencyProblem05(info.jab.fp.latency.LatencyProblem05.Config config) {
        this.config = config;
    }

    @Override
    public List<String> JavaSolution() {
        return null;
    }

    Function<String, Optional<URL>> toURL = address -> {
        try {
            URL url = new URL(address);
            return Optional.of(url);
        } catch (MalformedURLException ex) {
            LOGGER.error(ex.getLocalizedMessage(), ex);
            return Optional.empty();
        }
    };

    record Tuple2(URL param1, Config param2) {}

    Function<Tuple2, CompletableFuture<String>> fetchAsync = (tuple) -> {

        LOGGER.info("Thread: {}", Thread.currentThread().getName());
        return CompletableFuture
                .supplyAsync(() -> fetch.andThen(log).apply(tuple.param1()), tuple.param2().executor())
                .exceptionally(ex -> {
                    LOGGER.error(ex.getLocalizedMessage(), ex);
                    return "FETCH_BAD_RESULT";
                })
                .completeOnTimeout("[\"FETCH_BAD_RESULT_TIMEOUT\"]", tuple.param2().timeout(), TimeUnit.SECONDS);
    };

    Function<String, List<String>> serialize = (param) -> {
        try {
                ObjectMapper objectMapper = new ObjectMapper();
                List<String> deserializedData = objectMapper.readValue(param, new TypeReference<List<String>>() {});
                return deserializedData;
        } catch(IOException ex) {
                LOGGER.error("Bad Serialization process", ex);
                throw new RuntimeException("Bad Serialization process", ex);
        }
    };

    Predicate<String> godStartingByA = s -> s.toLowerCase().charAt(0) == 'a';

    Function<List<String>, List<URL>> validAddress = list -> list.stream()
            .map(toURL)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(toList());

    Function<Config, Tuple2> loadBalance = config -> {
        List<URL> validAddressList = validAddress.apply(config.list());
        Integer index = new Random().nextInt(validAddressList.size());
        return new Tuple2(validAddressList.get(index), config);
    };

    @Override
    public List<String> JavaStreamSolution() {

        return loadBalance
                .andThen(fetchAsync)
                .andThen(CompletableFuture::join)
                .andThen(serialize)
                .andThen(l -> l.stream()
                        .filter(godStartingByA)
                        .peek(System.out::println)
                        .collect(toList()))
                .apply(config);
    }

}


