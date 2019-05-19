package org.fundamentals.fp.forth;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherMeasure {

    @JsonProperty("Year")
    private Integer year;
    @JsonProperty("Month")
    private Integer month;
    @JsonProperty("Day")
    private Integer day;
    @JsonProperty("Hour")
    private Integer hour;
    @JsonProperty("Minute")
    private Integer minute;
    @JsonProperty("Temperature")
    private Double temperature;

}