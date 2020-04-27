package org.clechasseur.adventofcode2019.math

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