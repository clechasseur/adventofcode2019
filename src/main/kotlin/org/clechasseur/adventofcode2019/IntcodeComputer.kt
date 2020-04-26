package org.clechasseur.adventofcode2019

class IntcodeComputer(initialState: List<Int>) {
    private val _state = initialState.toMutableList()

    val state: List<Int>
        get() = _state

    fun run(): List<Int> {
        var ip = 0
        while (_state[ip] != 99) {
            val (opcode, param1, param2, param3) = _state.subList(ip, ip + 4)
            val in1 = _state[param1]
            val in2 = _state[param2]
            _state[param3] = when (opcode) {
                1 -> in1 + in2
                2 -> in1 * in2
                else -> error("Wrong opcode: $opcode")
            }
            ip += 4
        }
        return state
    }
}