package org.clechasseur.adventofcode2019.math

import kotlin.math.abs

fun <T> permutations(elements: List<T>): Sequence<List<T>> {
    if (elements.size == 1) {
        return sequenceOf(listOf(elements.first()))
    }

    return elements.asSequence().flatMap { elem ->
        val subIt = permutations(elements - elem).iterator()
        generateSequence { when (subIt.hasNext()) {
            true -> listOf(elem) + subIt.next()
            false -> null
        } }
    }
}

fun generatePairSequence(firstRange: IntRange, secondRange: IntRange): Sequence<Pair<Int, Int>> {
    return generateSequence(firstRange.first to secondRange.first) { when (it.second) {
        secondRange.last -> when (it.first) {
            firstRange.last -> null
            else -> it.first + 1 to secondRange.first
        }
        else -> it.first to it.second + 1
    } }
}

fun factors(n: Long): List<Long> {
    return generateSequence(1L) { when {
        it < n -> it + 1L
        else -> null
    } }.filter { n % it == 0L }.toList()
}

fun greatestCommonDenominator(a: Long, b: Long): Long {
    // https://en.wikipedia.org/wiki/Euclidean_algorithm
    require(a >= 0 && b >= 0) { "Cannot find GCD for negative numbers" }
    var rMinus2 = a % b
    var rMinus1 = b % rMinus2
    var r = rMinus2 % rMinus1
    while (r != 0L) {
        rMinus2 = rMinus1
        rMinus1 = r
        r = rMinus2 % rMinus1
    }
    return rMinus1
}

fun leastCommonMultiple(a: Long, b: Long): Long {
    // https://en.wikipedia.org/wiki/Least_common_multiple
    require(a >= 0 && b >= 0) { "Cannot find LCM for negative numbers" }
    return a / greatestCommonDenominator(a, b) * b
}

fun reduceFraction(numerator: Long, denominator: Long): Pair<Long, Long> {
    require(denominator != 0L) { "Divide by zero error" }
    return when (numerator) {
        0L -> 0L to 1L
        else -> {
            val gcd = greatestCommonDenominator(abs(numerator), abs(denominator))
            (numerator / gcd) to (denominator / gcd)
        }
    }
}
