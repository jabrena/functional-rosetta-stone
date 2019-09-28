package org.fundamentals.fp.playground;

import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

public class ListManipulation {

    public static void main(String[] args) {

        var numbers = List.of(9, 10, 3, 4, 7, 3, 4);
        var distinct = numbers.stream()
                .map(i -> i * i)
                .distinct()
                .peek(System.out::println)//Debug only
                .filter(i -> i > 50)
                .collect(toUnmodifiableList());

        System.out.println(distinct);
    }
}
