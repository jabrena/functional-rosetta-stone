package arrow.either

import arrow.core.getOrElse
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec

class EitherUnitTest : FunSpec() {

    init {
        test("should return an either with result of divide operation") {
            val either = attemptDivide(num = 4, deno = 5)
            val result = either.map { it }.getOrElse { -1.0 }

            result shouldBe 0.8
        }

        test("should return an either with exception of divide operation") {
            val either = attemptDivide(num = 4, deno = 0)
            val result = either.map { it }.getOrElse { -1.0 }

            result shouldBe -1.0
        }
    }
}