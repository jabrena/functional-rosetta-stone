package info.jab.fp.java8;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StringToDigit {

    static Function<String, List<Integer>> toDigits = value -> value.chars()
            .mapToObj(s -> Integer.valueOf(s))
            .collect(Collectors.toList());

    public static void main(String[] args) {

        toDigits.apply("zeus").stream().forEach(System.out::print);
    }

}
