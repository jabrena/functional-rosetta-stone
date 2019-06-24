package org.fundamentals.fp.euler;

fun KotlinSolution(limit : Long) : Long {

  return generateSequence(1) { it + 1 }
          .take((limit -1).toInt())
          .filter { x -> x % 3 == 0 || x % 5 == 0 }
          //.onEach { println("filtered $it") }
          .sum()
          .toLong()
}