package info.jab.fp.sealed;

import java.util.function.Function;

public class EitherExample {
    public static void main(String[] args) {
        Either<String, Integer> success = Either.right(42);
        Either<String, Integer> failure = Either.left("Error occurred");

        Function<Either<String, Integer>, String> resultMapper = either ->
            either.fold(
                error -> "Failed with error: " + error,
                value -> "Success with value: " + value
            );

        System.out.println(resultMapper.apply(success)); // Output: Success with value: 42
        System.out.println(resultMapper.apply(failure)); // Output: Failed with error: Error occurred
    }
}
