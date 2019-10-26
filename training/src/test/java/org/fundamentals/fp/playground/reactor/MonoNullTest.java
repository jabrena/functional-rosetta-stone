package org.fundamentals.fp.playground.reactor;

import io.vavr.control.Option;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.BDDAssertions.then;

public class MonoNullTest {

    @Test
    public void nullProblemTests() {

        final String value = null;
        Assertions.assertThrows(NullPointerException.class, () -> {
            Mono.just(value);
        });
        System.out.println(Optional.ofNullable(value));
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            System.out.println(Optional.ofNullable(value).get());
        });
        then(Mono.just(Optional.ofNullable(value)).block()).isEqualTo(Optional.empty());
        then(Mono.justOrEmpty(value).log().map(Optional::ofNullable).log().block()).isEqualTo(value);
        then(Mono.justOrEmpty(value).log().map(Optional::of).log().block()).isEqualTo(value);
        then(Mono.justOrEmpty(Optional.ofNullable(value)).block()).isEqualTo(value);
        then(Mono.empty().block()).isEqualTo(value);
        then(Mono.justOrEmpty(Optional.empty()).block()).isEqualTo(value);

        //KO
        then(Mono.just(Optional.ofNullable(value)).block())
                .isNotEqualTo(Mono.justOrEmpty(value).block());

        StepVerifier
                .create(Mono.justOrEmpty(value))
                .expectComplete()
                .verify();

        System.out.println(Option.of(value));
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            System.out.println(Option.of(value).get());
        });
    }
}