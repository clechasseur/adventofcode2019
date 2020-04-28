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

fun factors(n: Int): List<Int> {
    return generateSequence(1) { when {
        it < n -> it + 1
        else -> null
    } }.filter { n % it == 0 }.toList()
}

fun reduceFraction(numerator: Int, denominator: Int): Pair<Int, Int> {
    require(denominator != 0) { "Divide by zero error" }
    return when (numerator) {
        0 -> 0 to 1
        else -> {
            val gcd = factors(abs(numerator)).intersect(factors(abs(denominator))).last()
            (numerator / gcd) to (denominator / gcd)
        }
    }
}
