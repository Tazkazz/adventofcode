package lt.tazkazz.adventofcode

import java.io.File
import kotlin.math.absoluteValue

private val directionDeltas = listOf(
    // N+S- to E+W-, clockwise
    0 to 1, // East
    -1 to 0, // South
    0 to -1, // West
    1 to 0 // North
)

private fun main() {
    val entries = File("./inputs/day12.txt").readLines().map { line ->
        line.first() to line.substring(1).toInt()
    }

    run {
        var ns = 0
        var ew = 0
        var direction = 0
        entries.forEach { (action, value) ->
            when (action) {
                'N' -> ns += value
                'E' -> ew += value
                'S' -> ns -= value
                'W' -> ew -= value
                'L' -> direction = (direction + 4 - value / 90) % 4
                'R' -> direction = (direction + value / 90) % 4
                'F' -> {
                    val (nsDelta, ewDelta) = directionDeltas[direction]
                    ns += value * nsDelta
                    ew += value * ewDelta
                }
            }
        }

        println("With ship directions:")
        println("N+S-: $ns, E+W-: $ew, Manhattan distance: ${ns.absoluteValue + ew.absoluteValue}")
    }

    run {
        var ns = 0
        var ew = 0
        var wpNs = 1
        var wpEw = 10
        entries.forEach { (action, value) ->
            when (action) {
                'N' -> wpNs += value
                'E' -> wpEw += value
                'S' -> wpNs -= value
                'W' -> wpEw -= value
                'L' -> repeat(value / 90) {
                    val negativeWpNs = -wpNs
                    wpNs = wpEw
                    wpEw = negativeWpNs
                }
                'R' -> repeat(value / 90) {
                    val negativeWpEw = -wpEw
                    wpEw = wpNs
                    wpNs = negativeWpEw
                }
                'F' -> repeat(value) {
                    ns += wpNs
                    ew += wpEw
                }
            }
        }

        println("With waypoint:")
        println("N+S-: $ns, E+W-: $ew, Manhattan distance: ${ns.absoluteValue + ew.absoluteValue}")
    }
}