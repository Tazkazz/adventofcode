package lt.tazkazz.adventofcode

import java.io.File

private fun main() {
    val entries = File("./inputs/day3.txt").readLines()

    listOf(
        1 to 1,
        3 to 1,
        5 to 1,
        7 to 1,
        1 to 2
    ).map { (right, down) ->
        calculateTreesBySlope(entries, right, down).toBigDecimal().also {
            println("Trees for right $right, down $down: $it")
        }
    }.reduce { a, b -> a * b }
        .also { println("Multiplied: $it") }
}

private fun calculateTreesBySlope(entries: List<String>, right: Int, down: Int): Int {
    val width = entries.first().length

    var currentX = 0
    var skipDown = down - 1

    val objects = entries.drop(1).mapIndexedNotNull { index, line ->
        if (skipDown > 0) {
            skipDown--
            return@mapIndexedNotNull null
        }
        skipDown = down - 1
        currentX = (currentX + right) % width
        line[currentX]
    }

    return objects.filter { it == '#' }.size
}