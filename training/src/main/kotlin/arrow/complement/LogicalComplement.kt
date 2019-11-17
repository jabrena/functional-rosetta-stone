package arrow.complement

import arrow.core.Predicate
import arrow.syntax.function.complement

object LogicalComplement {

    val evenPredicate: Predicate<Int> = { value: Int -> value % 2 == 0 }
    val oddPredicate: Predicate<Int>  = evenPredicate.complement()
}