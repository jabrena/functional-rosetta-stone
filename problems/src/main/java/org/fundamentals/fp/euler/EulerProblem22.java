package org.fundamentals.fp.euler;


import io.vavr.Tuple2;
import io.vavr.collection.CharSeq;
import io.vavr.collection.Iterator;
import io.vavr.collection.Stream;
import io.vavr.control.Try;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

/**
 * Names score
 * Problem 22
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

    Function<String, List<String>> load = fileName -> Try.of(() -> Files.lines(Paths.get(
            getClass().getClassLoader().getResource(fileName).toURI()))
            .collect(toList()))
            .onFailure(System.out::println)
            .getOrElse(Collections.emptyList());

    Function<List<String>, List<Tuple2>> addIndex = list -> {

        AtomicInteger index = new AtomicInteger(0);
        //io.vavr.collection.List<Tuple2<Integer, String>> collect =

        return Stream.of(list)
                .map(s -> new Tuple2(index.incrementAndGet(), String.valueOf(s)))
                .toJavaList();
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
                return toDigits.andThen(sumDigits).apply(String.valueOf(item._2)) * Long.valueOf(String.valueOf(item._1));
            })
            .reduce(0L, Long::sum);

    public long javaStreamSolution() {
        return loadFile.andThen(addIndex).andThen(sum).apply("euler/p022_names.txt");
    }

    public long VAVRSolution() {
        return readLines(file("euler/p022_names.txt"))
                .map(l -> l.replaceAll("\"", ""))
                .flatMap(l -> Stream.of(l.split(",")))
                .sorted()
                .zipWithIndex()
                .map(t -> nameScore(t._1, t._2 + 1))
                .sum().longValue();
    }

    long nameScore(String name, long position) {
        return CharSeq.of(name)
                .map(c -> c - 'A' + 1)
                .sum().longValue() * position;
    }

    private Stream<String> readLines(File file) {
        try {
            return Stream.ofAll(new Iterator<String>() {

                final Scanner scanner = new Scanner(file);

                @Override
                public boolean hasNext() {
                    final boolean hasNext = scanner.hasNextLine();
                    if (!hasNext) {
                        scanner.close();
                    }
                    return hasNext;
                }

                @Override
                public String next() {
                    return scanner.nextLine();
                }
            });
        } catch (FileNotFoundException e) {
            return Stream.empty();
        }
    }

    private File file(String fileName) {
        final URL resource = getClass().getClassLoader().getResource(fileName);
        if (resource == null) {
            throw new RuntimeException("resource not found");
        }
        return new File(resource.getFile());
    }
}
