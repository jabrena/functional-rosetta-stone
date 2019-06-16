package org.fundamentals.fp.euler.problem1

import java.math.BigInteger

inline fun <T: Number> bigInt(n: T) = BigInteger(n.toString())

inline operator fun Int.times(multiplicand: BigInteger) = bigInt(this) * multiplicand

inline fun Int.multipleOf(n: Int) = toLong() multipleOf n
inline infix fun Long.multipleOf(n: Int) = this % n == 0.toLong()

fun main() {
  val limit = 1000

  // average execution time of 0.3447 milliseconds over 10 iterations
  val result = (1..limit - 1).asSequence().filter { n -> n.multipleOf(3) || n.multipleOf(5) }.sum()

  println("the sum of all the multiples of 3 or 5 below $limit is $result")
}