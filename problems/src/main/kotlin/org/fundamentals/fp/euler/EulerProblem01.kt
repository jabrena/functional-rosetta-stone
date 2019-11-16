package org.fundamentals.fp.euler;

/**
 * Problem 1: Multiples of 3 and 5
 * https://projecteuler.net/problem=1
 *
 * If we list all the natural numbers below 10 that are multiples of 3 or 5,
 * we get 3, 5, 6 and 9.
 * The sum of these multiples is 23.
 *
 * Find the sum of all the multiples of 3 or 5 below 1000.
 *
 */
fun KotlinSolution01(limit : Int) : Int {

  val THREE = 3
  val FIVE = 5
  val isMultiple = { l: Int, i: Int -> l % i == 0 }
  val isMultiple3 = { number: Int -> isMultiple(number, THREE) }
  val isMultiple5 = { number: Int -> isMultiple(number, FIVE) }

  return generateSequence(1) { it + 1 }
          .take(limit - 1)
          .filter { isMultiple3(it).or(isMultiple5(it)) }
          .sum()
}