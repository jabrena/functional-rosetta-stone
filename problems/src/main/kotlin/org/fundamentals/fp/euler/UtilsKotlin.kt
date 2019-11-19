package org.fundamentals.fp.euler

class UtilsKotlin {

    companion object UtilsKotlin {

        fun fibonnacci(): Sequence<Int> {

            return generateSequence(0 to 1) { Pair(it.second, it.first + it.second) }
                    .map { it.first }
        }

        fun fibonnacci(limit: Long): Sequence<Int> {

            return fibonnacci()
                    .takeWhile { x -> x <= limit.toInt() }
        }
    }
}