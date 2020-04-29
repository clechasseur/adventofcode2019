import org.clechasseur.adventofcode2019.IntcodeComputer
import org.clechasseur.adventofcode2019.Pt
import kotlin.math.min

object Day11 {
    private val input = listOf<Long>(3,8,1005,8,302,1106,0,11,0,0,0,104,1,104,0,3,8,102,-1,8,10,101,1,10,10,4,10,1008,
            8,0,10,4,10,101,0,8,29,1006,0,78,2,1007,9,10,3,8,1002,8,-1,10,1001,10,1,10,4,10,1008,8,1,10,4,10,1002,8,1,
            58,1006,0,7,3,8,1002,8,-1,10,101,1,10,10,4,10,1008,8,0,10,4,10,1002,8,1,83,2,1009,4,10,3,8,102,-1,8,10,1001,
            10,1,10,4,10,1008,8,0,10,4,10,1002,8,1,109,1,106,11,10,1006,0,16,3,8,1002,8,-1,10,1001,10,1,10,4,10,1008,8,
            1,10,4,10,102,1,8,138,2,108,0,10,1,101,14,10,1,1109,1,10,3,8,1002,8,-1,10,101,1,10,10,4,10,1008,8,0,10,4,
            10,102,1,8,172,2,3,10,10,1006,0,49,3,8,1002,8,-1,10,101,1,10,10,4,10,1008,8,1,10,4,10,1001,8,0,201,1006,0,
            28,2,3,15,10,2,109,12,10,3,8,1002,8,-1,10,1001,10,1,10,4,10,108,0,8,10,4,10,1001,8,0,233,3,8,102,-1,8,10,
            1001,10,1,10,4,10,108,1,8,10,4,10,101,0,8,255,3,8,1002,8,-1,10,1001,10,1,10,4,10,108,1,8,10,4,10,102,1,8,
            277,2,1107,9,10,101,1,9,9,1007,9,946,10,1005,10,15,99,109,624,104,0,104,1,21101,0,932856042280,1,21101,0,
            319,0,1105,1,423,21101,0,387512640296,1,21101,330,0,0,1106,0,423,3,10,104,0,104,1,3,10,104,0,104,0,3,10,
            104,0,104,1,3,10,104,0,104,1,3,10,104,0,104,0,3,10,104,0,104,1,21101,0,46266346499,1,21102,1,377,0,1105,1,
            423,21102,1,46211836967,1,21102,1,388,0,1105,1,423,3,10,104,0,104,0,3,10,104,0,104,0,21102,1,825460941588,
            1,21102,411,1,0,1106,0,423,21101,709475738388,0,1,21102,1,422,0,1105,1,423,99,109,2,21201,-1,0,1,21101,0,
            40,2,21102,454,1,3,21101,0,444,0,1106,0,487,109,-2,2106,0,0,0,1,0,0,1,109,2,3,10,204,-1,1001,449,450,465,
            4,0,1001,449,1,449,108,4,449,10,1006,10,481,1102,1,0,449,109,-2,2106,0,0,0,109,4,2102,1,-1,486,1207,-3,0,
            10,1006,10,504,21101,0,0,-3,22101,0,-3,1,21201,-2,0,2,21102,1,1,3,21102,1,523,0,1105,1,528,109,-4,2105,1,
            0,109,5,1207,-3,1,10,1006,10,551,2207,-4,-2,10,1006,10,551,22101,0,-4,-4,1105,1,619,22102,1,-4,1,21201,-3,
            -1,2,21202,-2,2,3,21101,570,0,0,1106,0,528,22102,1,1,-4,21102,1,1,-1,2207,-4,-2,10,1006,10,589,21101,0,0,
            -1,22202,-2,-1,-2,2107,0,-3,10,1006,10,611,21201,-1,0,1,21101,611,0,0,106,0,486,21202,-2,-1,-2,22201,-4,
            -2,-4,109,-5,2105,1,0)

    private const val black = 0
    private const val white = 1
    private val colorsToString = mapOf(
            black to ' ',
            white to '#'
    )

    fun part1() = runRobotRoutine().size

    fun part2(): String {
        val finalPanels = runRobotRoutine(Pt(0, 0) to white).moveToPositive()
        val maxX = finalPanels.map { it.key.x }.max()!!
        val maxY = finalPanels.map { it.key.y }.max()!!
        return (maxY downTo 0).joinToString("\n") { y ->
            (0..maxX).map { x -> colorsToString[finalPanels.getOrDefault(Pt(x, y), black)] }.joinToString("")
        }
    }

    private fun runRobotRoutine(vararg initialPanels: Pair<Pt, Int>): Map<Pt, Int> {
        val panels = initialPanels.toMap().toMutableMap()
        val computer = IntcodeComputer(input)
        var robot = Pt(0, 0)
        var direction = RobotDirection.UP
        while (!computer.done) {
            computer.addInput(panels.getOrDefault(robot, black).toLong())
            val output = computer.readAllOutput()
            if (output.isNotEmpty()) {
                require(output.size == 2) { "Wrong number of output: ${output.size}" }
                panels[robot] = output[0].toInt()
                require(panels[robot] in black..white) { "Wrong panel color: ${panels[robot]}" }
                direction = when (output[1]) {
                    0L -> direction.left
                    1L -> direction.right
                    else -> error("Wrong direction output: ${output[1]}")
                }
                robot = robot.move(direction)
            }
        }
        return panels
    }
}

private enum class RobotDirection(val displacement: Pt) {
    LEFT(Pt(-1, 0)),
    UP(Pt(0, 1)),
    RIGHT(Pt(1, 0)),
    DOWN(Pt(0, -1));

    val left: RobotDirection get() = when (this) {
        LEFT -> DOWN
        else -> values()[ordinal - 1]
    }

    val right: RobotDirection get() = when (this) {
        DOWN -> LEFT
        else -> values()[ordinal + 1]
    }
}

private fun Pt.move(direction: RobotDirection) = this + direction.displacement

private fun Map<Pt, Int>.moveToPositive(): Map<Pt, Int> {
    val minX = min(map { it.key.x }.min()!!, 0)
    val minY = min(map { it.key.y }.min()!!, 0)
    return map { Pt(it.key.x - minX, it.key.y - minY) to it.value }.toMap()
}
