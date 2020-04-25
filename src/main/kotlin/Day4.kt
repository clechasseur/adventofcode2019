object Day4 {
    private val input = 284_639..748_759

    private val adjDigitsRegex = Regex("(\\d)\\1")
    private val exactlyTwoAdjDigitsRegex = Regex("""^(\d)\1(?!\1)|(\d)((?!\2)\d)\3(?:(?!\3)|$)""")

    fun part1() {
        print("Day 4, part 1: ${input.filter { it.twoAdjescentDigits() && it.everIncreasing() }.count()}")
    }

    fun part2() {
        print("Day 4, part 2: ${input.filter { it.exactlyTwoAdjescentDigits() && it.everIncreasing() }.count()}")
    }

    private fun Int.twoAdjescentDigits() = adjDigitsRegex.find(this.toString()) != null
    private fun Int.exactlyTwoAdjescentDigits() = exactlyTwoAdjDigitsRegex.find(this.toString()) != null
    private fun Int.everIncreasing() = this.toString().asSequence().sorted().joinToString("").toInt() == this
}
