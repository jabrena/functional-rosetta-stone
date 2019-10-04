package org.fundamentals.fp.playground.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toUnmodifiableList;

public class PureFunctions {

    public static void main(String[] args) {

        Function<Integer, Double> impureFunc = value -> Math.random() * value;
        Function<Integer, Double> pureFunc = value -> Double.valueOf(value * value);

        BiFunction<Function<Integer, Double>, Integer, List<Double>> populate = (fun, limit) ->
                IntStream.rangeClosed(0, limit).boxed()
                        .map(fun)
                        .collect(toUnmodifiableList());

        var impureOutput = populate.apply(impureFunc, 5);
        var pureOutput = populate.apply(pureFunc, 5);

        System.out.println("Impure result: " + impureOutput); // result is inconsistent however input is same.
        System.out.println("Pure result: " + pureOutput);
    }

    private Double impureFunc(Integer value) { return Math.random() * value; };
    private Double pureFunc(Integer value) { return Double.valueOf(value * value); };

    private void imperativeStyle() {

        var impureOutput = new ArrayList<>();
        for(var i = 0; i < 5; i++){
            impureOutput.add(impureFunc(5));
        }

        var pureOutput = new ArrayList<>();
        for(var i = 0; i < 5; i++){
            pureOutput.add(pureFunc(5));
        }

        System.out.println("Impure result: " + impureOutput); // result is inconsistent however input is same.
        System.out.println("Pure result: " + pureOutput);
    }

}
