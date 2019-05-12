package org.fundamentals.fp.second;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NovelService {

    private List<Prizes> loadData() {

        return Try.of(() -> {
            ObjectMapper objectMapper = new ObjectMapper();
            String readContent = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                    .getResource("data/novel-prizes.json")
                    .toURI())));

            Root deserializedData = objectMapper.readValue(readContent, Root.class);
            return deserializedData.getPrizes();
        }).getOrElseThrow(ex -> {
              LOGGER.error(ex.getLocalizedMessage(), ex);
              throw new IllegalArgumentException("It was impossible to load the data");
          });

    }

    public Long getWinnersInPhysics() {

        return loadData().stream()
                .filter(prizes -> prizes.getCategory().equals("physics"))
                .flatMap(prizes -> prizes.getLaureates().stream())
                .count();
    }

    public Long getWinnersInPhysics2018() {

        return loadData().stream()
                .filter(prizes -> prizes.getCategory().equals("physics"))
                .filter(prizes -> Integer.parseInt(prizes.getYear()) == 2018)
                .flatMap(prizes -> prizes.getLaureates().stream())
                .count();
    }

    public Long getFirstWinnersInPhysics() {

        return Long.parseLong(loadData().stream()
                .sorted(Comparator.comparing(Prizes::getYear))
                .filter(prizes -> prizes.getCategory().equals("physics"))
                .findFirst()
                .get()
                .getYear());
    }

}
