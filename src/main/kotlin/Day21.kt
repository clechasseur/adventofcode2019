import org.clechasseur.adventofcode2019.Day21Data
import org.clechasseur.adventofcode2019.IntcodeComputer

object Day21 {
    private val input = Day21Data.input

    fun part1(): Long {
        return runProgram("""
            NOT A J
            NOT B T
            OR T J
            NOT C T
            OR T J
            AND D J
            WALK
            
        """.trimIndent())
    }

    fun part2(): Long {
        return runProgram("""
            NOT A J
            NOT B T
            OR T J
            NOT C T
            OR T J
            AND D J
            AND E T
            OR H T
            AND T J
            RUN
            
        """.trimIndent())
    }

    private fun runProgram(program: String): Long {
        val droid = IntcodeComputer(input)
        droid.addAsciiInput(program)

        val output = droid.readAllOutput()
        if (output.last() > Char.MAX_VALUE.toLong()) {
            return output.last()
        }
        output.forEach { print(it.toChar()) }
        error("Did not make it across")
    }
}
