///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS org.assertj:assertj-core:3.21.0

package info.jab.fp.dailyepsilon;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class Problem20211231 {

    public static void main(String[] args) {
        
        var roundingMode = RoundingMode.HALF_UP;

        var result = BigDecimal.valueOf(30 * Math.PI)
            .divide(BigDecimal.valueOf(3), roundingMode);

        assertThat(result)
            .usingComparator(BigDecimal::compareTo)
            .isCloseTo(BigDecimal.valueOf(31.41), within(BigDecimal.valueOf(0.01)));
    }
}

