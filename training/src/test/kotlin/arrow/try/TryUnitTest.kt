package arrow.`try`

import arrow.core.getOrElse
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec

class TryUnitTest : FunSpec() {

    init {
        test("should return Try with success as the result of the operation") {
            val tryOp = tryDivide(num = 10, deno = 10)
            val result = tryOp.filter { it == 1.0 }.map { it * 4 }.getOrElse { _ -> -1.0 }

            result shouldBe 4.0
        }

        test("should return Try with failure as the result of the operation") {
            val tryOp = tryDivide(num = 10, deno = 0)
            val result = tryOp.filter { it == 1.0 }.map { it * 4 }.getOrElse { _ -> -1.0 }

            result shouldBe -1.0
        }
    }
}