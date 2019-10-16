package org.fundamentals.fp.playground.java8;

import java.util.function.BiFunction;

public class ReferencialTransparency {

    static int add(int a, int b) {
        return a + b;
    }

    static int mult(int a, int b) {
        return a * b;
    }

    public static void main(String[] args) {

        int result = add(2, mult(3, 4));

        BiFunction<Integer, Integer, Integer> add = (i1, i2) -> i1 + i2;
        BiFunction<Integer, Integer, Integer> mult = (i1, i2) -> i1 * i2;

        int result2 = add.apply(2, mult.apply(3, 4));
        int result3 = add.apply(2, 12);
        System.out.println(result2);
        System.out.println(result3);
    }

}
