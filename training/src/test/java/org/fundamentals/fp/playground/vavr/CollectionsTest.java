package org.fundamentals.fp.playground.vavr;

import io.vavr.collection.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 *     Java collections are mutable, making them a great source of
 *     program failure, especially in the presence of concurrency.
 *     The Collection interface provides methods such as clear().
 *     Classes such as ConcurrentHashMap were created to deal with
 *     the already created problems.
 *     With immutability, we get thread-safety for free:
 *     no need to write new classes to deal with a problem that should not be there in the first place.
 *
 */
public class CollectionsTest {

    @Test
    public void whenSumsVavrList_thenCorrect() {
        int sum = List.of(1, 2, 3).sum().intValue();

        assertEquals(6, sum);
    }
}