import org.clechasseur.adventofcode2019.Pt
import org.clechasseur.adventofcode2019.dij.Dijkstra
import org.clechasseur.adventofcode2019.dij.Graph

object Day18 {
    private val input = """
        #################################################################################
        #.....#...........#....s..#.....#.......#...#...#...#...#.......#...........#...#
        #.#.#.#####.#.#####.#####.#.#####.#####.#.#.#.#.#.###.#.#.#.###.#.#.#########.#.#
        #.#.#.......#.#.........#.#.......#...#.#.#...#...#...#...#...#.#.#...#.......#.#
        #.#.#########.#.#########.#.#######.###.#.#####.###.#########.###.###.#.#######.#
        #l#...#...#...#...#.......#...#.....#...#.#.#...#...#.......#...#.#...#.#.......#
        #.###.#.###.#####.#.#########.###.#.#.###.#.#.###.#######.#.###.#.#.#.#.#.#####.#
        #.#...#...Z.#.#...#...#...........#.#...#.#.#.#.#.......#.#...#...#.#.#.#.....#.#
        #.#.#####.###.#.#####.###########.#####.#.#.#.#.#######.#.#########.###.#####M###
        #.#.#...#.#...#.....#.......#.....#...#.#.#...........#.#.......#...#...#...#...#
        #E#.#.#.#.###.#####.#######.#######.#.#.#.#######.#####.#.###.#.#.###.###.#####.#
        #.#...#.#.....#.....#.....#...#.....#.#.#.....#...#.....#.#.#.#.#...#.#.......#.#
        #.#####.#######.#########.###.#.#####.#.#.###.#####.#####.#.#.#####.#.#######.#.#
        #.#...#.........#...........#.#...#.....#...#.......#...#...#.....#.#.......#.#.#
        #.###.###########.#.#######.#.###.#####.###.#####.###.#.#####.#.#.#.#######.#.#.#
        #...#.....#x....#.#.#.....#.#...#.....#.#.#.#...#.....#.#...#.#.#.#.....L.#...#.#
        ###.#.#.#.###.#.#.###.###.###.#.#####.#.#.#.#.#.#######.#.#.###.#.#####.#####.#.#
        #.#.#.#.#...#.#...#...#.#...#.#.#.....#.#...#.#..b....#...#.....#.#...#.......#.#
        #.#.###.###.#.###.#.###.###.###.#.#####.#.###C#######.#############.#.#########.#
        #.#...#o#...#.#.#.#.#.....#.....#.....#.#..f#.#.....#.........#.....#.#.....#...#
        #.###.#.###.#.#.#.#.#####.#######.###.#####.#.#####.#########B#.#####.#.###.#.#.#
        #...#.#...#...#.#.#.#...#.......#...#...#...#.....#.........#.#.#.......#...#.#.#
        #.#.#.###.#####.#.#.###.###.###.#######.#.#######.#####.###.###.#.#######.###.#.#
        #.#.#.....#.....#.#...#...#...#.........#.#...N.#c....#...#t..P.#...#...#.....#.#
        #.#########.#####.#.#####.###.#####.#####.#.#########H###.###########.#.#.#######
        #.........#..g....#.....#...#.#.....#...#.#...#.....#...#.#...........#.#.#.....#
        #.#####.###.###########.###.#.#######.#.#.###.#.#.#####.#.#.###########.###.###.#
        #...#.#...#...#...#.....#...#.#.......#.#.#...#.#.......#...#.......#.#.......#.#
        ###.#.###.###.###.#.#####.###.#.#######.#.#.#.#.#########.#####V###.#.#########.#
        #.#.#.#.....#.#...#.......#.#...#...#...#...#.#......y#.#.#.....#.#...#.I.....#.#
        #.#.#.#.#.###.#.###########.#.###.###.#.#####.#######.#.#.#.#####.###.#.#####.#.#
        #.....#.#.#...#.......#.......#...#...#.#.....#.#.....#.....#...#...#.#.#.......#
        #.#####.###.#######.#.#.#######.#.#.#####.#####.#.#############.#.#.#.#.#########
        #.#.#a..#...#.....#.#...#.......#.#.....#.#.....#...#..j#.......#.#.#.#.....#...#
        #.#.#.#.#.###.###.#.###########.#.#####.#.#.#######.#.#.###.#####.#.#.#####.#.#.#
        #.#.#.#.#.....#...#.#...........#...#...#.#.......#...#...#.....#.#..d....#...#.#
        #.#.#F#.#######.###.#.#########.#####.###.#.###.#########.#####.#.#############.#
        #.#.#.#.......#...#...#.........#...#...#.#...#.....#.#...#...#...#...#...#...U.#
        #.#.#.###########.###############.#.###.#.#######.#.#.#.###.#.#####.#.#.#.#.###.#
        #...#.............................#...............#...#.....#..q....#...#..r#...#
        #######################################.@.#######################################
        #.........#.....#.........#.........#.........#...........#.........#.........#.#
        #.#######.#.#.###.#####.#.#####.###.#.#.#.#####.###.#######.#####.###.###.###.#G#
        #.#.....#.#.#.......#...#.......#.#.#.#.#.#.....#.#.......#.#.....#.A...#...#...#
        #.#.###.#.###.#######.###########.#.###.#.#.#####.#######.#.#.#####.#######.###.#
        #.#...#.#...#.#...#...#.......#.....#...#.........#.....#.#.#.......#.....#...#.#
        #.#####.###.#.###.#.#.#.#####.#.#####.#.###########.###.#.#.#########.###.###.#.#
        #.....#.....#.....#.#.#.#.#...#.#...#.#.#...#.....#.#.#.#...#.....#...#.......#.#
        #.###.#.#########.#.###.#.#.###.#.#.#.#.#.#.#.###.#.#.#.#.###.###.#.#X#########.#
        #w..#.#.#.....#...#...#.#.#.#.....#n..#.#.#.#.#.#...#.#.#.#...#...#.#.#...#...#.#
        ###.#.#.###.###.#####.#.#.#.#############.#.#.#.#####.#.#.#.###.###.#.#.#.#.###.#
        #...#.#...#.#...#...#...#.#.#...........#.#...#.......#.#.#.#.#.#...#.#.#.#.....#
        #.###.###.#.#.###.#######.#.#.#########.#.###########.#.###.#.#.###.###.#.#######
        #.#...#.#.#.....#.........#...........#.#...#.........#.....#.#...#...#.#.......#
        #.#.###.#.#####.#####.#########.#######.#.#.#.###############.###.###.#.#######.#
        #.#.#.#..m#...#.......#...#...#.#...#...#.#.#...#.......#.......#...#...#.#.....#
        ###.#.#.###.#.#########.#.#.#.###.#.#.#.#.#.###.#.#####.#.###.#####.#.###.#.#####
        #...#...#...#...........#...#.....#...#.#.#...#.#.#.#...#...#.....#.#.#...#.....#
        #.###.###.#.###########################.#.###.#.#.#.#.###.#######.#.#.#.#O#####.#
        #p#.#.#...#.#...J.......#...#.......#...#...#.#.....#.....#.....#.#.#...#...#...#
        #.#.#K#.###.#.#####.###.#.#.#.#####.#.#####.#.#############.###.#.#.#######.#.#.#
        #.#.#.#...#.#.#.....#.#...#.#.#.....#...#.#.#...#.........#.#...#.#...#.#e..#.#.#
        #.#.#.#.#.#.#.###.###.#####.###.#######.#.#.###.#.#######.#.#####.###.#.#.###.#.#
        #.#.#.#.#.#.#...#...#h..#.#.Q.#.....#...#.#.#.#.#...#...#.#.#.#.....#.#.#...#.#.#
        #.#.#.###.#.###.#######.#.###.#.###.#.###.#.#.#.###.###.#.#.#.#.#.#.#.#.#.###.#.#
        #.#.#.#...#...#.....Y...#...#.#.#.#...#.#...#.#.........#.#.#...#.#.#.#...#...#.#
        #.#.#.#.###.#############.###.#.#.#####.#.###.###########.#.###.#.#.#.#####.###.#
        #.#.....#...#.........#.R...#.#.#.......#.#...#.....#.....#...#.#.#.#.......#...#
        #.#######.###.###.#####.###.#.#.#.#####.#.#.#.#.###.#.#.#####.#.#.#.#########.###
        #.....#...#...#.#.#...#.#.#...#.#...#.#.#.#.#.#.#.....#.#.....#.#.#.#......k#...#
        #.###.#####.###.#.#.#.#.#.#####.###.#.#.#.#.#.#.#.#######.#####.#.###.#.###.###.#
        #...#.#.....#...#...#.#.......#...#...#.#.#.#.#.#.#.#.....#...#.#.#...#.#.....#.#
        ###.#.#.#####D#.#####.#######.###.#.###.#.#.#.#.#.#.#.#####.###.#.#.###.#######.#
        #...#...#.....#i#.........#.#.#...#.#...#.#.#.#.#...#...#.......#...#.#.......#.#
        #.#######.#######.#######.#.#.#.#####.#.#.###.#.###.###.#.###########.#######.#.#
        #.#...#.........#...#.......#.#.......#.#.....#...#...#.#.#.......#.........#..z#
        #.#T#.###.#####.###.#########.#########.#####.###.#####.#.#####.#.###.#####.#####
        #...#...#.#...#.#...#...#...#.........#.#...#.#.#.#...#.#.....#.#...#.#.....#...#
        #######.###.#.#.#.###.#.#.#.#########.#.#.#.#.#.#.#.#.#.#####.#S###.###W###.#.#.#
        #...........#...#.....#...#..u........#.#.#....v#...#...#.......#.......#.....#.#
        #################################################################################
    """.trimIndent()

    fun part1(): Long = minPath.steps

    private val minPath: KeyPath by lazy {
        val labyrinth = input.lineSequence().mapIndexed {
            y, line -> line.mapIndexed { x, c -> Pt(x, y) to c }
        }.flatten().toMap()
        val interesting = labyrinth.filter { it.value == '@' || it.value.isLetter() }
        val graph = mutableMapOf<Pt, MutableMap<Pt, Long>>()
        interesting.map { (pos, c) ->
            val (dist, _) = Dijkstra.build(LabyrinthGraph(labyrinth, pos), pos)
            interesting.filter { it.value != c }.forEach { (subPos, _) ->
                val subDist = dist[subPos]
                if (subDist != null && subDist != Long.MAX_VALUE) {
                    graph.getOrPut(pos) { mutableMapOf() }[subPos] = subDist
                }
            }
        }

        var paths = setOf(KeyPath(listOf('@'), 0L))
        while (paths.map { it.keys.size }.max()!! < 27) {
            paths = forward(paths, interesting, graph)
        }
        paths.minBy { it.steps }!!
    }

    private fun forward(paths: Set<KeyPath>, interesting: Map<Pt, Char>, graph: Map<Pt, Map<Pt, Long>>): Set<KeyPath> {
        return paths.flatMap { path ->
            val nextKeys = mutableMapOf<Char, Long>()
            getNextKeys(path.keys.last(), path, interesting, graph, nextKeys)
            nextKeys.filterNot { it.key.isUpperCase() }.map { (key, dist) ->
                KeyPath(path.keys + key, path.steps + dist)
            }
        }.fold(mutableMapOf<KeyPath, Long>()) { acc, path ->
            val existingSteps = acc[path]
            if (existingSteps == null || path.steps < existingSteps) {
                acc.remove(path)
                acc[path] = path.steps
            }
            acc
        }.keys
    }

    private fun getNextKeys(from: Char, path: KeyPath, interesting: Map<Pt, Char>, graph: Map<Pt, Map<Pt, Long>>,
                            keys: MutableMap<Char, Long>, distSoFar: Long = 0) {

        val fromPos = interesting.asSequence().filter { it.value == from }.single().key
        graph[fromPos]?.forEach { (pos, dist) ->
            val feature = interesting[pos] ?: error("Unknown pos")
            if (feature.isLetter() && !path.keysSet.contains(feature)) {
                val existingDist = keys[feature]
                if (existingDist == null || dist + distSoFar < existingDist) {
                    keys.remove(feature)
                    keys[feature] = dist + distSoFar
                    if (feature.isUpperCase() && path.keysSet.contains(feature.toLowerCase())) {
                        getNextKeys(feature, path, interesting, graph, keys, dist + distSoFar)
                    }
                }
            }
        }
    }

    private class LabyrinthGraph(private val labyrinth: Map<Pt, Char>, private val start: Pt) : Graph<Pt> {
        companion object {
            private val directions = listOf(Pt(1, 0), Pt(-1, 0), Pt(0, 1), Pt(0, -1))
        }

        private val passable = labyrinth.asSequence().filter {
            it.value == '@' || it.value == '.' || it.value.isLetter()
        }.map { it.key }.toList()

        override fun allPassable(): List<Pt> = passable

        override fun neighbours(node: Pt): List<Pt> = directions.map { it + node }.filter {
            val srcType = labyrinth[node] ?: error("Source node must be known")
            val destType = labyrinth[it]
            (!srcType.isUpperCase() || node == start) && destType != null && destType != '#'
        }

        override fun dist(a: Pt, b: Pt): Long = 1L
    }

    private class KeyPath(val keys: List<Char>, val steps: Long) {
        val keysSet = keys.toSet()

        override fun equals(other: Any?): Boolean
                = other is KeyPath && other.keysSet == keysSet && other.keys.last() == keys.last()

        override fun hashCode(): Int {
            var result = keysSet.hashCode()
            result = 31 * result + keys.last().hashCode()
            return result
        }

        override fun toString(): String = "$steps steps: ${keys.joinToString()}"
    }
}
