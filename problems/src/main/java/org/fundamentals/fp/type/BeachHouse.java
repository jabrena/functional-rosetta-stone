package org.fundamentals.fp.type;

import java.util.Optional;
import lombok.Value;

@Value
public class BeachHouse {

    private final Optional<Insurance> insurance;

    public BeachHouse(Insurance insurance) {
        this.insurance = Optional.ofNullable(insurance);
    }
}