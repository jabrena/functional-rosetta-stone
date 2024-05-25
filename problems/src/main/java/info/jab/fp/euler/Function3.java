package info.jab.fp.euler;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface Function3<T, U, V, R> {

    R apply(T t, U u, V v);

    default <K> Function3<T, U, V, K> andThen(Function<? super R, ? extends K> after) {
        Objects.requireNonNull(after);
        return (T t, U u, V v) -> after.apply(apply(t, u, v));
    }
}
