package org.clechasseur.adventofcode2019

object IntcodeComputer {
    fun run(state: MutableList<Int>) {
        var ip = 0
        while (state[ip] != 99) {
            val (opcode, param1, param2, param3) = state.subList(ip, ip + 4)
            val in1 = state[param1]
            val in2 = state[param2]
            state[param3] = when (opcode) {
                1 -> in1 + in2
                2 -> in1 * in2
                else -> error("Wrong opcode: $opcode")
            }
            ip += 4
        }
    }
}