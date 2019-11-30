package arrow.composition;

import arrow.core.AndThen
import arrow.core.andThen
import arrow.core.extensions.list.foldable.foldLeft

fun main(args: Array<String>) {

        //sampleStart
        val f = (0..10000).toList()
        .fold({ x: Int -> x + 1 }) { acc, _ ->
                acc.andThen { it + 1 }
        }

        val f2 = (0..10000).toList()
        .foldLeft(AndThen { x: Int -> x + 1 }) { acc, _ ->
                acc.andThen { it + 1 }
        }
        //sampleEnd
        println("f(0) = ${f(0)}, f2(0) = ${f2(0)}")
}
