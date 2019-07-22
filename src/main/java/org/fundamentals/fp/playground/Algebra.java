package org.fundamentals.fp.playground;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Function4;
import java.math.BigInteger;

public class Algebra {

    public static void main(String[] args) {

        /*
        f(x) = x + 1
        f(x,y) = x + y
        f(a,b,c,x) = a * x^2 + b*x + c
        */

        Function1<BigInteger, BigInteger>
                fx = x -> x.add(BigInteger.ONE);
        Function2<BigInteger, BigInteger, BigInteger>
                fxy = (x, y) -> x.add(y);
        Function4<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>
                fabcx = (a, b, c, x) -> a.multiply(x.pow(2)).add(b.multiply(x)).add(c);
    }

}
