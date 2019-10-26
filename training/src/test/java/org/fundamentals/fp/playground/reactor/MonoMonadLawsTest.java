package org.fundamentals.fp.playground.reactor;

import io.vavr.control.Option;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.BDDAssertions.then;

public class MonoMonadLawsTest {

    /**
     * Monad law 1, Left Identity
     *
     * From LYAHFGG [1] above:
     *   The first monad law states that if we take a value, put it in a default context
     *   with return and then feed it to a function by using >>=, it’s the same as just
     *   taking the value and applying the function to it
     */
    @Test
    public void law1LeftIdentityTest() {

        Integer value = 42;
        Function<Integer, Mono<Integer>> addOne = x -> Mono.just(x + 1);

        StepVerifier
                .create(Mono.just(value).flatMap(addOne))
                .expectNext(addOne.apply(value).block())
                .expectComplete()
                .verify();
    }

    /**
     * Monad law 2, Right Identity
     *
     * From LYAHFGG [1] above:
     *   The second law states that if we have a monadic value and we use >>= to feed
     *   it to return, the result is our original monadic value.
     */
    @Test
    public void law2RightIdentityTest() {

        Integer value = 42;
        Mono.just(value).flatMap(x -> Mono.just(x)).equals(Mono.just(value));

        StepVerifier
                .create(Mono.just(value).flatMap(x -> Mono.just(x)))
                .expectNext(Mono.just(value).block())
                .expectComplete()
                .verify();
    }

    /**
     * Monad law 3, Associativity
     *
     * From LYAHFGG [1] above:
     *   The final monad law says that when we have a chain of monadic function
     *   applications with >>=, it shouldn’t matter how they’re nested.
     */
    @Test
    public void law3AssociativityTest() {

        Integer value = 42;
        Function<Integer, Mono<Integer>> addOne = x -> Mono.just(x + 1);
        Function<Integer, Mono<Integer>> addTwo = i -> Mono.just(i + 2);
        Function<Integer, Mono<Integer>> addThree = i -> addOne.apply(i).log().flatMap(addTwo);

        StepVerifier
                .create(Mono.just(value)
                        .flatMap(i -> addOne.apply(i))
                        .flatMap(i -> addTwo.apply(i)))
                .expectNext(Mono.just(value).flatMap(addThree).block())
                .expectComplete()
                .verify();
    }

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