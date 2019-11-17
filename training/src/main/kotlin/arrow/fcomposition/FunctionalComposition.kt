package arrow.fcomposition

import arrow.core.andThen
import arrow.syntax.function.compose
import arrow.syntax.function.forwardCompose

object FunctionalComposition {

    private val p:    (String) -> String    = { "<p>$it</p>" }
    private val div:  (String) -> String    = { "<div>$it</div>" }
    private val strong:  (String) -> String = { "<strong>$it</strong>" }

    val divStrong   = div compose strong
    val divP        = p forwardCompose div
    val pDivStrong  = p andThen div andThen strong
}
