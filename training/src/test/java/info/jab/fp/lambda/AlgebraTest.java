package info.jab.fp.lambda;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import java.util.function.Function;
import java.util.function.BiFunction;

import static org.assertj.core.api.BDDAssertions.then;

public class AlgebraTest {

    @Test
    public void algebraTest() {

        /*
        f(x) = x + 1
        f(x,y) = x + y
        */

        Function<BigInteger, BigInteger> fx = x -> x.add(BigInteger.ONE);
        BiFunction<BigInteger, BigInteger, BigInteger> fxy = (x, y) -> x.add(y);

        then(fx.apply(BigInteger.ONE)).isEqualTo(new BigInteger("2"));
        then(fxy.apply(BigInteger.ONE, BigInteger.ONE)).isEqualTo(new BigInteger("2"));
    }

}
