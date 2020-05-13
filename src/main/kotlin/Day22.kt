import kotlin.math.abs

object Day22 {
    private const val spaceDeckSize = 10_007

    fun part1(): Int {
        val deck = Deck(spaceDeckSize)
        shuffle(deck)
        return deck.cards.indexOf(2019)
    }

    fun part2(): Int {
        val deck = Deck(spaceDeckSize)
        var n = 0
        do {
            shuffle(deck)
            n++
        } while (deck.cards != (0 until spaceDeckSize).toList())
        return n
    }

    private fun shuffle(deck: Deck) {
        deck.apply {
            cut(9002)
            dealWithIncrement(17)
            cut(-4844)
            dealWithIncrement(26)
            cut(-4847)
            dealWithIncrement(74)
            dealIntoNewStack()
            dealWithIncrement(75)
            dealIntoNewStack()
            dealWithIncrement(64)
            cut(9628)
            dealWithIncrement(41)
            cut(9626)
            dealWithIncrement(40)
            cut(-7273)
            dealIntoNewStack()
            dealWithIncrement(20)
            dealIntoNewStack()
            cut(2146)
            dealWithIncrement(7)
            cut(-3541)
            dealWithIncrement(25)
            cut(-1343)
            dealWithIncrement(42)
            cut(-2608)
            dealWithIncrement(75)
            cut(-9258)
            dealIntoNewStack()
            cut(-2556)
            dealIntoNewStack()
            cut(-5363)
            dealIntoNewStack()
            cut(-8143)
            dealWithIncrement(15)
            cut(-9309)
            dealWithIncrement(65)
            cut(-5470)
            dealWithIncrement(9)
            dealIntoNewStack()
            dealWithIncrement(64)
            cut(137)
            dealWithIncrement(40)
            dealIntoNewStack()
            cut(5042)
            dealWithIncrement(74)
            cut(4021)
            dealWithIncrement(39)
            cut(-5108)
            dealWithIncrement(50)
            cut(-6608)
            dealWithIncrement(64)
            cut(4438)
            dealWithIncrement(48)
            cut(7916)
            dealWithIncrement(23)
            cut(-6677)
            dealWithIncrement(27)
            cut(-1758)
            dealWithIncrement(32)
            cut(-3104)
            dealWithIncrement(37)
            cut(9453)
            dealWithIncrement(20)
            dealIntoNewStack()
            dealWithIncrement(6)
            cut(8168)
            dealWithIncrement(69)
            cut(6704)
            dealWithIncrement(45)
            cut(-9423)
            dealWithIncrement(2)
            cut(-3498)
            dealWithIncrement(39)
            dealIntoNewStack()
            cut(6051)
            dealWithIncrement(42)
            cut(7140)
            dealIntoNewStack()
            dealWithIncrement(73)
            cut(-8201)
            dealIntoNewStack()
            dealWithIncrement(13)
            cut(2737)
            dealWithIncrement(3)
            cut(-4651)
            dealIntoNewStack()
            dealWithIncrement(30)
            cut(-1505)
            dealWithIncrement(59)
            dealIntoNewStack()
            dealWithIncrement(55)
            cut(8899)
            dealWithIncrement(39)
            cut(8775)
            dealWithIncrement(57)
            cut(-1919)
            dealWithIncrement(39)
            cut(-3845)
            dealWithIncrement(8)
            cut(-4202)
        }
    }
}

private class Deck(val size: Int) {
    var cards = (0 until size).toList()

    fun cut(n: Int) {
        cards = if (n >= 0) {
            cards.drop(n) + cards.take(n)
        } else {
            cards.takeLast(abs(n)) + cards.dropLast(abs(n))
        }
    }

    fun dealIntoNewStack() {
        cards = cards.reversed()
    }

    fun dealWithIncrement(n: Int) {
        val indexes = (0 until size).map { (it * n) % size }.zip(0 until size)
        cards = List(size) { idx -> cards[indexes.find { it.first == idx }!!.second] }
    }
}
