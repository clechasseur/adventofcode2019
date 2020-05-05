import org.clechasseur.adventofcode2019.Day17Data
import org.clechasseur.adventofcode2019.IntcodeComputer
import org.clechasseur.adventofcode2019.Pt

object Day17 {
    private val input = Day17Data.input

    fun part1(): Int {
        val view = IntcodeComputer(input).readAllOutput().joinToString("") {
            it.toChar().toString()
        }.lineSequence().map {
            it.toList()
        }.filter { it.isNotEmpty() }.toList()

        var total = 0
        for (y in view.indices) {
            for (x in view[0].indices) {
                if (isScaffoldIntersection(x, y, view)) {
                    total += x * y
                }
            }
        }
        return total
    }

    fun part2(): Long {
        val robot = IntcodeComputer(listOf(2L) + input.drop(1))
        val view = robot.readAllOutput().joinToString("") {
            it.toChar().toString()
        }.lineSequence().map {
            it.toList()
        }.takeWhile { it.isNotEmpty() }.toList()

        val path = buildPath(view)
        val program = buildProgram(path).padded()

        return 0L
    }

    private fun isScaffoldIntersection(x: Int, y: Int, view: List<List<Char>>): Boolean
            = y > view.indices.first
            && y < view.indices.last
            && x > view[0].indices.first
            && x < view[0].indices.last
            && view[y][x] == '#'
            && view[y - 1][x] == '#'
            && view[y + 1][x] == '#'
            && view[y][x - 1] == '#'
            && view[y][x + 1] == '#'

    private fun buildPath(view: List<List<Char>>): List<BotMovement> {
        val taggedView = view.mapIndexed { y, line ->
            line.mapIndexed { x, c -> c to Pt(x, y) }
        }
        var botPos = taggedView.asSequence().flatten().filter { it.first == '^' }.single().second
        var heading = BotHeading.UP
        var moved = 0
        val path = mutableListOf<BotMovement>()
        var atEnd = false
        while (!atEnd) {
            val nextPos = botPos + heading.movement
            if (!inBounds(view, nextPos) || view[nextPos.y][nextPos.x] != '#') {
                if (moved != 0) {
                    path.add(MoveBotForward(moved))
                    moved = 0
                }
                val leftPos = botPos + heading.left.movement
                if (inBounds(view, leftPos) && view[leftPos.y][leftPos.x] == '#') {
                    path.add(TurnBotLeft())
                    heading = heading.left
                } else {
                    val rightPos = botPos + heading.right.movement
                    if (inBounds(view, rightPos) && view[rightPos.y][rightPos.x] == '#') {
                        path.add(TurnBotRight())
                        heading = heading.right
                    } else {
                        atEnd = true
                    }
                }
            } else {
                botPos += heading.movement
                moved++
            }
        }
        return path
    }

    private fun inBounds(view: List<List<Char>>, pt: Pt) = pt.y in view.indices && pt.x in view[0].indices

    private fun buildProgram(path: List<BotMovement>): BotProgram
            = buildProgram(path, BotProgram(listOf(listOf(path.first()))), path.drop(1))!!

    private fun buildProgram(path: List<BotMovement>, soFar: BotProgram, remaining: List<BotMovement>): BotProgram? = when {
        soFar.programs.size > 3 -> null
        remaining.isEmpty() -> if (soFar.validFor(path)) soFar else null
        else -> {
            buildProgram(path, BotProgram(
                    soFar.programs.dropLast(1) + listOf(soFar.programs.first() + remaining[0])
            ), remaining.drop(1)) ?: buildProgram(path, BotProgram(
                    soFar.programs + listOf(listOf(remaining[0]))
            ), remaining.drop(1))
        }
    }
}

private interface BotMovement

private class TurnBotLeft : BotMovement {
    override fun toString() = "L"
}

private class TurnBotRight : BotMovement {
    override fun toString() = "R"
}

private class MoveBotForward(val steps: Int) : BotMovement {
    override fun toString() = steps.toString()
}

private enum class BotHeading(val movement: Pt) {
    UP(Pt(0, -1)),
    DOWN(Pt(0, 1)),
    LEFT(Pt(-1, 0)),
    RIGHT(Pt(1, 0));

    val left: BotHeading get() = when (this) {
        UP -> LEFT
        LEFT -> DOWN
        DOWN -> RIGHT
        RIGHT -> UP
    }

    val right: BotHeading get() = when (this) {
        UP -> RIGHT
        RIGHT -> DOWN
        DOWN -> LEFT
        LEFT -> UP
    }
}

private data class BotProgram(val programs: List<List<BotMovement>>) {
    fun buildFor(path: List<BotMovement>): String {
        val a = (programs.firstOrNull() ?: emptyList()).joinToString(",")
        val b = (programs.drop(1).firstOrNull() ?: emptyList()).joinToString(",")
        val c = (programs.drop(2).firstOrNull() ?: emptyList()).joinToString(",")
        return path.joinToString(",").replace(a, "A").replace(b, "B").replace(c, "C")
    }

    fun validFor(path: List<BotMovement>): Boolean
            = programs.size <= 3
            && programs.all { it.joinToString(",").length <= 20 }
            && buildFor(path).matches(Regex("""^(?:A|B|C|,)*$"""))

    fun padded() = BotProgram((programs + List(3) { emptyList<BotMovement>() }).take(3))
}
