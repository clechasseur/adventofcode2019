import org.clechasseur.adventofcode2019.Day23Data
import org.clechasseur.adventofcode2019.IntcodeComputer

object Day23 {
    private val input = Day23Data.input

    fun part1(): Long {
        val computers = List(50) { idx -> IntcodeComputer(input, idx.toLong()) }
        val queues = List(50) { mutableListOf<Packet>() }
        var destPacket: Packet? = null
        while (destPacket == null) {
            computers.forEachIndexed { idx, computer ->
                val output = computer.readAllOutput()
                require(output.size % 3 == 0) { "Output size of computer $idx is not divisible by 3" }
                output.chunked(3) { Packet(it[0].toInt(), it[1], it[2]) }.forEach { packet -> when {
                    packet.target == 255 -> {
                        if (destPacket == null) {
                            destPacket = packet
                        }
                    }
                    packet.target in queues.indices -> queues[packet.target].add(packet)
                    else -> error("Packet destination ${packet.target} unknown")
                } }
            }
            queues.forEachIndexed { idx, queue ->
                if (queue.isEmpty()) {
                    computers[idx].addInput(-1L)
                } else {
                    queue.forEach { packet ->
                        computers[idx].addInput(packet.x, packet.y)
                    }
                    queue.clear()
                }
            }
        }
        return destPacket!!.y
    }

    private data class Packet(val target: Int, val x: Long, val y: Long)
}
