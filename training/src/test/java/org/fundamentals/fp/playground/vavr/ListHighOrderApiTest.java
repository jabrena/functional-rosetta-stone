package org.fundamentals.fp.playground.vavr;

import io.vavr.PartialFunction;
import io.vavr.Tuple;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.control.Option.none;
import static io.vavr.control.Option.some;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ListHighOrderApiTest {

    private final List<Integer> numbers = List.of(1, 2, 3, 4, 5);
    private final List<String> letters = List.of("a", "b", "c", "d", "e");

    @Test
    public void applying_a_function_that_transforms_one_element_into_another_with_map_to_a_list() {

        Function<Integer, String> stringifyNumber = x -> x.toString();

        assertEquals(List.of("1", "2", "3", "4", "5"), numbers.map(stringifyNumber));
    }

    @Test
    public void applying_a_function_that_transforms_one_element_into_a_collection_with_flatMap_to_a_list_joins_all_the_collections_into_one() {

        Function<Integer, List<Integer>> numberAndNext = x -> List.of(x, x + 1);

        assertEquals(List.of(1, 2, 2, 3, 3, 4, 4, 5, 5, 6), numbers.flatMap(numberAndNext));
    }

    @Test
    public void filtering_with_a_predicate_returns_a_list_elements_that_satisfy_the_condition_according_to_the_filter_function() {

        Predicate<Integer> isEvenPredicate = x -> x % 2 == 0;

        assertEquals(List.of(2, 4), numbers.filter(isEvenPredicate));

        assertEquals(List.of(1, 3, 5), numbers.removeAll(isEvenPredicate));

        assertEquals(List.of(1, 3, 4, 5), numbers.removeFirst(isEvenPredicate));

        assertEquals(List.of(1, 2, 3, 5), numbers.removeLast(isEvenPredicate));

    }

    @Test
    public void find_unique_elements_in_a_list_using_a_discriminator_function() {

        Function<Integer, Boolean> isEvenFunction = x -> x % 2 == 0;

        assertEquals(List.of(1, 2), numbers.distinctBy(isEvenFunction));
    }

    @Test
    public void folding_applies_a_function_to_elements_2_by_2_until_reducing_the_list_to_a_single_element() {

        BiFunction<String, String, String> concatenate = (a, b) -> a + b;

        assertEquals("_abcde", letters.fold("_", concatenate));

        assertEquals("_abcde", letters.foldLeft("_", concatenate));

        assertEquals("abcde_", letters.foldRight("_", concatenate));

        assertEquals("abcde", letters.reduce(concatenate));
    }

    @Test
    public void min_max_maybe_return_value_from_a_list_using_a_function_for_custom_comparision_between_elements() {

        Function<String, Integer> firstCharToAsciiValue = x -> (int) x.charAt(0); // casting char to int returns ascii value

        assertEquals(some("a"), letters.minBy(firstCharToAsciiValue));

        assertEquals(some("e"), letters.maxBy(firstCharToAsciiValue));

        Function<String, Integer> firstCharToNegativeAsciiValue = x -> ((int) x.charAt(0)) * -1;

        assertEquals(some("e"), letters.minBy(firstCharToNegativeAsciiValue));

        assertEquals(none(), List.<String>empty().maxBy(firstCharToAsciiValue));
    }

    @Test
    public void lists_can_be_partitioned_by_functions_that_evaluate_conditions() {

        Function<String, Boolean> isVowelFunction = x -> x.matches("[aeiouAEIOU]");

        assertEquals(
                HashMap.of(
                        true, List.of("a", "e"),
                        false, List.of("b", "c", "d")
                ),
                letters.groupBy(isVowelFunction)
        );

        Predicate<String> isVowelPredicate = x -> x.matches("[aeiouAEIOU]");

        assertEquals(
                Tuple.of(
                        List.of("a", "e"),
                        List.of("b", "c", "d")
                ),
                letters.partition(isVowelPredicate)
        );
    }

    @Test
    public void lists_can_be_split_into_2_by_position_of_an_element_matching_a_predicate() {

        Predicate<String> isVowel = x -> x.matches("[aeiouAEIOU]");

        assertEquals(
                Tuple.of(
                        List.of("a"),
                        List.of("b", "c", "d", "e")
                ),
                letters.splitAt(isVowel.negate())
        );

        assertEquals(
                Tuple.of(
                        List.of("a", "b"),
                        List.of("c", "d", "e")
                ),
                letters.splitAtInclusive(isVowel.negate())
        );
    }

    @Test
    public void lists_can_be_sliced_in_different_ways() {

        assertEquals(new Integer(1), numbers.head());
        assertEquals(List.of(2, 3, 4, 5), numbers.tail());

        Predicate<Integer> isFour = x -> x == 4;
        Predicate<Integer> isNotFour = isFour.negate();

        assertEquals(List.of(1, 2), numbers.take(2));
        assertEquals(List.of(3, 4, 5), numbers.drop(2));

        assertEquals(List.of(1, 2, 3), numbers.takeUntil(isFour));
        assertEquals(List.of(4, 5), numbers.dropUntil(isFour));

        assertEquals(List.of(1, 2, 3), numbers.takeWhile(isNotFour));
        assertEquals(List.of(4, 5), numbers.dropWhile(isNotFour));

        assertEquals(List.of(4, 5), numbers.takeRight(2));
        assertEquals(List.of(1, 2, 3), numbers.dropRight(2));

        // no take right until :(
        assertEquals(List.of(1, 2, 3, 4), numbers.dropRightUntil(isFour));

        // no take right while :(
        assertEquals(List.of(1, 2, 3, 4), numbers.dropRightWhile(isNotFour));
    }

    @Test
    public void lists_can_be_queried_to_find_if_it_contains_an_element_satisfying_a_condition() {

        Predicate<Integer> isEven = x -> x % 2 == 0;
        Predicate<Integer> isTen = x -> x == 10;

        assertEquals(some(2), numbers.find(isEven));
        assertEquals(none(), numbers.find(isTen));

        assertEquals(some(4), numbers.findLast(isEven));
        assertEquals(none(), numbers.findLast(isTen));
    }

    @Test
    public void lists_can_be_sorted_with_customizations() {

        Function<Integer, Integer> changeSignOnOddNumbers = x -> {
            if (x % 2 != 0) return x * -1;
            else return x;
        };

        assertEquals(List.of(1, 2, 3, 4, 5), numbers.sorted());

        assertEquals(List.of(5, 3, 1, 2, 4), numbers.sortBy(changeSignOnOddNumbers));

        Comparator<Integer> descendingOrder = (x, y) -> y - x;

        assertEquals(List.of(5, 4, 3, 2, 1), numbers.sorted(descendingOrder));

        assertEquals(List.of(4, 2, 1, 3, 5), numbers.sortBy(descendingOrder, changeSignOnOddNumbers));
    }

    class Person {
        private String firstName;
        private String lastName;

        Person(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        String getFirstName() {
            return firstName;
        }

        String getLastName() {
            return lastName;
        }

    }

    @Test
    public void lists_can_be_filtered_and_transform_the_outputs_using_partial_functions() {

        List<Person> persons = List.of(
                new Person("John", "Smith"),
                new Person("Amanda", "Smith"),
                new Person("Anna", "Tucker"),
                new Person("Damian", "without last name")
        );

        // TODO: Find a way to use pattern matching extracting variables directly instead of using a guard
        PartialFunction<Person, String> firstNamesOfSmiths = Case($(person -> "Smith".equals(person.getLastName())), x -> x.getFirstName());

        assertEquals(List.of("John", "Amanda"), persons.collect(firstNamesOfSmiths));

        assertEquals(List.of("John", "Amanda"), persons.filter(p -> "Smith".equals(p.getLastName())).map(p -> p.getFirstName()));
    }

    @Test
    public void lists_can_be_combined_with_other_lists() {

        assertEquals(
                List.of(
                        Tuple.of(1, "a"),
                        Tuple.of(2, "b"),
                        Tuple.of(3, "c"),
                        Tuple.of(4, "d"),
                        Tuple.of(5, "e")

                ),
                numbers.zip(letters)
        );

        assertEquals(
                List.of(
                        Tuple.of("a", 1),
                        Tuple.of("b", 2),
                        Tuple.of("c", 3),
                        Tuple.of("x", 4),
                        Tuple.of("x", 5)
                ),
                List.of("a", "b", "c").zipAll(numbers, "x", 0)
        );

        assertEquals(
                List.of(
                        Tuple.of("a", 1),
                        Tuple.of("b", 2),
                        Tuple.of("c", 3),
                        Tuple.of("d", 4),
                        Tuple.of("e", 5),
                        Tuple.of("f", 0),
                        Tuple.of("g", 0)
                ),
                List.of("a", "b", "c", "d", "e", "f", "g").zipAll(numbers, "x", 0)
        );


        BiFunction<String, Integer, String> combineLetterAndNumber = (letter, number) -> letter + number.toString();
        assertEquals(
                List.of("a1", "b2", "c3", "d4", "e5"),
                letters.zipWith(numbers, combineLetterAndNumber)
        );

        BiFunction<Integer, Integer, Integer> multiplyByIndex = (number, index) -> number * index;
        assertEquals(
                List.of(0, 2, 6, 12, 20), // remember index start at 0
                numbers.zipWithIndex(multiplyByIndex)
        );
    }

    @Test
    public void lists_can_be_unzipped_too() {

        assertEquals(
                Tuple.of(
                        List.of(1, 2, 3, 4, 5),
                        List.of(11, 12, 13, 14, 15)
                ),
                numbers.unzip(x -> Tuple.of(x, x + 10)));
    }

    @Test
    public void lists_can_be_iterated_with_an_operation_that_accumulates_the_value() {

        BiFunction<Integer, Integer, Integer> accumulateSum = (accumulator, number) -> accumulator + number;
        assertEquals(
                List.of(
                        0, // accumulator = initial value
                        1, // accumulator = accumulator + first number
                        3, // accumulator = accumulator + second number
                        6, // accumulator = accumulator + third number
                        10,// accumulator = accumulator + fourth number
                        15 // accumulator = accumulator + fifth number
                ),
                numbers.scan(0, accumulateSum) // same as scanLeft
        );

        assertEquals(
                List.of(
                        15, // perform operations on all elements + initial value
                        14, // undo operation for first element
                        12, // undo operation for first 2 elements
                        9,  // undo operation for first 3 elements
                        5,  // undo operation for first 4 elements
                        0   // undo operation for all elements == initial value
                ),
                numbers.scanRight(0, accumulateSum)
        );
    }

    class DummyHelper {
        void doSomething(Integer x) { /* do something */ }
    }

    @Spy
    private DummyHelper dummy = new DummyHelper();

    @Disabled
    @Test
    public void side_effects_can_be_performed_when_iterating_list_elements_only_once_and_continue_doing_more_operations() {

        Consumer<Integer> doSideEffect = number -> dummy.doSomething(number);

        assertEquals(List.of(2, 3, 4, 5, 6), numbers.peek(doSideEffect).map(x -> x + 1));

        verify(dummy, times(1)).doSomething(1);
        verify(dummy, never()).doSomething(2);
        verify(dummy, never()).doSomething(3);
        verify(dummy, never()).doSomething(4);
        verify(dummy, never()).doSomething(5);
    }

    @Disabled
    @Test
    public void side_effects_can_be_performed_when_iterating_list_elements_for_each_element_and_finish_execution() {

        Consumer<Integer> doSideEffect = number -> dummy.doSomething(number);

        numbers.forEach(doSideEffect);

        verify(dummy).doSomething(1);
        verify(dummy).doSomething(2);
        verify(dummy).doSomething(3);
        verify(dummy).doSomething(4);
        verify(dummy).doSomething(5);
    }
}