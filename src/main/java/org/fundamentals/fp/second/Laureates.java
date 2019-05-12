package org.fundamentals.fp.second;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Laureates {
    private String id;
    private String firstname;
    private String surname;
    private String motivation;
    private String share;

}
