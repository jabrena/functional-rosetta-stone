package info.jab.fp.java8;

        /*
        f(x) = x + 1
        f(x,y) = x + y
        f(a,b,c,x) = a * x^2 + b*x + c
        */

/*
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Function4;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class AlgebraTest {

    @Test
    public void algebraTest() {

        Function1<BigInteger, BigInteger>
                fx = x -> x.add(BigInteger.ONE);
        Function2<BigInteger, BigInteger, BigInteger>
                fxy = (x, y) -> x.add(y);
        Function4<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>
                fabcx = (a, b, c, x) -> a.multiply(x.pow(2)).add(b.multiply(x)).add(c);

        then(fx.apply(BigInteger.ONE))
                .isEqualTo(new BigInteger("2"));
        then(fxy.apply(BigInteger.ONE, BigInteger.ONE))
                .isEqualTo(new BigInteger("2"));
        then(fabcx.apply(BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.TWO))
                .isEqualTo(new BigInteger("7"));
    }

}
*/
