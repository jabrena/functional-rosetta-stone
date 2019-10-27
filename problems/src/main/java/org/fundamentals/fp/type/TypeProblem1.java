package org.fundamentals.fp.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TypeProblem1 {

    private final Person person;

    public String getInsuranceName() {
        return person.getBeachhouse().getInsurance().getName();
    }
}
