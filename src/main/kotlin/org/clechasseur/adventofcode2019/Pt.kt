package org.clechasseur.adventofcode2019

import kotlin.math.abs

data class Pt(val x: Int, val y: Int) : Comparable<Pt> {
    operator fun plus(pt: Pt) = Pt(x + pt.x, y + pt.y)

    override fun compareTo(other: Pt): Int = manhattan(this, Pt(0, 0)) - manhattan(other, Pt(0, 0))
}

fun manhattan(a: Pt, b: Pt): Int = abs(a.x - b.x) + abs(a.y - b.y)
