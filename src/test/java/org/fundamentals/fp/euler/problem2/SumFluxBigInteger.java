package org.fundamentals.fp.euler.problem2;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.math.MathFlux;

import static org.assertj.core.api.Assertions.assertThat;

public class SumFluxBigInteger {

    @Test
    public void SumBigIntegerTest() {

        Mono<Long> longSum = MathFlux.sumLong(Flux.just(1L, 2L, 3L));

        List<BigInteger> list = Arrays.asList(
                new BigInteger("1"),
                new BigInteger("2"),
                new BigInteger("3")
        );

        BigInteger bigIntegerSum = list.stream().reduce(BigInteger.ZERO, BigInteger::add);

        assertThat(longSum.block()).isEqualTo(bigIntegerSum.longValue());

        Flux<BigInteger> sequence = Flux.fromIterable(list);

        //Doesn´t exist the method
        //MathFlux.sumBigInteger(sequence);
    }

}
