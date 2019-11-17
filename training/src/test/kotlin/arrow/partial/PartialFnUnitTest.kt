package arrow.partial

import arrow.core.orElse
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.FunSpec

/*
class PartialFnUnitTest : FunSpec() {
    init {
        test("should throw NPE") {
            val list = listOf("a", "b", null)
            shouldThrow<NullPointerException> { list.map(PartialFn.upper) }
        }

        test("should not throw NPE") {
            val list = listOf("a", "b", null)
            list.map(PartialFn.upperPartial.orElse(PartialFn.upperPartialOrElse)) shouldBe listOf("A", "B", "")
        }
    }
}
*/