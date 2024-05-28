package info.jab.fp.dailyepsilon;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Problem20211227 {

    public static void main(String[] args) {
        
        Function<Integer, BigDecimal> calculateIteration = (n) -> {

            var precision = new MathContext(10); // 10 Digits of precision
            var roundingMode = RoundingMode.HALF_UP; // Rounding mode
    
            return BigDecimal.valueOf(4 * n).divide(
                BigDecimal.valueOf(3).pow(n - 2, precision), roundingMode);
        };

        var iterations = 100;

        var result = IntStream.range(1, iterations).boxed()
            .map(calculateIteration)
            .reduce(BigDecimal.ZERO, BigDecimal::add); //Sum
        
        System.out.println("Result: " + result);
    }
}
