import kotlin.math.abs

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

    fun part1(): Long {
        val techniques = input.lineSequence().map { it.toTechnique() }
        val deck = Deck(10_007)
        techniques.forEach { it(deck) }
        return deck.cards.indexOf(2019L).toLong()
    }

    fun part2() = 0L
}

private class Deck(val size: Long) {
    var cards = (0 until size).toList()
}

private interface Technique {
    operator fun invoke(deck: Deck)
}

private class DealIntoNewStack : Technique {
    override fun invoke(deck: Deck) {
        deck.cards = deck.cards.reversed()
    }
}

private class Cut(val n: Long) : Technique {
    override fun invoke(deck: Deck) {
        val absn = if (n >= 0) n else deck.size - abs(n)
        deck.cards = deck.cards.drop(absn.toInt()) + deck.cards.take(absn.toInt())
    }
}

private class DealWithIncrement(val n: Long): Technique {
    override fun invoke(deck: Deck) {
        val indexes = (0 until deck.size).map { (it * n) % deck.size }.zip(0 until deck.size)
        deck.cards = List(deck.size.toInt()) { idx -> deck.cards[indexes.find { it.first == idx.toLong() }!!.second.toInt()] }
    }
}

private fun String.toTechnique(): Technique = when {
    this == "deal into new stack" -> DealIntoNewStack()
    startsWith("cut ") -> Cut(substring(4).toLong())
    startsWith("deal with increment ") -> DealWithIncrement(substring(20).toLong())
    else -> error("Unknown technique: $this")
}
