package org.fundamentals.fp.type;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class TypeProblem1Test {

    @Test
    public void given_javaStreamSolution_when_executeMethod_then_returnExpectedResult() {

        //Given
        String DEFAULT_INSURANCE_NAME = "MyInsurance";
        Insurance insurance = new Insurance(DEFAULT_INSURANCE_NAME);
        BeachHouse house = new BeachHouse(insurance);
        Person person = new Person(house);

        TypeProblem1 problem = new TypeProblem1(person);

        then(problem.getInsuranceName()).isEqualTo(DEFAULT_INSURANCE_NAME);
    }

    @Test
    public void given_javaStreamSolution_when_PersonDoesntHaveAnyBeachHouse_then_returnExpectedResult() {

        //Given
        String DEFAULT_FALLBACK = "Not available";
        Person person = new Person(null);

        TypeProblem1 problem = new TypeProblem1(person);

        then(problem.getInsuranceName()).isEqualTo(DEFAULT_FALLBACK);
    }

}
