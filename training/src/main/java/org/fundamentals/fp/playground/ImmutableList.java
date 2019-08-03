package org.fundamentals.fp.playground;

import java.util.List;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toUnmodifiableList;

public class ImmutableList {

    public static void main(String[] args) {

        Supplier<List<Integer>> retrieveList = () -> List.of(1, 2, 3).stream()
                .peek(System.out::println)
                .collect(toUnmodifiableList()); //Java 10
                //.collect(Collectors.collectingAndThen(
                //    Collectors.toList(),
                //    x -> Collections.unmodifiableList(x))); // Java 8

        var list = retrieveList.get();
        list.add(1); //Katacroker
    }
}
