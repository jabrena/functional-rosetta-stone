package org.fundamentals.fp.playground.vavr.level1;

import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class ListTest {

    @Test
    public void demo() {
        List<String> list = List.of(
                "Java", "PHP", "Jquery", "JavaScript", "JShell", "JAVA");

        List<String> newList = list.filter(l -> l.toLowerCase().contains("j"));

        then(newList.size()).isEqualTo(5);
    }

}
