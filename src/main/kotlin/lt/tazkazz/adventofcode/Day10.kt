package lt.tazkazz.adventofcode

import java.io.File

private fun main() {
    val entries = File("./inputs/day10.txt").readLines().map(String::toInt).sorted()

    run {
        val differences = mutableListOf<Int>()

        (listOf(0) + entries + listOf(entries.last() + 3)).reduce { previous, next ->
            differences += (next - previous)
            next
        }

        val differencesBySize = differences.groupBy { it }.mapValues { it.value.size }

        println("Jolt differences: $differencesBySize")

        println("1 ΔD * 3 ΔD = ${differencesBySize[1]!! * differencesBySize[3]!!}")
    }

    run {
        val groups = mutableListOf<Group>()
        var lower = 0
        var previous = 0
        var adapters = mutableListOf<Int>()
        entries.forEach { entry ->
            if (entry - previous == 3) {
                if (adapters.size > 1) {
                    groups += Group(lower, previous, adapters.dropLast(1))
                }
                lower = entry
                adapters = mutableListOf()
            } else {
                adapters.add(entry)
            }

            previous = entry
        }

        if (adapters.size > 1) {
            groups += Group(lower, previous, adapters.dropLast(1))
        }

        val combinations = groups.map(Group::calculateCombinations).map(Int::toBigDecimal).reduce { acc, value -> acc * value }

        println("Total combinations: $combinations")
    }
}

private data class Group(
    val lower: Int,
    val upper: Int,
    val adapters: List<Int>
) {
    fun calculateCombinations(low: Int = lower, nextAdapters: List<Int> = adapters): Int {
        val adjacentAdapters = nextAdapters.takeWhile { it - low <= 3 }
        if (adjacentAdapters.isEmpty()) {
            return if (upper - low <= 3) 1 else 0
        }
        return adjacentAdapters.mapIndexed { index, adjacentLow ->
            calculateCombinations(adjacentLow, nextAdapters.drop(index + 1))
        }.sum() + if (upper - low <= 3) 1 else 0
    }
}