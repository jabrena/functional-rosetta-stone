package org.fundamentals.fp.oxford;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.BDDAssertions.then;

@Slf4j
public class OxfordProblem1Test {

    @Test
    public void given_datafile_when_execute_getBadRecords_then_expectedResults() {

        //Given
        OxfordProblem1 problem = new OxfordProblem1();

        //When
        long count = problem.getSolution1();

        //Then
        then(count).isEqualTo(5695L);
    }

}
