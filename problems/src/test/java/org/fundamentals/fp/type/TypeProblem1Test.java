package org.fundamentals.fp.type;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class TypeProblem1Test {

    @Test
    public void given_javaStreamSolution_when_executeMethod_then_returnExpectedResult() {

        //Given
        Person person = new Person();

        TypeProblem1 problem = new TypeProblem1(person);

        then(problem.getInsuranceName()).isNotNull();
    }

}
