package lt.tazkazz.adventofcode

import java.io.File

private fun main() {
    val entries = File("./inputs/day6.txt").readText().split("\n\n")
        .map { group -> group.split("\n").map { it.toSet() } }

    val totalAnyYesForAllGroups = entries.map { group ->
        group.flatten().toSet().size
    }.sum()

    println("Total any Yes for all groups: $totalAnyYesForAllGroups")

    val totalAllYesForAllGroups = entries.map { group ->
        group.flatten().toSet().filter { question ->
            group.all { it.contains(question) }
        }.size
    }.sum()

    println("Total all Yes for all groups: $totalAllYesForAllGroups")
}