package org.fundamentals.fp.type;

import java.util.Optional;
import lombok.Value;

@Value
public class Person {

    private final Optional<BeachHouse> beachHouse;

    public Person(BeachHouse beachHouse) {
        this.beachHouse = Optional.ofNullable(beachHouse);
    }
}

