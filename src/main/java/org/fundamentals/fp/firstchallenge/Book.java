package org.fundamentals.fp.firstchallenge;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Value;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {

    @JsonProperty("@category")
    private String category;
    @JsonProperty("title")
    private Title title;
    @JsonProperty("author")
    private String author;
    @JsonProperty("year")
    private String year;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("@cover")
    private String cover;

}
