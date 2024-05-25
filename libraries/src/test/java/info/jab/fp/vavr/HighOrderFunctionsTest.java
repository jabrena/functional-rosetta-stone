package info.jab.fp.vavr;

import java.util.function.Function;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HighOrderFunctionsTest {

    @Test
    public void functions_can_return_functions() {

        Function<Integer, Function<Integer, Integer>> highOrderSum = x -> y -> x + y;

        Function<Integer, Integer> result = highOrderSum.apply(10);

        assertEquals(new Integer(15), result.apply(5));
    }

    @Test
    public void functions_can_take_functions_as_parameters() {

        Function<Function<Integer, String>, String> highOrderStringifier = f -> f.apply(10);

        assertEquals("10!!!", highOrderStringifier.apply(number -> number.toString() + "!!!"));
        assertEquals("10???", highOrderStringifier.apply(number -> number.toString() + "???"));
    }

    @Test
    public void functions_can_take_functions_as_parameters_and_return_functions() {

        Function<Function<Integer, String>, Function<String, String>> highOrderPhilosopher = f -> string -> string + " " + f.apply(24);

        Function<String, String> resultingFunction = highOrderPhilosopher.apply(x -> new StringBuilder(x.toString()).reverse().toString());
        assertEquals("The meaning of life is 42", resultingFunction.apply("The meaning of life is"));

        Function<String, String> anotherFunction = highOrderPhilosopher.apply(x -> String.valueOf(x + 18));
        assertEquals("I'll repeat, is 42", anotherFunction.apply("I'll repeat, is"));
    }
}