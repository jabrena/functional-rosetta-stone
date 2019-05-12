package org.fundamentals.fp.second;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Prizes {

    private String year;
    private String category;
    private String overallMotivation;
    private List<Laureates> laureates;

}