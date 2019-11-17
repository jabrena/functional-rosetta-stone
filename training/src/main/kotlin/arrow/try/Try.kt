package arrow.`try`

import arrow.core.Try

fun tryDivide(num: Int, deno: Int): Try<Double> {
    return Try {
        num.toDouble() / deno.toDouble()
    }
}