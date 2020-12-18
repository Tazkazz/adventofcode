package lt.tazkazz.adventofcode

import java.io.File

private typealias CharMultiArray = Array<Array<Char>>
private typealias SeatCounter = (area: CharMultiArray, index1: Int, index2: Int, target: Char) -> Int

private const val EMPTY = 'L'
private const val OCCUPIED = '#'
private const val FLOOR = '.'

private val indexDeltasAround = listOf(
    -1 to -1,
    -1 to 0,
    -1 to 1,
    0 to 1,
    1 to 1,
    1 to 0,
    1 to -1,
    0 to -1
)

private fun main() {
    val entries = File("./inputs/day11.txt").readLines().map { line ->
        line.toCharArray().toTypedArray()
    }.toTypedArray()

    run {
        val counter = ::countImmediateAround
        val tolerance = 4
        var iterations = 0
        var previous = entries
        var current = iterate(entries, counter, tolerance)

        while (!previous.contentDeepEquals(current)) {
            iterations++
            previous = current
            current = iterate(current, counter, tolerance)
        }

        val occupiedSeats = current.map { seats -> seats.filter { it == OCCUPIED }.size }.sum()

        println("Occupied seats after $iterations iterations by counting immediate seats around: $occupiedSeats")
    }

    run {
        val counter = ::countVisibleAround
        val tolerance = 5
        var iterations = 0
        var previous = entries
        var current = iterate(entries, counter, tolerance)

        while (!previous.contentDeepEquals(current)) {
            iterations++
            previous = current
            current = iterate(current, counter, tolerance)
        }

        val occupiedSeats = current.map { seats -> seats.filter { it == OCCUPIED }.size }.sum()

        println("Occupied seats after $iterations iterations by counting visible seats around: $occupiedSeats")
    }
}

private fun iterate(area: CharMultiArray, counter: SeatCounter, tolerance: Int): CharMultiArray {
    return area.mapIndexed { index1, seats ->
        seats.mapIndexed { index2, seat ->
            when {
                seat == EMPTY && counter(area, index1, index2, OCCUPIED) == 0 -> OCCUPIED
                seat == OCCUPIED && counter(area, index1, index2, OCCUPIED) >= tolerance -> EMPTY
                else -> seat
            }
        }.toTypedArray()
    }.toTypedArray()
}

private fun countImmediateAround(area: CharMultiArray, index1: Int, index2: Int, target: Char): Int {
    val maxIndex1 = area.lastIndex
    val maxIndex2 = area.first().lastIndex

    return indexDeltasAround
        .map { (delta1, delta2) -> index1 + delta1 to index2 + delta2 }
        .filter { (i1, i2) -> i1 in 0..maxIndex1 && i2 in 0..maxIndex2 && area[i1][i2] == target }
        .size
}

private fun countVisibleAround(area: CharMultiArray, index1: Int, index2: Int, target: Char): Int {
    val maxIndex1 = area.lastIndex
    val maxIndex2 = area.first().lastIndex

    return indexDeltasAround
        .mapNotNull { (delta1, delta2) ->
            var i1 = index1 + delta1
            var i2 = index2 + delta2

            while (i1 in 0..maxIndex1 && i2 in 0..maxIndex2 && area[i1][i2] == FLOOR) {
                i1 += delta1
                i2 += delta2
            }

            if (i1 in 0..maxIndex1 && i2 in 0..maxIndex2) area[i1][i2] else null
        }
        .filter { it == target }
        .size
}