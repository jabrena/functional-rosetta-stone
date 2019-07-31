package org.fundamentals.fp.playground;

import io.vavr.control.Either;
import java.util.Random;
import org.junit.Test;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Left;
import static io.vavr.API.Match;
import static io.vavr.API.Right;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;

public class EitherTest {

    @Test
    public void eitherFeature() {
        Either<Throwable, String> eitherString =
                getEitherString()
                        //.filter(e -> !e.isEmpty())
                        .right()
                        .map(String::toUpperCase)
                        .map(s -> s + "!!!")
                        .toEither();

        String response = Match(eitherString).of(
                Case($Right($()), value -> "Right value " + value),
                Case($Left($()), x -> "Left value " + x));

        System.out.println(response);
        System.out.println(eitherString);
    }

    private Either<Throwable, String> getEitherString() {
        if (new Random().nextBoolean()) {
            return Right("hello Vavr world " + System.nanoTime());
        } else {
            return Left(new CustomException());
        }
    }

    private class CustomException extends RuntimeException {
    }

}
