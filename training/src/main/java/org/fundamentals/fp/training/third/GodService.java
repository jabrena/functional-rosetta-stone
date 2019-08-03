package org.fundamentals.fp.training.third;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GodService {

    private List<String> loadData() {

        return Try.of(() -> {
            ObjectMapper objectMapper = new ObjectMapper();
            String readContent = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                    .getResource("data/greekGods.json")
                    .toURI())));

            List<String> deserializedData = objectMapper.readValue(readContent, new TypeReference<List<String>>() {});
            return deserializedData;
        }).getOrElseThrow(ex -> {
            LOGGER.error(ex.getLocalizedMessage(), ex);
            throw new IllegalArgumentException("It was impossible to load the data");
        });

    }

    public List<String> searchGodsStartingWithH() {

        return loadData().stream()
                .filter(god -> god.toLowerCase().startsWith("h"))
                .skip(1)
                .limit(3)
                //.peek(System.out::println)
                .collect(Collectors.toList());
    }

    public Optional<List<String>> searchGodsWithLargeNames() {

        return Optional.of(loadData().stream()
                .filter(god -> god.length() > 10)
                .collect(Collectors.toList()));
    }

    public Option<List<String>> searchGodsWithLargeNames2() {

        return Option.of(loadData().stream()
                .filter(god -> god.length() > 10)
                .collect(Collectors.toList()));
    }

    public Either<Throwable, List<String>> searchGodsWithLargeNames3() {

        List<String> list = loadData().stream()
                .filter(god -> god.length() > 10)
                .collect(Collectors.toList());

        if(list.size() > 0 ) {
            return Either.right(list);
        }else {
            return Either.left(new RuntimeException("No values"));
        }
    }

}
