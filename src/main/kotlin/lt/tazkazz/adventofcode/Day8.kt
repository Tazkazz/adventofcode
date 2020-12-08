package lt.tazkazz.adventofcode

import java.io.File

private fun main() {
    val entries = File("./inputs/day8.txt").readLines().map {
        val (instruction, argumentString) = it.split(" ")
        instruction to argumentString.toInt()
    }

    val beforeInfiniteLoop = executeCode(entries)
    println("Accumulator value before infinite loop: ${beforeInfiniteLoop.first}")

    entries.mapIndexedNotNull { index, (instruction, _) ->
        if (instruction == "nop" || instruction == "jmp") index else null
    }.forEach {
        val result = executeCode(entries, it)
        if (result.second) {
            println("Execution halted with switched instruction at index $it, accumulator value: ${result.first}")
        }
    }
}

/**
 * Pair<Int, Boolean> - Accumulator value to a flag whether the code halted
 */
private fun executeCode(entries: List<Pair<String, Int>>, switchIndex: Int? = null): Pair<Int, Boolean> {
    val visitedIndices = mutableSetOf<Int>()
    var accumulator = 0
    var index = 0

    while (index !in visitedIndices && index < entries.size) {
        visitedIndices += index
        var (instruction, argument) = entries[index]

        switchIndex?.takeIf { it == index }?.let {
            instruction = when (instruction) {
                "nop" -> "jmp"
                "jmp" -> "nop"
                else -> instruction
            }
        }

        when (instruction) {
            "nop" -> index++
            "jmp" -> index += argument
            "acc" -> {
                accumulator += argument
                index++
            }
        }
    }

    return accumulator to (index !in visitedIndices)
}