package org.fundamentals.fp.euler;

fun KotlinSolution01(limit : Int) : Int {

  return generateSequence(1) { it + 1 }
          .take(limit - 1)
          .filter { x -> x % 3 == 0 || x % 5 == 0 }
          .sum()
}