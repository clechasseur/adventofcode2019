import kotlin.math.max

object Day22 {
    private val input = """
        cut 9002
        deal with increment 17
        cut -4844
        deal with increment 26
        cut -4847
        deal with increment 74
        deal into new stack
        deal with increment 75
        deal into new stack
        deal with increment 64
        cut 9628
        deal with increment 41
        cut 9626
        deal with increment 40
        cut -7273
        deal into new stack
        deal with increment 20
        deal into new stack
        cut 2146
        deal with increment 7
        cut -3541
        deal with increment 25
        cut -1343
        deal with increment 42
        cut -2608
        deal with increment 75
        cut -9258
        deal into new stack
        cut -2556
        deal into new stack
        cut -5363
        deal into new stack
        cut -8143
        deal with increment 15
        cut -9309
        deal with increment 65
        cut -5470
        deal with increment 9
        deal into new stack
        deal with increment 64
        cut 137
        deal with increment 40
        deal into new stack
        cut 5042
        deal with increment 74
        cut 4021
        deal with increment 39
        cut -5108
        deal with increment 50
        cut -6608
        deal with increment 64
        cut 4438
        deal with increment 48
        cut 7916
        deal with increment 23
        cut -6677
        deal with increment 27
        cut -1758
        deal with increment 32
        cut -3104
        deal with increment 37
        cut 9453
        deal with increment 20
        deal into new stack
        deal with increment 6
        cut 8168
        deal with increment 69
        cut 6704
        deal with increment 45
        cut -9423
        deal with increment 2
        cut -3498
        deal with increment 39
        deal into new stack
        cut 6051
        deal with increment 42
        cut 7140
        deal into new stack
        deal with increment 73
        cut -8201
        deal into new stack
        deal with increment 13
        cut 2737
        deal with increment 3
        cut -4651
        deal into new stack
        deal with increment 30
        cut -1505
        deal with increment 59
        deal into new stack
        deal with increment 55
        cut 8899
        deal with increment 39
        cut 8775
        deal with increment 57
        cut -1919
        deal with increment 39
        cut -3845
        deal with increment 8
        cut -4202
    """.trimIndent()

    // Stole this one from: https://github.com/nibarius/aoc/blob/master/src/main/aoc2019/Day22.kt

    fun part1(): Long {
        val deckSize = 10_007L
        val techniques = reduce(input.lineSequence().toList().flatMap { it.toTechniques(deckSize) }, deckSize)
        return finalPositionForCard(card = 2019L, techniques = techniques, deckSize = deckSize)
    }

    fun part2(): Long {
        val deckSize = 119_315_717_514_047L
        val repeats = 101_741_582_076_661L
        val targetPosition = 2020L

        val shufflesLeftUntilInitialState = deckSize - 1 - repeats

        val techniques = reduce(input.lineSequence().toList().flatMap { it.toTechniques(deckSize) }, deckSize)
        val reduced = reduce(techniques, deckSize)
        val repeated = repeat(reduced, shufflesLeftUntilInitialState, deckSize)

        return finalPositionForCard(card = targetPosition, techniques = repeated, deckSize = deckSize)
    }
}

private interface Technique {
    fun nextPositionForCard(card: Long, deckSize: Long): Long
    fun canBeCombinedWith(other: Technique): Boolean
    fun combine(other: Technique, deckSize: Long): List<Technique>
}

private class Cut(val n: Long) : Technique {
    override fun nextPositionForCard(card: Long, deckSize: Long): Long = Math.floorMod(card - n, deckSize)

    override fun canBeCombinedWith(other: Technique): Boolean = true

    override fun combine(other: Technique, deckSize: Long): List<Technique> = when (other) {
        is Cut -> listOf(Cut(Math.floorMod(n + other.n, deckSize)))
        is DealWithIncrement -> listOf(DealWithIncrement(other.n), Cut(mulMod(n, other.n, deckSize)))
        else -> error("Invalid technique combination")
    }
}

private class DealWithIncrement(val n: Long): Technique {
    override fun nextPositionForCard(card: Long, deckSize: Long): Long = Math.floorMod(card * n, deckSize)

    override fun canBeCombinedWith(other: Technique): Boolean = other !is Cut

    override fun combine(other: Technique, deckSize: Long): List<Technique> = when (other) {
        is DealWithIncrement -> listOf(DealWithIncrement(mulMod(n, other.n, deckSize)))
        else -> error("Invalid technique combination")
    }
}

private fun String.toTechniques(deckSize: Long): List<Technique> = when {
    startsWith("cut ") -> listOf(Cut(substring(4).toLong()))
    startsWith("deal with increment ") -> listOf(DealWithIncrement(substring(20).toLong()))
    this == "deal into new stack" -> listOf(DealWithIncrement(deckSize - 1L), Cut(1L))
    else -> error("Unknown technique: $this")
}

private fun mulMod(a: Long, b: Long, mod: Long): Long {
    return a.toBigInteger().multiply(b.toBigInteger()).mod(mod.toBigInteger()).longValueExact()
}

private fun reduce(techniques: List<Technique>, deckSize: Long): List<Technique> {
    var process = techniques
    while (process.size > 2) {
        var offset = 0
        while (offset < process.size - 1) {
            if (process[offset].canBeCombinedWith(process[offset + 1])) {
                val combined = process[offset].combine(process[offset + 1], deckSize)
                process = process.subList(0, offset) + combined + process.subList(offset + 2, process.size)
                offset = max(0, offset - 1)
            } else {
                offset++
            }
        }
    }
    return process
}

private fun repeat(techniques: List<Technique>, times: Long, deckSize: Long): List<Technique> {
    var current = techniques
    val res = mutableListOf<Technique>()
    for (bit in times.toString(2).reversed()) {
        if (bit == '1') {
            res.addAll(current)
        }
        current = reduce(current + current, deckSize)
    }
    return reduce(res, deckSize)
}

private fun finalPositionForCard(card: Long, techniques: List<Technique>, deckSize: Long): Long {
    return techniques.fold(card) { pos, technique -> technique.nextPositionForCard(pos, deckSize) }
}
