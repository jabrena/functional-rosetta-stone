package info.jab.fp.async;

/*
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;
import io.vavr.control.Either;
import io.vavr.control.Try;

public class WebAddressService {

    private List<String> loadData() {

        return Try.of(() -> {
            ObjectMapper objectMapper = new ObjectMapper();
            String readContent = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                    .getResource("data/webAddresses.json")
                    .toURI())));

            List<String> deserializedData = objectMapper.readValue(readContent, new TypeReference<List<String>>() {});
            return deserializedData;
        }).getOrElseThrow(ex -> {
            //LOGGER.error(ex.getLocalizedMessage(), ex);
            throw new IllegalArgumentException("It was impossible to load the data");
        });

    }

    public List<String> getRawData() {
        return loadData();
    }

    public List<String> search() {

        return loadData().stream()
                .filter(this::isValid)
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

    private boolean isValid(String address) {

        Either<Throwable, URL> result = Try.of(() -> new URL(address)).toEither();
        return Match(result).of(
                Case($Right($()), true),
                Case($Left($()), false)
        );
    }
}
 */
