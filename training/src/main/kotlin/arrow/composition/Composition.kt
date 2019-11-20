package arrow.composition

import arrow.syntax.function.andThen
import arrow.syntax.function.forwardCompose
import java.util.Random


fun main(args : Array<String>) {
    println("Hello World")
    //forwardComposeExample1()
    andThenExample1()
}

fun forwardComposeExample1() {
    val randomDigit = Random().nextInt()
    val get = { randomDigit }
    val pow = { i: Int -> i * i }
    val composition = get forwardCompose pow
    println(composition())
}

fun andThenExample1() {
    val getOne = { d: Double -> d + 1.0 }
    val pow = { d: Double -> Math.pow(d, 2.0) }
    var round = { d: Double -> Math.round(d) }
    //getOne.andThen(pow).andThen(pow).andThen(round).apply(1.0)
    val composition = getOne andThen  pow andThen  pow andThen round
    println(composition(1.0))
}