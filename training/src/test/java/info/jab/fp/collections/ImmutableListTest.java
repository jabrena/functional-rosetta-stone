package info.jab.fp.collections;

import static java.util.stream.Collectors.toUnmodifiableList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ImmutableListTest {

    @Test
    void given_javaInmutableList_when_addNewElement_then_katakrokerTest() {

         var list = java.util.List.of(1, 2, 3).stream()
                .peek(System.out::println)
                .collect(toUnmodifiableList()); //Java 10
                //.collect(Collectors.collectingAndThen(
                //    Collectors.toList(),
                //    x -> Collections.unmodifiableList(x))); // Java 8

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            list.add(1); //Katacroker
        });
    }

    /*
    @Test
    void given_vavrList_when_addNewElement_then_katakrokerTest() {

        var animals = io.vavr.collection.List.of("🐱", "🐶");
        animals.append("🐕");

        then(animals.size()).isEqualTo(2);

        animals = animals.append("🐕");

        then(animals.size()).isEqualTo(3);
        then(animals).isEqualTo(io.vavr.collection.List.of("🐱", "🐶", "🐕"));
    }
     */
}
