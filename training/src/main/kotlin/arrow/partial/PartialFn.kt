package arrow.partial

/*
import arrow.core.PartialFunction

object PartialFn {
    val upper: (String?) -> String = { s -> s!!.toUpperCase() }
    val upperPartial = PartialFunction<String?, String>(definedAt = { s -> s != null }, f = upper)
    val upperPartialOrElse = PartialFunction<String?, String>(definedAt = { s -> s == null }, f = { "" })
}
*/