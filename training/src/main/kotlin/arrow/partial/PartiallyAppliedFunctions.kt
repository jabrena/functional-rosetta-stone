package arrow.partial

import arrow.partial.SupportedStates.MH
import arrow.partial.SupportedStates.RAJ
import arrow.partial.SupportedStates.UP

import arrow.syntax.function.partially1
import arrow.syntax.function.partially3

object PartiallyAppliedFunctions {

    private val strong: (String, String, String) -> String
                    = { body, id, style -> """<strong id="$id" style="$style"">$body</strong>""" }

    val redStyleStrongVariable: (String, String) -> String = strong.partially3("font: red")

    fun redStyleStrongFn(body: String, id: String) = strong.partially3("font: red").invoke(body, id)

    val applyTax: (SupportedStates, Double) -> Double = { state, amount ->
        when (state) {
            MH  -> amount * 2
            RAJ -> amount * 3
            UP  -> amount * 4
        }
    }

    fun localTax(amount: Double) = applyTax.partially1(MH)(amount)
}

enum class SupportedStates {
    MH, RAJ, UP
}