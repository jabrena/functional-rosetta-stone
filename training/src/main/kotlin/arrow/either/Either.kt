package arrow.either

import arrow.core.Either

fun attemptDivide(num: Int, deno: Int): Either<IllegalArgumentException, Double> {
    if (deno == 0)
        return Either.left(IllegalArgumentException("denominator is 0"))

    return Either.right((num.toDouble() / deno.toDouble()))
}