package info.jab.fp.sealed;

// Either.java
public sealed interface Either<L, R> permits Either.Left, Either.Right {

    <T> T fold(java.util.function.Function<? super L, ? extends T> leftMapper,
               java.util.function.Function<? super R, ? extends T> rightMapper);

    boolean isLeft();
    boolean isRight();

    static <L, R> Either<L, R> left(L value) {
        return new Left<>(value);
    }

    static <L, R> Either<L, R> right(R value) {
        return new Right<>(value);
    }

    record Left<L, R>(L value) implements Either<L, R> {

        @Override
        public <T> T fold(java.util.function.Function<? super L, ? extends T> leftMapper,
                          java.util.function.Function<? super R, ? extends T> rightMapper) {
            return leftMapper.apply(value);
        }

        @Override
        public boolean isLeft() {
            return true;
        }

        @Override
        public boolean isRight() {
            return false;
        }
    }

    record Right<L, R>(R value) implements Either<L, R> {
        
        @Override
        public <T> T fold(java.util.function.Function<? super L, ? extends T> leftMapper,
                          java.util.function.Function<? super R, ? extends T> rightMapper) {
            return rightMapper.apply(value);
        }

        @Override
        public boolean isLeft() {
            return false;
        }

        @Override
        public boolean isRight() {
            return true;
        }
    }
}
