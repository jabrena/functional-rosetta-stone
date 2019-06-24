package org.fundamentals.fp.euler;

fun KotlinSolution01(limit : Long) : Long {

  return generateSequence(1) { it + 1 }
          .take((limit -1).toInt())
          .filter { x -> x % 3 == 0 || x % 5 == 0 }
          //.onEach { println("$it") }
          .sum()
          .toLong()
}