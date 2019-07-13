package org.fundamentals.fp.latency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import java.net.URL;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

import static java.util.stream.Collectors.toUnmodifiableList;
import static org.fundamentals.fp.latency.SimpleCurl.fetch;

@ThreadSafe
@Immutable
@Slf4j
public class LatencyProblem03 {

    private final EnumMap<GODS, String> godMap;

    public LatencyProblem03(EnumMap<GODS, String> godMap) {
        this.godMap = godMap;
    }

    public enum GODS {
        GREEK,
        ROMAN,
        NORDIC
    }

    public List<String> JavaStreamSolution(GODS god) {

        Function<String, URL> toURL = address -> Try.of(() ->
                new URL(address)).getOrElseThrow(ex -> {
            LOGGER.error(ex.getLocalizedMessage(), ex);
            throw new RuntimeException("Bad address", ex);
        });

        Function<String, Stream<String>> serialize = param -> Try.of(() -> {
            ObjectMapper objectMapper = new ObjectMapper();
            List<String> deserializedData = objectMapper.readValue(param, new TypeReference<List<String>>() {});
            return deserializedData.stream();
        }).getOrElseThrow(ex -> {
            LOGGER.error("Bad Serialization process", ex);
            throw new RuntimeException(ex);
        });

        return toURL
                .andThen(fetch)
                .andThen(serialize)
                .apply(godMap.get(god))
                .collect(toUnmodifiableList());
    }
}
