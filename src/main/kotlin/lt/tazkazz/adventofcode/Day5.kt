package lt.tazkazz.adventofcode

import java.io.File

private fun main() {
    val entries = File("./inputs/day5.txt").readLines()

    val seats = entries.map { entry ->
        val (row, column) = entry.map {
            if (it == 'F' || it == 'L') '0' else '1'
        }.joinToString(separator = "").chunked(7).map { it.toInt(2) }
        Triple(row, column, row * 8 + column)
    }

    val seatIds = seats.map { it.third }

    val minSeatId = seatIds.minOrNull()!!
    val maxSeatId = seatIds.maxOrNull()!!

    println("Min seat ID: $minSeatId")
    println("Max seat ID: $maxSeatId")

    (minSeatId..maxSeatId).forEach { seatId ->
        if (seatId !in seatIds && (seatId - 1) in seatIds && (seatId + 1) in seatIds) {
            println("Unoccupied seat ID: $seatId")
        }
    }
}