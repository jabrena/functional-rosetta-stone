///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS org.assertj:assertj-core:3.21.0

package info.jab.fp.dailyepsilon;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class Problem20211229 {

    public static void main(String[] args) {
        
        Function<Long, BigDecimal> factorial = limit -> IntStream.iterate(limit.intValue(), i -> i - 1)
                .limit(limit)
                .mapToObj(BigDecimal::valueOf)
                .reduce((n1, n2) -> n1.multiply(n2))
                .get();

        var precision = 50;
        var roundingMode = RoundingMode.HALF_UP;

        var component1 = factorial.apply(11L).divide(factorial.apply(22L), precision, roundingMode);
        var component2 = factorial.apply(21L).divide(factorial.apply(10L), precision, roundingMode);
        var component3 = BigDecimal.valueOf(58);
        var result = component1.multiply(component2).multiply(component3);

        assertThat(result.doubleValue()).as("Result is: ").isEqualTo(29);
        assertThat(result)
            .usingComparator(BigDecimal::compareTo)
            .isCloseTo(BigDecimal.valueOf(29), within(BigDecimal.valueOf(0.000000001)));
    }
}

