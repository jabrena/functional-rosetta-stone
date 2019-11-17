package arrow.curry

import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec

class CurryUnitTest: FunSpec(){
    init {
        test("should allow curried invocation of add function"){
            val result = Curry.curried(2)(3)
            result shouldBe 5
        }

    }
}