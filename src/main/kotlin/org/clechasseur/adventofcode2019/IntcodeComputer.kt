package org.clechasseur.adventofcode2019

class IntcodeComputer(program: List<Int>, vararg initialInput: Int) {
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

    private var state = State.RUNNING
    private val ram = program.toMutableList()
    private val input = initialInput.toMutableList()
    private val output = mutableListOf<Int>()
    private var ip = 0
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
        run()
    }

    val memory: List<Int>
        get() = ram

    val done: Boolean
        get() = state == State.DONE

    fun addInput(value: Int) {
        input.add(value)
        if (state == State.WAITING_FOR_INPUT) {
            state = State.RUNNING
            run()
        }
    }

    fun hasOutput() = output.isNotEmpty()

    fun readOutput(): Int {
        require(hasOutput()) { "No output to read" }
        return output.removeAt(0)
    }

    fun readAllOutput(): List<Int> {
        val allOutput = output.toList()
        output.clear()
        return allOutput
    }

    private fun run() {
        while (state == State.RUNNING) {
            nextOp().execute()
        }
    }

    private fun nextOp(): Op {
        requireInBounds()
        var opVal = ram[ip++]
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
        val param = ram[ip++]
        return when (val mode = nextMode()) {
            positionMode -> ram[param]
            immediateMode -> param
            else -> error("Wrong parameter mode: $mode")
        }
    }

    private fun save(value: Int) {
        requireInBounds()
        val addr = ram[ip++]
        require(nextMode() == positionMode) { "Saving parameters must be in position mode" }
        ram[addr] = value
    }

    private fun nextMode(): Int = when (modes.isEmpty()) {
        true -> 0
        false -> modes.removeAt(modes.size - 1)
    }

    private fun requireInBounds() {
        require(ip in ram.indices) { "Out of bounds" }
    }

    private enum class State {
        RUNNING, WAITING_FOR_INPUT, DONE
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
            if (input.isNotEmpty()) {
                save(input.removeAt(0))
            } else {
                ip--
                state = State.WAITING_FOR_INPUT
            }
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
            state = State.DONE
        }
    }
}