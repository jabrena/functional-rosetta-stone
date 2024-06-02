package info.jab.fp.concepts;

import java.util.function.LongPredicate;
import java.util.function.Supplier;
import java.util.stream.LongStream;

public class EquationalReasoningExamples {

    /**
     * Find sum of the first four odd primes
     * 
     * Problems from: https://twitter.com/Daily_Epsilon
     */
    public static void main(String[] args) {

        LongPredicate isPrime = number -> {
            return LongStream.rangeClosed(2, number / 2)
                .noneMatch(i -> number % i == 0);
        };

        Supplier<LongStream> primesGenerator = () -> {
            return LongStream.iterate(1, i -> i + 1) //Infinite Stream
                .skip(1) //1 is not a prime number
                .filter(isPrime);
        };

        LongPredicate isOdd = number -> (number % 2 != 0);
        
        var result = primesGenerator.get()
            .filter(isOdd)
            .limit(4)
            .peek(System.out::println)
            .sum();

        System.out.print("Result: " + result);
    }

}
