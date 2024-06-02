package info.jab.fp.util;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class EitherMonadLawsTest {

    // Left Identity: return a >>= f ≡ f a
    @Test
    void testLeftIdentity() {
        Function<Integer, Either<String, Integer>> f = x -> Either.right(x + 1);
        Integer a = 42;

        Either<String, Integer> leftIdentity = Either.<String, Integer>right(a).flatMap(f);
        Either<String, Integer> expected = f.apply(a);

        assertEquals(expected, leftIdentity);
    }

    // Right Identity: m >>= return ≡ m
    @Test
    void testRightIdentity() {
        Either<String, Integer> m = Either.right(42);

        Either<String, Integer> rightIdentity = m.flatMap(Either::right);

        assertEquals(m, rightIdentity);
    }

    // Associativity: (m >>= f) >>= g ≡ m >>= (\x -> f x >>= g)
    @Test
    void testAssociativity() {
        Function<Integer, Either<String, Integer>> f = x -> Either.right(x + 1);
        Function<Integer, Either<String, Integer>> g = x -> Either.right(x * 2);

        Either<String, Integer> m = Either.right(42);

        Either<String, Integer> leftAssociative = m.flatMap(f).flatMap(g);
        Either<String, Integer> rightAssociative = m.flatMap(x -> f.apply(x).flatMap(g));

        assertEquals(leftAssociative, rightAssociative);
    }
}
