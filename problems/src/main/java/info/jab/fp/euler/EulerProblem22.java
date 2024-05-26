package info.jab.fp.euler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Problem 22: Names score
 * https://projecteuler.net/problem=22
 *
 * Using names.txt (right click and 'Save Link/Target As...'), a 46K text file
 * containing over five-thousand first names, begin by sorting it into alphabetical order.
 * Then working out the alphabetical value for each name, multiply this value by its alphabetical position
 * in the list to obtain a name score.
 *
 * For example, when the list is sorted into alphabetical order, COLIN,
 * which is worth 3 + 15 + 12 + 9 + 14 = 53, is the 938th name in the list.
 * So, COLIN would obtain a score of 938 × 53 = 49714.
 *
 * What is the total of all the name scores in the file?
 * 
 */
public class EulerProblem22 {

    Function<List<String>, List<String>> split = list -> list.stream()
            .flatMap(element -> Pattern.compile(",").splitAsStream(element))
            .map(l -> l.replaceAll("\"", ""))
            .collect(toList());

    Function<String, List<String>> load = fileName -> {
        try {
            return Files.lines(Paths.get(
                    getClass().getClassLoader().getResource(fileName).toURI()))
                    .collect(Collectors.toList());
        } catch (IOException | URISyntaxException | NullPointerException ex) {
            System.out.println(ex.getLocalizedMessage());
            return Collections.emptyList();
        }
    };

    /*
    Function<String, List<String>> load = fileName -> Try.of(() -> Files.lines(Paths.get(
            getClass().getClassLoader().getResource(fileName).toURI()))
            .collect(toList()))
            .onFailure(System.out::println)
            .getOrElse(Collections.emptyList());
             */

    record Tuple2(Integer param1, String param2) {}

    Function<List<String>, List<Tuple2>> addIndex = list -> {

        AtomicInteger index = new AtomicInteger(0);

        return list.stream()
                .map(s -> new Tuple2(index.incrementAndGet(), String.valueOf(s)))
                .toList();
    };

    Function<String, List<String>> loadFile = fileName -> this.load.andThen(this.split).apply(fileName);

    Function<String, List<Long>> toDigits = name -> name.chars()
            .map(c -> c - 'A' + 1)
            .mapToLong(i -> Long.valueOf(i))
            .boxed()
            .collect(toList());

    Function<List<Long>, Long> sumDigits = digits -> digits.stream()
            .reduce(0L, Long::sum);

    Function<List<Tuple2>, Long> sum = list -> list.stream()
            .map(item -> {
                return toDigits
                .andThen(sumDigits)
                .apply(String.valueOf(item.param2())) * Long.valueOf(String.valueOf(item.param1()));
            })
            .reduce(0L, Long::sum);

    public long javaStreamSolution() {

        return loadFile
                .andThen(addIndex)
                .andThen(sum)
                .apply("euler/p022_names.txt");
    }

}
