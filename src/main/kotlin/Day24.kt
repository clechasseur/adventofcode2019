import org.clechasseur.adventofcode2019.Direction
import org.clechasseur.adventofcode2019.Pt

object Day24 {
    private val input = """
        ..##.
        ..#..
        ##...
        #....
        ...##
    """.trimIndent()

    private val initialState = State(input.lineSequence().map { it.toList() }.toList())

    fun part1(): Int {
        val states = mutableListOf(initialState)
        while (true) {
            val newState = states.last().evolve()
            if (states.indexOf(newState) != -1) {
                return newState.biodiversity
            }
            states.add(newState)
        }
    }

    private data class State(val ground: List<List<Char>>) {
        val biodiversity: Int
            get() = generateSequence(1) { it * 2 }.zip(ground.asSequence().flatten()).map {
                if (it.second == '#') it.first else 0
            }.sum()

        fun evolve() = State(ground.indices.map { y ->
            ground[y].indices.map { x ->
                val bugs = numBugsAround(Pt(x, y))
                when (ground[y][x]) {
                    '#' -> if (bugs == 1) '#' else '.'
                    '.' -> if (bugs in 1..2) '#' else '.'
                    else -> error("Wrong ground at ($x, $y): ${ground[y][x]}")
                }
            }
        })

        private fun numBugsAround(pt: Pt): Int = Direction.values().map { direction ->
            if (at(pt + direction.displacement) == '#') 1 else 0
        }.sum()

        private fun at(pt: Pt): Char = when {
            pt.x in ground[0].indices && pt.y in ground.indices -> ground[pt.y][pt.x]
            else -> '.'
        }
    }
}
