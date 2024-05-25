package info.jab.fp.java8;

import java.util.function.Function;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class FunctionalCompositionTest {

    /**
     * When called with the value 3, the composed Function
     * will first call the add Function and then the multiply Function.
     * The resulting calculation will be (3 + 3) * 2 and the result will be 12.
     */
    @Test
    public void thenComposeTest() {

        Function<Integer, Integer> multiply = (value) -> value * 2;
        Function<Integer, Integer> add      = (value) -> value + 3;
        Function<Integer, Integer> addThenMultiply = multiply.compose(add);

        Integer result1 = addThenMultiply.apply(3);

        then(result1).isEqualTo(12);
    }

    @Test
    public void andThenTest() {

        Function<Integer, Integer> multiply = (value) -> value * 2;
        Function<Integer, Integer> add      = (value) -> value + 3;
        Function<Integer, Integer> multiplyThenAdd = multiply.andThen(add);

        Integer result2 = multiplyThenAdd.apply(3);

        then(result2).isEqualTo(9);
    }
}
