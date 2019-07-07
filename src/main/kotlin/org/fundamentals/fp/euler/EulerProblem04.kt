package org.fundamentals.fp.euler

fun reverse(number: Int) : String {
    return StringBuilder().append(number).reverse().toString()
}

fun isPalindrome(value: Int) : Boolean {
    return reverse(value) == value.toString()
}


fun KotlinSolution04(min : Int, max : Int) : Long {

    return IntRange(min, max)
            .flatMap { value ->
                IntRange(min, max)
                        .map { element -> element * value }
            }
            .filter { i -> isPalindrome(i) }
            .map { i -> i.toLong() }
            .max()!!
}