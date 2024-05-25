package info.jab.fp.reactor;

import java.util.function.Function;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxMonadLawsTest {

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
        Function<Integer, Flux<Integer>> addOne = x -> Flux.just(x + 1);

        StepVerifier
                .create(Flux.just(value).flatMap(addOne))
                .expectNext(addOne.apply(value).blockFirst())
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
        Flux.just(value).flatMap(x -> Flux.just(x)).equals(Flux.just(value));

        StepVerifier
                .create(Flux.just(value).flatMap(x -> Flux.just(x)))
                .expectNext(Flux.just(value).blockFirst())
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
        Function<Integer, Flux<Integer>> addOne = x -> Flux.just(x + 1);
        Function<Integer, Flux<Integer>> addTwo = i -> Flux.just(i + 2);
        Function<Integer, Flux<Integer>> addThree = i -> addOne.apply(i).log().flatMap(addTwo);

        StepVerifier
                .create(Flux.just(value)
                        .flatMap(i -> addOne.apply(i))
                        .flatMap(i -> addTwo.apply(i)))
                .expectNext(Flux.just(value).flatMap(addThree).blockFirst())
                .expectComplete()
                .verify();
    }

}