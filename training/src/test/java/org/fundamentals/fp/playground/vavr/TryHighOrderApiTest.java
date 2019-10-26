package org.fundamentals.fp.playground.vavr;

import io.vavr.PartialFunction;
import io.vavr.control.Try;

import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Predicate;

import static io.vavr.API.*;
import static io.vavr.control.Try.failure;
import static io.vavr.control.Try.success;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TryHighOrderApiTest {

    private final Try<String> successfulStringyNumber = success("10");
    private final Try<String> failedStringyNumber = failure(new RuntimeException());

    @Test
    public void try_value_can_be_mapped_to_another_value_with_a_high_order_function() {

        Function<String, Integer> parseNumber = x -> Integer.valueOf(x);

        Try<Integer> maybeResult = successfulStringyNumber.map(parseNumber);
        maybeResult.forEach(result -> assertEquals(new Integer(10), result));

        Try<Integer> noResult = failedStringyNumber.map(parseNumber);
        noResult.forEach(result -> fail("should not apply the function"));
    }

    @Test
    public void try_value_can_be_mapped_to_another_value_with_a_high_order_function_that_returns_also_try() {

        Function<String, Try<Integer>> maybeParseNumber = x -> Try.of(() -> Integer.parseInt(x));

        Try<Integer> maybeResult = successfulStringyNumber.flatMap(maybeParseNumber);
        maybeResult.forEach(result -> assertEquals(new Integer(10), result));

        Try<Integer> noResult = failedStringyNumber.flatMap(maybeParseNumber);
        noResult.forEach(result -> fail("should not apply the function"));
    }

    @Test
    public void try_values_can_be_filtered_with_a_predicate() {

        Predicate<String> containsDigitsOnly = x -> x.matches("[0-9]+");
        successfulStringyNumber.filter(containsDigitsOnly).forEach(result -> assertEquals("10", result));

        Predicate<String> containsLettersOnly = x -> x.matches("[a-zA-Z]+");
        successfulStringyNumber.filter(containsLettersOnly).forEach(result -> fail("should not pass the filter"));
    }

    @Test
    public void option_values_can_be_collected_with_partial_functions_aka_pattern_matching() {

        // Can't use the original type Try<Integer>, has to be a Serializable for VAVR to be able to generate the Case
        // but this is usually hidden, here is extracted as a variable for demonstration purposes
        Function<Serializable, Integer> parseNumber = x -> Integer.parseInt((String) x);
        PartialFunction<Serializable, Integer> anyNumber = Case($(Some($())), parseNumber);
        PartialFunction<Serializable, Integer> onlyFive = Case($(Some("10")), parseNumber);

        successfulStringyNumber.collect(anyNumber).forEach(result -> assertEquals(new Integer(10), result));
        failedStringyNumber.collect(anyNumber).forEach(result -> fail("should not be picked up by the partial function"));
        successfulStringyNumber.collect(onlyFive).forEach(result -> fail("should not be picked up by the partial function"));
    }

}