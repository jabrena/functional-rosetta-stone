package info.jab.fp.vavr;

import io.vavr.control.Either;
import io.vavr.control.Option;

import java.util.function.Function;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EitherInstanceMethodsTest {
    
    @Test
    public void bimap_left() {
        Either<String, Object> eitherLeft =
                Either.left("not found")
                        .bimap(left -> left + " mapped", Function.identity());
        
        assertThat(eitherLeft.left().get(), is("not found mapped"));
    }

    @Test
    public void bimap_right() {
        Either<Object, String> eitherLeft =
                Either.right("not found")
                        .bimap(Function.identity(), left -> left + " mapped");

        assertThat(eitherLeft.right().get(), is("not found mapped"));
    }
    
    @Test
    public void filter_left() {
        Option<Either<Integer, Integer>> filter =
                Either.<Integer, Integer>left(6).filter(x -> x > 10);
        
        assertThat(filter.get().getLeft(), is(6));
    }

    @Test
    public void filter_right_notSatisfied() {
        Option<Either<Integer, Integer>> filter =
                Either.<Integer, Integer>right(6).filter(x -> x > 10);

        assertTrue(filter.isEmpty());
    }

    @Test
    public void filter_right_satisfied() {
        Option<Either<Integer, Integer>> filter =
                Either.<Integer, Integer>right(6).filter(x -> x > 5);

        assertThat(filter.get().get(), is(6));
    }
}