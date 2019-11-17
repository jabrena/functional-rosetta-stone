package arrow.fcomposition

import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec

class FunctionalCompositionUnitTest : FunSpec() {

    init {
        test("should return <strong> embedded within <div>") {
            val divStrong = FunctionalComposition.divStrong("Hello")
            divStrong shouldBe "<div><strong>Hello</strong></div>"
        }

        test("should return <p> embedded within <div>") {
            val divStrong = FunctionalComposition.divP("Hello")
            divStrong shouldBe "<div><p>Hello</p></div>"
        }

        test("should return <p> embedded within <div> embedded withing strong") {
            val divStrong = FunctionalComposition.pDivStrong("Hello")
            divStrong shouldBe "<strong><div><p>Hello</p></div></strong>"
        }
    }
}