import org.clechasseur.adventofcode2019.Pt3D
import org.clechasseur.adventofcode2019.manhattan
import org.clechasseur.adventofcode2019.toPt3D
import kotlin.math.abs
import kotlin.math.sign

object Day12 {
    private val input = """
        <x=16, y=-8, z=13>
        <x=4, y=10, z=10>
        <x=17, y=-5, z=6>
        <x=13, y=-3, z=0>
    """.trimIndent()

    private val initialMoons = input.lineSequence().map { Moon(it.toPt3D(), Pt3D.ZERO) }.toList()

    fun part1() = initialMoons.move(1_000).sumBy { it.energy }

    fun part2() = generateSequence(initialMoons to 0L) {
        it.first.moveOnce() to it.second + 1
    }.dropWhile {
        it.second == 0L || it.first != initialMoons
    }.first().second

    private fun List<Moon>.move(steps: Int) = generateSequence(this) { it.moveOnce() }.drop(1).take(steps).last()

    private fun List<Moon>.moveOnce() = map { moon ->
        Moon(moon.position, this.filter { it != moon }.map {
            otherMoon -> velocityChange(moon, otherMoon)
        }.fold(moon.velocity) { acc, v -> acc + v })
    }.map {
        moon -> Moon(moon.position + moon.velocity, moon.velocity)
    }

    private fun velocityChange(moon: Moon, otherMoon: Moon) = velocityChange(moon.position, otherMoon.position)

    private fun velocityChange(position: Pt3D, otherPosition: Pt3D) = Pt3D(
            (otherPosition.x - position.x).sign,
            (otherPosition.y - position.y).sign,
            (otherPosition.z - position.z).sign
    )
}

private data class Moon(val position: Pt3D, val velocity: Pt3D) : Comparable<Moon> {
    override fun compareTo(other: Moon): Int = when (val cmp = position.compareTo(other.position)) {
        0 -> velocity.compareTo(other.velocity)
        else -> cmp
    }
}

private val Pt3D.energy get() = abs(x) + abs(y) + abs(z)

private val Moon.potentialEnergy get() = position.energy
private val Moon.kineticEnergy get() = velocity.energy
private val Moon.energy get() = potentialEnergy * kineticEnergy
