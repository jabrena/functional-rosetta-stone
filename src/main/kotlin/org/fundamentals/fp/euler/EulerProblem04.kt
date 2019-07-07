package org.fundamentals.fp.euler

fun reverse(number: Int) : String {

    return StringBuilder().append(number).reverse().toString()
}

fun isPalindrome(value: Int) : Boolean {

    return reverse(value) == value.toString()
}

fun crossProduct(min: Int, max: Int, value: Int): List<Int> {

    return IntRange(min, max)
            .map { element -> element * value }
}

fun KotlinSolution04(min : Int, max : Int) : Int {

    return IntRange(min, max)
            .flatMap { value -> crossProduct(min, max, value) }
            .filter { i -> isPalindrome(i) }
            .max()!!
}