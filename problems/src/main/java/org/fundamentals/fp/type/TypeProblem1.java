package org.fundamentals.fp.type;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TypeProblem1 {

    private String DEFAULT_FALLBACK = "Not available";

    @NonNull
    private final Person person;

    public String getInsuranceName() {

        return person.getBeachHouse()
                .flatMap(BeachHouse::getInsurance)
                .map(Insurance::getName)
                .orElse(DEFAULT_FALLBACK);
    }
}
