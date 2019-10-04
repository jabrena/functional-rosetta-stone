package org.fundamentals.fp.playground.vavr;

import io.vavr.Function2;
import java.util.List;
import java.util.function.Function;

public class EquationalReasoning {

    public static void main(String[] args) {

        var list = List.of(80, 90, 100);

        Function<List<Integer>, Integer> sum = l -> l.stream()
                .reduce(0, (i1, i2) -> i1 + i2);
        Function2<Integer, Integer, Integer> divide =
                (i1, i2) -> i1 / i2;

        System.out.println(divide.apply(sum.apply(list), list.size()));
    }

}
