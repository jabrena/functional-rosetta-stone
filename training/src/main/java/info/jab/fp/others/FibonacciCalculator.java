package info.jab.fp.others;

import java.util.function.Function;

public class FibonacciCalculator {
    public static void main(String[] args) {
        // Define the recursive Fibonacci function using the Function interface
        Function<Integer, Integer> fib = new Function<>() {
            @Override
            public Integer apply(Integer n) {
                if (n <= 1) return n;
                // Recursive call using this function reference
                return this.apply(n - 1) + this.apply(n - 2);
            }
        };

        // Calculate Fibonacci numbers
        int number = 10; // Example number
        int result = fib.apply(number);
        System.out.println("Fibonacci number for " + number + " is " + result);
    }
}
