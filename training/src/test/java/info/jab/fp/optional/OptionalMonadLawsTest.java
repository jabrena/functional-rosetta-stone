package info.jab.fp.optional;

//import io.vavr.control.Option;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.BDDAssertions.then;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OptionalMonadLawsTest {

    @Test
    public void given_javaOptionalMonad_when_use_then_passAllMonadLawTests() {

        //Left Identity:
        Function<Integer, Optional> addOne = x -> Optional.of(x + 1);
        Optional.of(5).flatMap(x -> addOne.apply(x)).equals(addOne.apply(5));

        //Right Identity:
        Optional.of(5).flatMap(Optional::of).equals(Optional.of(5));

        //Associativity:
        //Function<Integer, Optional> addOne = i -> Optional.of(i + 1);
        Function<Integer, Optional> addTwo = i -> Optional.of(i + 2);
        Function<Integer, Optional> addThree = i -> addOne.apply(i).flatMap(addTwo);
        Optional.of(5).flatMap(x -> addOne.apply(x)).flatMap(addTwo).equals(Optional.of(5).
                flatMap(x -> addThree.apply(x)));

        //Problems with Optional
        Function f = new Function<String, Optional<String>>() {
            @Override public Optional<String> apply(String str) {
                return str == null ? Optional.of("X") : Optional.of(str + str);
            }
        };

        then(f.apply(null)).isEqualTo(Optional.of("X"));
        Assertions.assertThrows(NullPointerException.class, () -> {
            Optional.of(null).flatMap(f);
        });
        then(Optional.ofNullable(null).flatMap(f)).isEqualTo(Optional.empty());
        then(Optional.of(1).map(a -> null)).isEqualTo(Optional.empty());
        Assertions.assertThrows(NullPointerException.class, () -> {
            Optional.of(1).flatMap(a -> null);
        });
    }

    /*
    @Test
    public void given_vavrOptionMonad_when_use_then_passAllMonadLawTests() {

        //Left Identity:
        Function<Integer, Option> addOne = x -> Option.of(x + 1);
        Option.of(5).flatMap(x -> addOne.apply(x)).equals(addOne.apply(5));

        //Right Identity:
        Option.of(5).flatMap(Option::of).equals(Option.of(5));

        //Associativity:
        //Function<Integer, Option> addOne = i -> Option.of(i + 1);
        Function<Integer, Option> addTwo = i -> Option.of(i + 2);
        Function<Integer, Option> addThree = i -> addOne.apply(i).flatMap(addTwo);
        Option.of(5).flatMap(x -> addOne.apply(x)).flatMap(addTwo).equals(Option.of(5).
                flatMap(x -> addThree.apply(x)));

        Function f2 = new Function<String, Option<String>>() {
            @Override public Option<String> apply(String str) {
                return str == null ? Option.of("X") : Option.of(str + str);
            }
        };

        then(f2.apply(null)).isEqualTo(Option.of("X"));
        then(Option.of(null).flatMap(f2)).isEqualTo(Option.none());
        then(Option.of(1).map(a -> null)).isEqualTo(Option.some(null));
        then(Option.of(1).flatMap(a -> null)).isEqualTo(null);
    }
     */

}
