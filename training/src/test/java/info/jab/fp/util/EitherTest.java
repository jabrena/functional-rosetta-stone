package info.jab.fp.util;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
 
public class EitherTest {

    @Test
    void testLeft() {
        Either<String, Integer> left = Either.left("Error");
        assertTrue(left.isLeft());
        assertFalse(left.isRight());
        assertEquals("Error", left.fold(Function.identity(), r -> "No Error"));
    }

    @Test
    void testRight() {
        Either<String, Integer> right = Either.right(42);
        assertFalse(right.isLeft());
        assertTrue(right.isRight());
        assertEquals("No Error", right.fold(l -> "Error", r -> "No Error"));
    }

    @Test
    void testMap() {
        Either<String, Integer> right = Either.right(42);
        Either<String, Integer> mappedRight = right.map(x -> x + 1);
        assertTrue(mappedRight.isRight());
        assertEquals(43, mappedRight.fold(l -> -1, Function.identity()));

        Either<String, Integer> left = Either.left("Error");
        Either<String, Integer> mappedLeft = left.map(x -> x + 1);
        assertTrue(mappedLeft.isLeft());
        assertEquals("Error", mappedLeft.fold(Function.identity(), r -> "No Error"));
    }

    @Test
    void testFlatMap() {
        Either<String, Integer> right = Either.right(42);
        Either<String, Integer> flatMappedRight = right.flatMap(x -> Either.right(x + 1));
        assertTrue(flatMappedRight.isRight());
        assertEquals(43, flatMappedRight.fold(l -> -1, Function.identity()));

        Either<String, Integer> left = Either.left("Error");
        Either<String, Integer> flatMappedLeft = left.flatMap(x -> Either.right(x + 1));
        assertTrue(flatMappedLeft.isLeft());
        assertEquals("Error", flatMappedLeft.fold(Function.identity(), r -> "No Error"));
    }

    @Test
    void testSwap() {
        Either<String, Integer> right = Either.right(42);
        Either<Integer, String> swappedRight = right.swap();
        assertTrue(swappedRight.isLeft());
        assertEquals(42, swappedRight.fold(Function.identity(), r -> -1));

        Either<String, Integer> left = Either.left("Error");
        Either<Integer, String> swappedLeft = left.swap();
        assertTrue(swappedLeft.isRight());
        assertEquals("Error", swappedLeft.fold(l -> -1, Function.identity()));
    }

    @Test
    void testGetOrElse() {
        Either<String, Integer> right = Either.right(42);
        assertEquals(42, right.getOrElse(() -> -1));

        Either<String, Integer> left = Either.left("Error");
        assertEquals(-1, left.getOrElse(() -> -1));
    }

    @Test
    void testOrElse() {
        Either<String, Integer> right = Either.right(42);
        assertEquals(42, right.orElse(() -> Either.right(-1)).fold(l -> -1, Function.identity()));

        Either<String, Integer> left = Either.left("Error");
        assertEquals(-1, left.orElse(() -> Either.right(-1)).fold(l -> -1, Function.identity()));
    }

    @Test
    void testFilterOrElse() {
        Either<String, Integer> right = Either.right(42);
        Either<String, Integer> filteredRight = right.filterOrElse(x -> x > 40, () -> "Filtered out");
        assertTrue(filteredRight.isRight());
        assertEquals(42, filteredRight.fold(l -> -1, Function.identity()));

        Either<String, Integer> filteredRightFail = right.filterOrElse(x -> x < 40, () -> "Filtered out");
        assertTrue(filteredRightFail.isLeft());
        assertEquals("Filtered out", filteredRightFail.fold(Function.identity(), r -> "No Error"));

        Either<String, Integer> left = Either.left("Error");
        Either<String, Integer> filteredLeft = left.filterOrElse(x -> x > 40, () -> "Filtered out");
        assertTrue(filteredLeft.isLeft());
        assertEquals("Error", filteredLeft.fold(Function.identity(), r -> "No Error"));
    }

    @Test
    void testCombine() {
        Either<String, Integer> right1 = Either.right(42);
        Either<String, Integer> right2 = Either.right(58);
        Either<String, Integer> combinedRight = right1.combine(right2, Integer::sum);
        assertTrue(combinedRight.isRight());
        assertEquals(100, combinedRight.fold(l -> -1, Function.identity()));

        Either<String, Integer> left = Either.left("Error");
        Either<String, Integer> combinedWithLeft = right1.combine(left, Integer::sum);
        assertTrue(combinedWithLeft.isRight());
        assertEquals(42, combinedWithLeft.fold(l -> -1, Function.identity()));

        Either<String, Integer> combinedBothLeft = left.combine(left, Integer::sum);
        assertTrue(combinedBothLeft.isLeft());
        assertEquals("Error", combinedBothLeft.fold(Function.identity(), r -> "No Error"));
    }
}
