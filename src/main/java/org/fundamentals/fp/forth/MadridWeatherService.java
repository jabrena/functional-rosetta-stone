package org.fundamentals.fp.forth;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class MadridWeatherService {

    private List<WeatherMeasure> loadData() {

        return Try.of(() -> {
            ObjectMapper objectMapper = new ObjectMapper();
            String readContent = new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                    .getResource("data/madrid-weather.json")
                    .toURI())));

            List<WeatherMeasure> deserializedData = objectMapper.readValue(readContent, new TypeReference<List<WeatherMeasure>>() {});
            return deserializedData;
        }).onFailure(System.out::println)
          .getOrElse(Collections.emptyList());

    }

    public Double getAverage(){

        return loadData().stream()
                .filter(weatherMeasure -> weatherMeasure.getYear() == 2019)
                .filter(weatherMeasure -> weatherMeasure.getMonth() == 5)
                .filter(weatherMeasure -> weatherMeasure.getDay() == 12)
                .mapToDouble(weatherMeasure -> weatherMeasure.getTemperature())
                .average()
                .getAsDouble();
    }

    public Double getAverage2(){

        return loadData().stream()
                .filter(weatherMeasure -> weatherMeasure.getYear() == 2019)
                .filter(weatherMeasure -> weatherMeasure.getMonth() == 5)
                .filter(weatherMeasure -> weatherMeasure.getDay() == 12)
                .collect(Collectors.averagingDouble(weatherMeasure -> weatherMeasure.getTemperature()));
    }


}
