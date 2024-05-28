package info.jab.fp.dailyepsilon;

import java.math.BigDecimal;
import java.util.function.Predicate;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Problem20211226 {

    public static void main(String[] args) {

        Predicate<Integer> isPrime = number -> {
            return LongStream.rangeClosed(2, number / 2)
                .noneMatch(i -> number % i == 0);
        };

        Predicate<Integer> isOdd = number -> (number % 2 == 0 ) ? false : true;
        
        var result = Stream.iterate(1, i -> i + 1) //Infinite Stream
            .skip(1) //1 is not a prime number
            .filter(isPrime)
            .filter(isOdd)
            .limit(4)
            .peek(System.out::println)
            .map(BigDecimal::valueOf)
            .reduce(BigDecimal.ZERO, BigDecimal::add); //Sum

        System.out.print("Result: " + result);
    }
}
