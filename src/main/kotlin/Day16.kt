import kotlin.math.abs
import kotlin.math.min

object Day16 {
    private val input = """
        59738571488265718089358904960114455280973585922664604231570733151978336391124265667937788506879073944958411270241510791284757734034790319100185375919394328222644897570527214451044757312242600574353568346245764353769293536616467729923693209336874623429206418395498129094105619169880166958902855461622600841062466017030859476352821921910265996487329020467621714808665711053916709619048510429655689461607438170767108694118419011350540476627272614676919542728299869247813713586665464823624393342098676116916475052995741277706794475619032833146441996338192744444491539626122725710939892200153464936225009531836069741189390642278774113797883240104687033645
    """.trimIndent().asSequence().map { it.toString().toLong() }.toList()

    private val basePattern = listOf<Long>(0, 1, 0, -1)

    fun part1() = fft(input).take(100).last().take(8).joinToString("").toInt()

    fun part2(): Int {
        val bigInput = input.asSequence().keepGoing().take(10_000 * input.size).toList()
        val offset = bigInput.take(7).joinToString("").toInt()
        val result = tailFft(bigInput, offset).take(100).last()
        return result.drop(offset).take(8).joinToString("").toInt()
    }

    private fun fft(signal: List<Long>): Sequence<List<Long>> = generateSequence(signal) {
        prevSignal -> signal.indices.map { elementIdx ->
            var idx = elementIdx
            var result = 0L
            var sign = 1
            while (idx < prevSignal.size) {
                val readOffset = min(elementIdx, prevSignal.size - (idx + 1))
                if (sign > 0) {
                    for (valIdx in idx..(idx + readOffset)) {
                        result += prevSignal[valIdx]
                    }
                } else {
                    for (valIdx in idx..(idx + readOffset)) {
                        result -= prevSignal[valIdx]
                    }
                }
                sign = -sign
                idx += (elementIdx + 1) * 2
            }
            abs(result) % 10
        }
    }.drop(1)

    private fun tailFft(signal: List<Long>, offset: Int): Sequence<List<Long>> = generateSequence(signal) { prevSignal ->
        val nextSignal = MutableList(prevSignal.size) { 0L }
        nextSignal[nextSignal.size - 1] = prevSignal.last()
        for (i in (prevSignal.indices.last - 1) downTo offset) {
            nextSignal[i] = prevSignal[i] + nextSignal[i + 1]
        }
        for (i in (prevSignal.indices.last - 1) downTo offset) {
            nextSignal[i] = nextSignal[i] % 10
        }
        nextSignal
    }.drop(1)
}

private fun <T> Sequence<T>.keepGoing() = sequence {
    val values = toList()
    if (values.isNotEmpty()) {
        while (true) {
            yieldAll(values)
        }
    }
}
