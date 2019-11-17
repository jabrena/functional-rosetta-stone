package arrow.curry

import arrow.syntax.function.curried

object Curry {
    val add     = { x: Int, y: Int -> x + y }
    val curried = add.curried()
}