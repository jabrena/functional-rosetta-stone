package org.fundamentals.fp.training.second;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Root {

    private List<Prizes> prizes;

}