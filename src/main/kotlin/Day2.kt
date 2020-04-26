import org.clechasseur.adventofcode2019.IntcodeComputer

object Day2 {
    private val input = listOf(1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,1,9,19,1,19,5,23,2,23,13,27,1,10,27,31,2,31,6,35, 1,5,
            35,39,1,39,10,43,2,9,43,47,1,47,5,51,2,51,9,55,1,13,55,59,1,13,59,63,1,6,63,67,2,13,67,71,1,10,71,75,2,13,
            75,79,1,5,79,83,2,83,9,87,2,87,13,91,1,91,5,95,2,9,95,99,1,99,5,103,1,2,103,107,1,10,107,0,99,2,14,0,0)

    private const val target = 19690720

    fun part1() = run(12, 2)

    fun part2(): Int {
        val (noun, verb) = generateSequence(0 to 0) { when (it.second) {
            99 -> when (it.first) {
                99 -> null
                else -> it.first + 1 to 0
            }
            else -> it.first to it.second + 1
        } }.map { it to run(it.first, it.second) }.filter { it.second == target }.first().first
        return 100 * noun + verb
    }

    private fun run(noun: Int, verb: Int): Int {
        return IntcodeComputer(input.toMutableList().apply {
            this[1] = noun
            this[2] = verb
        }).memory[0]
    }
}
