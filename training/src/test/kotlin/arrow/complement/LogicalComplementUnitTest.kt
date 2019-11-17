package arrow.complement

import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec


class LogicalComplementUnitTest : FunSpec() {

    init {
        test("should return true given a number is even") {
            val isEven = LogicalComplement.evenPredicate(20)
            isEven shouldBe true
        }

        test("should return false given number is odd using evenPredicate") {
            val isEven = LogicalComplement.evenPredicate(21)
            isEven shouldBe false
        }

        test("should return false given number is odd using complement (oddPredicate)") {
            val isOdd = LogicalComplement.oddPredicate(21)
            isOdd shouldBe true
        }
    }
}