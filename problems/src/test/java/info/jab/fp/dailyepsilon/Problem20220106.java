///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS org.assertj:assertj-core:3.21.0

package info.jab.fp.dailyepsilon;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class Problem20220106 {

    public static void main(String[] args) {

        var iterations = 1_000_000;

        var roundingMode = RoundingMode.HALF_UP;
        var precision = new MathContext(20);

        var op1 = new BigDecimal(String.valueOf(Math.PI)).pow(2, precision);
        var op2 = IntStream.rangeClosed(1, iterations).boxed()
            .map(n -> 1 / Math.pow(n, 2))//TODO Refactor using only BigDecimal methods
            .map(String::valueOf)
            .map(BigDecimal::new)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        var result = op1.divide(op2, roundingMode);

        System.out.println(op1);
        System.out.println(op2);
        System.out.println(result);

        assertThat(result)
            .usingComparator(BigDecimal::compareTo)
            .isCloseTo(BigDecimal.valueOf(6), within(new BigDecimal("1E-5")));
    }
}

