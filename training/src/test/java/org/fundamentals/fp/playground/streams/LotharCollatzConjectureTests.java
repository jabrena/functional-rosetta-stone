package org.fundamentals.fp.playground.streams;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;

@Slf4j
public class LotharCollatzConjectureTests {

    @Test
    public void given_implementation_when_verify_with_7_then_Ok() {

        LotharCollatzConjecture implementation = new LotharCollatzConjecture(7);
        then(implementation.verify()).isTrue();
    }

    @Test
    public void given_implementation_when_verify_with_23_then_Ok() {

        LotharCollatzConjecture implementation = new LotharCollatzConjecture(23);
        then(implementation.verify()).isTrue();
    }

    @Test
    public void given_implementation_when_verify_with_1000_then_Ok() {

        LotharCollatzConjecture implementation = new LotharCollatzConjecture(1000);
        then(implementation.verify()).isTrue();
    }

    @Test
    public void given_implementation_when_verify_first_1000_numbers_then_Ok() {

        IntStream.rangeClosed(1, 1000).boxed()
                .skip(1)
                .forEach(i -> {
                    LOGGER.info("{}\n", i);
                    LotharCollatzConjecture implementation = new LotharCollatzConjecture(i);
                    then(implementation.verify()).isTrue();
                });
    }

}
