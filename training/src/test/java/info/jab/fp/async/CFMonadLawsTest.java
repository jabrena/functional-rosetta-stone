package info.jab.fp.async;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import org.junit.jupiter.api.extension.ExtendWith;

import info.jab.utils.TestLoggerExtension;

/**
 * Original code from: https://gist.github.com/lestard/e28fb8a340737ffd9623
 */
@ExtendWith(TestLoggerExtension.class)
public class CFMonadLawsTest {

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

        int value = 42;
        Function<Integer, CompletableFuture<Integer>> f = n -> CompletableFuture.completedFuture(n * 2);

        then(CompletableFuture.completedFuture(value).thenCompose(f).join()
                        .equals(f.apply(value).join())).isTrue();
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

        int value = 42;
        CompletableFuture<Integer> monadicValue = CompletableFuture.completedFuture(value);

        then(monadicValue.thenCompose(CompletableFuture::completedFuture).join()
                .equals(monadicValue.join())).isTrue();
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

        int value = 42;
        CompletableFuture<Integer> monadicValue = CompletableFuture.completedFuture(value);
        Function<Integer, CompletableFuture<Integer>> f = n -> CompletableFuture.completedFuture(n * 2);
        Function<Integer, CompletableFuture<Integer>> g = n -> CompletableFuture.completedFuture(n * 5);
        Function<Integer, CompletableFuture<Integer>> f_flatMap_g = n -> f.apply(n).thenCompose(g);

        then(monadicValue.thenCompose(f).thenCompose(g).join()
                .equals(monadicValue.thenCompose(f_flatMap_g).join())).isTrue();
    }
}