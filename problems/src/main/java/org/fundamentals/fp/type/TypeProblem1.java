package org.fundamentals.fp.type;

import java.util.Objects;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TypeProblem1 {

    private String DEFAULT_FALLBACK = "Not available";
    private final Person person;

    public String getInsuranceName() {

        if(!Objects.isNull(person)) {

            if(!Objects.isNull(person.getBeachHouse())) {

                return person.getBeachHouse().getInsurance().getName();
            }
        }

        return DEFAULT_FALLBACK;
    }
}
