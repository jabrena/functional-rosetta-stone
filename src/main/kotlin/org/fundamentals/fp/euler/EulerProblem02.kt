package org.fundamentals.fp.euler;

fun KotlinSolution02(limit : Long) : Long {

    return generateSequence(0 to 1) { Pair(it.second, it.first + it.second) }
            .map { it.first }
            .takeWhile { x -> x <= limit.toInt()}
            .filter { x -> x % 2 == 0 }
            //.onEach { println("$it") }
            .sum()
            .toLong()
}