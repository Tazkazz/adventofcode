package lt.tazkazz.adventofcode

import java.io.File

private val linePattern = "(\\w+ \\w+) bags contain (.+)".toRegex()
private val bagPattern = "(\\d+) (\\w+ \\w+) bags?".toRegex()

private const val SHINY_GOLD = "shiny gold"

private fun main() {
    val entries = File("./inputs/day7.txt").readLines().map { line ->
        val (mainColor, inside) = linePattern.find(line.dropLast(1))!!.groupValues.drop(1)
        if (inside.endsWith("no other bags")) {
            mainColor to emptyList()
        } else {
            val bagsInside = inside.split(", ").map { bag ->
                val (count, innerColor) = bagPattern.find(bag)!!.groupValues.drop(1)
                count.toInt() to innerColor
            }.toMutableList()
            mainColor to bagsInside
        }
    }.toMap().toMutableMap()

    val bagsToCheck = mutableSetOf<String>()
    val approvedOutsideBags = mutableSetOf<String>()

    entries.forEach { (mainColor, bags) ->
        if (bags.any { it.second == SHINY_GOLD }) bagsToCheck += mainColor
    }

    while (bagsToCheck.isNotEmpty()) {
        val bagToCheck = bagsToCheck.first()
        if (bagToCheck in approvedOutsideBags) {
            bagsToCheck.remove(bagToCheck)
            continue
        }

        approvedOutsideBags += bagToCheck

        entries.forEach { (mainColor, bags) ->
            if (bags.any { it.second == bagToCheck }) bagsToCheck += mainColor
        }

        bagsToCheck.remove(bagToCheck)
    }

    println("Bags that contain '$SHINY_GOLD': ${approvedOutsideBags.size}")

    fun getInsideBagCountByColor(color: String): Int =
        entries[color]!!.map { it.first * (getInsideBagCountByColor(it.second) + 1) }.sum()

    val bagCountInsideShinyGold = getInsideBagCountByColor(SHINY_GOLD)

    println("Bags inside '$SHINY_GOLD': $bagCountInsideShinyGold")
}