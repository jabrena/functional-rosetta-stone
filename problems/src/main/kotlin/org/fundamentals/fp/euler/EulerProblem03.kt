package org.fundamentals.fp.euler

/**
 * Largest prime factor
 * https://projecteuler.net/problem=3
 *
 * The prime factors of 13195 are 5, 7, 13 and 29.
 *
 * What is the largest prime factor of the number 600851475143 ?
 *
 * Scenario 13195
 *
 * Given primeFactor
 * When 13195
 * Then 29 [5, 7, 13, 29]
 *
 * Scenario 600851475143
 *
 * Given primeFactor
 * When 600851475143
 * Then ?
 */
fun KotlinSolution03(limit : Long) : Long {

    return limit.primeFactors().max()!!
}

/**
 * Checks if this number is a multiple of the provided number _(eg. this % other == 0)_.
 *
 * _Reference: [http://mathworld.wolfram.com/Multiple.html]_
 *
 * @return true if the receiver is a multiple of the provided number
 */
infix fun Long.isMultipleOf(other: Long) = this % other == 0L

/**
 * Returns the square root of the receiver _(eg. from Math.sqrt)_.
 *
 * _Reference: [http://mathworld.wolfram.com/SquareRoot.html]_
 *
 * @return the square root of the receiver
 */
fun Double.squareRoot() = Math.sqrt(this)

/**
 * @see [Double.squareRoot]
 */
fun Double.sqrt() = this.squareRoot()

/**
 * Returns the integer part of the receiver _(eg. removes the decimal part)_.
 *
 * Example: `10.25 becomes 10`
 *
 * _Reference: [http://mathworld.wolfram.com/FloorFunction.html]_
 *
 * @return the integer part of the receiver
 */
fun Double.floor() = this.toLong()

/**
 * @see [Double.squareRoot]
 */
fun Long.sqrt() = this.toDouble().sqrt()


/**
 * Checks if the receiver number is a prime number _(eg. only divisors are 1 and itself)_.
 *
 * _Reference: [http://mathworld.wolfram.com/PrimeNumber.html]_
 *
 * @return true if the receiver number is a prime
 */
fun Long.isPrime() = this > 1 && (2..this.sqrt().floor()).all { !(this isMultipleOf it) }

/**
 * Returns a List containing all prime factors of the receiver numbers _(eg. factorization of receiver into its consituent primes)_.
 *
 * _Reference: [http://mathworld.wolfram.com/PrimeFactor.html]_
 *
 * @return a List containing all prime factors of the receiver number
 */
fun Long.primeFactors(): List<Long> = if (isPrime()) listOf(this) else {
    val nextPrimeFactor = (2..this.sqrt().floor()).find { this isMultipleOf it && it.isPrime() }
    if (nextPrimeFactor == null) emptyList()
    else listOf(nextPrimeFactor) + (this / nextPrimeFactor).primeFactors()
}