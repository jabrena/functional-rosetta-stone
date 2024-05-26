package info.jab.fp.stream;

import java.util.stream.IntStream;

import static org.assertj.core.api.BDDAssertions.then;
import org.junit.jupiter.api.Test;

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
    public void given_implementation_when_verify_first_100_numbers_then_Ok() {

        IntStream.rangeClosed(1, 100).boxed()
                .skip(1)
                .forEach(i -> {
                    //LOGGER.info("{}\n", i);
                    LotharCollatzConjecture implementation = new LotharCollatzConjecture(i);
                    then(implementation.verify()).isTrue();
                });
    }

}
