package arrow.partial

import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec

class PartiallyAppliedFunctionsUnitTest : FunSpec() {

    init {
        test("should return string representation given body and id using redStyleStrongVariable") {
            val output = PartiallyAppliedFunctions.redStyleStrongVariable("Hello World", "id1")
            output shouldBe """<strong id="id1" style="font: red"">Hello World</strong>"""
        }

        test("should return string representation given body and id using redStyleStrongFn") {
            val output = PartiallyAppliedFunctions.redStyleStrongFn("Hello World", "id1")
            output shouldBe """<strong id="id1" style="font: red"">Hello World</strong>"""
        }

        test("should return local tax for MH") {
            val tax = PartiallyAppliedFunctions.localTax(12.0)
            tax shouldBe 24.0
        }

        test("should return tax for RAJ") {
            val tax = PartiallyAppliedFunctions.applyTax(SupportedStates.RAJ, 10.0)
            tax shouldBe 30.0
        }
    }
}