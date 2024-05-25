package info.jab.fp.latency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import java.net.URL;
import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Stream;
//import net.jcip.annotations.Immutable;
//import net.jcip.annotations.ThreadSafe;

import static info.jab.fp.latency.SimpleCurl.fetch;
import static java.util.stream.Collectors.toUnmodifiableList;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

//@ThreadSafe
//@Immutable
public class LatencyProblem03 {

    private static final Logger LOGGER = LoggerFactory.getLogger(LatencyProblem03.class);

    public enum GODS {
        GREEK,
        ROMAN,
        NORDIC
    }

    private EnumMap<GODS, String> godMap;
    private ExecutorService executor;
    private int TIMEOUT;

    public LatencyProblem03(EnumMap<GODS, String> godMap, ExecutorService executor, int TIMEOUT) {
        this.godMap = godMap;
        this.executor = executor;
        this.TIMEOUT = TIMEOUT;
    }

    Function<String, URL> toURL = address -> Try.of(() ->
            new URL(address)).getOrElseThrow(ex -> {
        LOGGER.error(ex.getLocalizedMessage(), ex);
        throw new RuntimeException("Bad address", ex);
    });

    Function<URL, String> callAsync = url -> {

        LOGGER.info("Thread: {}", Thread.currentThread().getName());
        return CompletableFuture
                .supplyAsync(() -> fetch.apply(url), executor)
                .exceptionally(ex -> {
                    LOGGER.error(ex.getLocalizedMessage(), ex);
                    return "FETCH_BAD_RESULT";
                })
                .completeOnTimeout(
                        "FETCH_BAD_RESULT_TIMEOUT"
                        ,TIMEOUT, TimeUnit.SECONDS).join();
    };

    Function<String, Stream<String>> serialize = param -> Try.of(() -> {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> deserializedData = objectMapper.readValue(param, new TypeReference<List<String>>() {});
        return deserializedData.stream();
    }).getOrElseThrow(ex -> {
        LOGGER.error("Bad Serialization process", ex);
        throw new RuntimeException(ex);
    });

    public List<String> JavaStreamSolutionAsync(GODS god) {

        return toURL
                .andThen(callAsync)
                .andThen(serialize)
                .apply(godMap.get(god))
                .collect(toUnmodifiableList());
    }

}
