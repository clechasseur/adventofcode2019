package org.clechasseur.adventofcode2019.dij

object Dijkstra {
    data class Output<T : Comparable<T>>(val dist: Map<T, Long>, val prev: Map<T, T>)

    fun <T : Comparable<T>> build(graph: Graph<T>, start: T): Output<T> {
        // https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm

        val q = mutableSetOf<T>()
        val dist = mutableMapOf<T, Long>()
        val prev = mutableMapOf<T, T>()

        (graph.allPassable() + start).forEach { node ->
            dist[node] = Long.MAX_VALUE
            q.add(node)
        }
        dist[start] = 0L

        while (q.isNotEmpty()) {
            val u = q.asSequence().filter { dist[it]!! != Long.MAX_VALUE }.minBy { dist[it]!! } ?: break
            q.remove(u)
            graph.neighbours(u).forEach { v ->
                val alt = dist[u]!! + graph.dist(u, v)
                if (alt < dist[v]!! || (prev.containsKey(v) && alt == dist[v]!! && graph.isABetter(u, prev[v]!!))) {
                    dist[v] = alt
                    prev[v] = u
                }
            }
        }

        return Output(dist, prev)
    }

    fun <T : Comparable<T>> assemblePath(prev: Map<T, T>, start: T, end: T): List<T> {
        val path = mutableListOf<T>()
        var n = end
        while (n != start) {
            path.add(n)
            n = prev[n] ?: error("Impossible to find path between $start and $end")
        }
        return path.reversed()
    }
}
