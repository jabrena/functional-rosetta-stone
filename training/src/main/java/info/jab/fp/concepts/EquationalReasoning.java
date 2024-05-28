package info.jab.fp.concepts;

import java.util.List;
import java.util.function.Function;

public class EquationalReasoning {

    public static void main(String[] args) {

        var list = List.of(80, 90, 100);

        Function<List<Integer>, Integer> sum = l -> l.stream()
                .reduce(0, (i1, i2) -> i1 + i2);
        Function<List<Integer>, Integer> divide = l ->
                sum.apply(l) / l.size();

        System.out.println(divide.apply(list));
    }

}
