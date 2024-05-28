package info.jab.fp.dailyepsilon;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * https://en.wikipedia.org/wiki/Quinary
 */
public class Problem20211224 {

    public static void main(String[] args) {

        Function<Integer, String> baseConversion = (number) -> {
            // Parse the number with source radix
            // and return in specified radix(base)
            final int sBase = 10;
            final int dBase = 5;
            return Integer.toString(
                Integer.parseInt(String.valueOf(number), sBase), dBase);
        };

        var iterations = 1000;
        var digitLimit = 2;

        var resultList = IntStream.range(1, iterations).boxed()
            .map(baseConversion)
            .takeWhile((s) -> s.length() <= digitLimit)
            .collect(Collectors.toUnmodifiableList());

        System.out.print("Result: " + resultList.get(resultList.size() - 1));
    }
}
