package info.jab.fp.euler;

import java.util.stream.Stream;

/**
 * Created by mtumilowicz on 2018-11-25.
 */
@FunctionalInterface
public interface Trampoline<T> {

    default boolean isComplete() {
        return false;
    }

    default T result() {
        throw new UnsupportedOperationException();
    }

    Trampoline<T> bounce();

    default T invoke() {
        return Stream.iterate(this, Trampoline::bounce)
                .filter(Trampoline::isComplete)
                .findFirst()
                .orElseThrow()
                .result();
    }

    static <T> Trampoline<T> completed(T result) {
        return new Completed<>(result);
    }

    class Completed<T> implements Trampoline<T> {

        private final T result;

        Completed(T result) {
            this.result = result;
        }

        @Override
        public boolean isComplete() {
            return true;
        }

        @Override
        public T result() {
            return result;
        }

        @Override
        public Trampoline<T> bounce() {
            throw new UnsupportedOperationException();
        }
    }
}
