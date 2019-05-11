package org.fundamentals.fp.firstchallenge;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Title {

    @JsonProperty("@lang")
    private String lang;
    @JsonProperty("#text")
    private String text;

}