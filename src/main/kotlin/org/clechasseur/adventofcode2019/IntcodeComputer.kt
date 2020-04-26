package org.clechasseur.adventofcode2019

class IntcodeComputer(initialState: List<Int>, vararg val inputValues: Int) {
    companion object {
        private const val addOp = 1
        private const val timesOp = 2
        private const val saveOp = 3
        private const val outOp = 4
        private const val jumpIfTrueOp = 5
        private const val jumpIfFalseOp = 6
        private const val lessThanOp = 7
        private const val equalsOp = 8
        private const val endOp = 99

        private const val positionMode = 0
        private const val immediateMode = 1
    }

    private val state = initialState.toMutableList()
    private val input = List(inputValues.size) { i -> inputValues[i] }.reversed().toMutableList()
    private val output = mutableListOf<Int>()
    private var ip = 0
    private var done = false
    private var op: Op? = null
    private var modes = mutableListOf<Int>()
    private val ops = mapOf(
            addOp to Add(),
            timesOp to Times(),
            saveOp to Save(),
            outOp to Out(),
            jumpIfTrueOp to JumpIfTrue(),
            jumpIfFalseOp to JumpIfFalse(),
            lessThanOp to LessThan(),
            equalsOp to Equals(),
            endOp to End()
    )

    init {
        while (!done) {
            nextOp().execute()
        }
    }

    val finalState: List<Int>
        get() = state

    val finalOutput: List<Int>
        get() = output

    private fun nextOp(): Op {
        requireInBounds()
        var opVal = state[ip++]
        val opcode = opVal % 100
        op = ops[opcode]
        opVal /= 100
        modes.clear()
        while (opVal != 0) {
            modes.add(opVal % 10)
            opVal /= 10
        }
        modes.reverse()
        return op ?: error("Wrong opcode: $opcode")
    }

    private fun nextParam(): Int {
        requireInBounds()
        val param = state[ip++]
        return when (val mode = nextMode()) {
            positionMode -> state[param]
            immediateMode -> param
            else -> error("Wrong parameter mode: $mode")
        }
    }

    private fun nextInput(): Int {
        require(input.isNotEmpty()) { "No more input" }
        return input.removeAt(input.size - 1)
    }

    private fun save(value: Int) {
        requireInBounds()
        val addr = state[ip++]
        require(nextMode() == positionMode) { "Saving parameters must be in position mode" }
        state[addr] = value
    }

    private fun nextMode(): Int = when (modes.isEmpty()) {
        true -> 0
        false -> modes.removeAt(modes.size - 1)
    }

    private fun requireInBounds() {
        require(ip in state.indices) { "Out of bounds" }
    }

    private interface Op {
        fun execute()
    }

    private inner class Add : Op {
        override fun execute() {
            save(nextParam() + nextParam())
        }
    }

    private inner class Times : Op {
        override fun execute() {
            save(nextParam() * nextParam())
        }
    }

    private inner class Save : Op {
        override fun execute() {
            save(nextInput())
        }
    }

    private inner class Out : Op {
        override fun execute() {
            output.add(nextParam())
        }
    }

    private inner class JumpIfTrue : Op {
        override fun execute() {
            val test = nextParam()
            val move = nextParam()
            if (test != 0) {
                ip = move
            }
        }
    }

    private inner class JumpIfFalse : Op {
        override fun execute() {
            val test = nextParam()
            val move = nextParam()
            if (test == 0) {
                ip = move
            }
        }
    }

    private inner class LessThan : Op {
        override fun execute() {
            val in1 = nextParam()
            val in2 = nextParam()
            save(when (in1 < in2) {
                true -> 1
                false -> 0
            })
        }
    }

    private inner class Equals : Op {
        override fun execute() {
            save(when (nextParam() == nextParam()) {
                true -> 1
                false -> 0
            })
        }
    }

    private inner class End : Op {
        override fun execute() {
            done = true
        }
    }
}