package org.fundamentals.fp.playground;

public class FizzBuzz {
    public static String fizzBuzz(int n) {
        return n % 15 == 0 ? "FizzBuzz"
                : n % 5 == 0 ? "Buzz"
                : n % 3 == 0 ? "Fizz"
                : String.valueOf(n);
    }
}