import org.clechasseur.adventofcode2019.IntcodeComputer
import org.clechasseur.adventofcode2019.Pt
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.round

object Day19 {
    private val input = listOf<Long>(109,424,203,1,21102,11,1,0,1105,1,282,21102,1,18,0,1105,1,259,1201,1,0,221,203,1,
            21101,0,31,0,1105,1,282,21102,38,1,0,1106,0,259,21001,23,0,2,22101,0,1,3,21101,0,1,1,21102,1,57,0,1105,1,
            303,2101,0,1,222,21001,221,0,3,20102,1,221,2,21102,259,1,1,21101,80,0,0,1106,0,225,21101,0,111,2,21102,1,
            91,0,1105,1,303,2102,1,1,223,20101,0,222,4,21102,1,259,3,21102,1,225,2,21102,1,225,1,21101,0,118,0,1105,1,
            225,20101,0,222,3,21102,148,1,2,21102,1,133,0,1106,0,303,21202,1,-1,1,22001,223,1,1,21102,148,1,0,1106,0,
            259,2101,0,1,223,20102,1,221,4,21001,222,0,3,21101,0,17,2,1001,132,-2,224,1002,224,2,224,1001,224,3,224,
            1002,132,-1,132,1,224,132,224,21001,224,1,1,21101,0,195,0,106,0,109,20207,1,223,2,20102,1,23,1,21102,-1,
            1,3,21101,0,214,0,1105,1,303,22101,1,1,1,204,1,99,0,0,0,0,109,5,2102,1,-4,249,22101,0,-3,1,21202,-2,1,2,
            21202,-1,1,3,21102,1,250,0,1105,1,225,22102,1,1,-4,109,-5,2106,0,0,109,3,22107,0,-2,-1,21202,-1,2,-1,
            21201,-1,-1,-1,22202,-1,-2,-2,109,-3,2105,1,0,109,3,21207,-2,0,-1,1206,-1,294,104,0,99,22102,1,-2,-2,109,
            -3,2106,0,0,109,5,22207,-3,-4,-1,1206,-1,346,22201,-4,-3,-4,21202,-3,-1,-1,22201,-4,-1,2,21202,2,-1,-1,
            22201,-4,-1,1,21202,-2,1,3,21101,0,343,0,1105,1,303,1105,1,415,22207,-2,-3,-1,1206,-1,387,22201,-3,-2,-3,
            21202,-2,-1,-1,22201,-3,-1,3,21202,3,-1,-1,22201,-3,-1,2,21201,-4,0,1,21102,384,1,0,1106,0,303,1105,1,415,
            21202,-4,-1,-4,22201,-4,-3,-4,22202,-3,-2,-2,22202,-2,-4,-4,22202,-3,-2,-3,21202,-4,-1,-2,22201,-3,-2,1,
            21202,1,1,-4,109,-5,2106,0,0)

    fun part1() = (0 until 50).flatMap { y ->
        (0 until 50).map { x ->
            IntcodeComputer(input, x.toLong(), y.toLong()).readOutput().toInt()
        }
    }.count { it == 1 }

    fun part2(): Int {
//        print("   ")
//        (0 until 100).forEach { print(it % 10) }
//        println()
//        (0 until 100).forEach { y ->
//            print("%3d".format(y))
//            (0 until 100).forEach { x ->
//                val whether = IntcodeComputer(input, x.toLong(), y.toLong()).readOutput().toInt()
//                print(if (whether == 1) '#' else '.')
//            }
//            println()
//        }
        // 2x2 -> (21, 18)
        // 3x3 -> (35, 30)
        // 4x4 -> (50, 43)
        val ratio = 21.0 / 18.0
        val topLeft = generateSequence(50.0 to 43.0) { (_, y) ->
            val newY = y + 1.0
            (newY * ratio) to newY
        }/*.filter { (_, y) ->
            val closestY = round(y)
            abs(y - closestY) < 0.1
        }*/.map { (x, y) ->
            Pt(round(x).toInt(), round(y).toInt())
        }.dropWhile {
            biggestSquare(it) < 100
        }.first()
        return topLeft.x * 10_000 + topLeft.y
    }

    private fun biggestSquare(topLeft: Pt): Int = min(verticalLineLength(topLeft), horizontalLineLength(topLeft))

    private fun verticalLineLength(pt: Pt): Int = generateSequence(pt) { Pt(it.x, it.y + 1) }.map {
        IntcodeComputer(input, it.x.toLong(), it.y.toLong()).readOutput().toInt()
    }.takeWhile { it == 1 }.count()

    private fun horizontalLineLength(pt: Pt): Int = generateSequence(pt) { Pt(it.x + 1, it.y) }.map {
        IntcodeComputer(input, it.x.toLong(), it.y.toLong()).readOutput().toInt()
    }.takeWhile { it == 1 }.count()
}
