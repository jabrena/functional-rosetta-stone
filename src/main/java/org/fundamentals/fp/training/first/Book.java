package org.fundamentals.fp.training.first;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

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
