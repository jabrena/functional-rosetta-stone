package info.jab.fp.others;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import static java.util.stream.Collectors.toUnmodifiableList;
import java.util.stream.IntStream;

/**
 * Write a program that prints the numbers from 1 to 100.
 * But for multiples of three print “Fizz” instead of the number and
 * for the multiples of five print “Buzz”.
 * For numbers which are multiples of both three and five print “FizzBuzz".
 */
public class FizzBuzz {

    private final int THREE = 3;
    private final int FIVE = 5;
    BiPredicate<Integer, Integer> isMultiple = (l, i) -> l % i == 0;
    Predicate<Integer> isMultiple3 = number -> isMultiple.test(number, THREE);
    Predicate<Integer> isMultiple5 = number -> isMultiple.test(number, FIVE);

    Function<Integer, String> fizzBuzzMapper = i -> {

        if(isMultiple3.test(i) && isMultiple5.test(i)) {
            return "FizzBuzz";
        } else if (isMultiple3.test(i)) {
            return "Fizz";
        } else if(isMultiple5.test(i)) {
            return "Buzz";
        }

        return String.valueOf(i);
    };

    public List<String> JavaStreamSolution() {

        return IntStream.rangeClosed(1, 100).boxed()
                .map(fizzBuzzMapper)
                .collect(toUnmodifiableList());
    }
}
