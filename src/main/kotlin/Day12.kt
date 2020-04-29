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

    fun part2(): Long {
        val history = UniverseHistory()
        var state = initialMoons
        var step = 0L
        var duration: Long? = null
        while (duration == null) {
            val pastStep = history.seenAtStep(state)
            if (pastStep != null) {
                duration = step - pastStep
            } else {
                history.addState(state, step)
                state = state.moveOnce()
                step++
            }
        }
        return duration
    }

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

private class UniverseHistory {
    private val history = mutableMapOf<Int, MutableList<Pair<String, Long>>>()

    fun seenAtStep(state: List<Moon>): Long? {
        val stateHistoryString = state.stateHistoryString
        return when (val stateList = history[state.stateKey]) {
            null -> null
            else -> stateList.find { it.first == stateHistoryString }?.second
        }
    }

    fun addState(state: List<Moon>, step: Long) {
        history.getOrPut(state.stateKey) { mutableListOf() }.add(state.stateHistoryString to step)
    }

    private val List<Moon>.stateKey get() = map { manhattan(it.position, Pt3D.ZERO) }.sum()
    private val List<Moon>.stateHistoryString get() = joinToString("") { it.toString() }
}
