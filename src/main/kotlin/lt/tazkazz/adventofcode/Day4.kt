package lt.tazkazz.adventofcode

import java.io.File

private val requiredFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
private val eyeColors = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")

private fun main() {
    val entries = File("./inputs/day4.txt").readText().split("\n\n").map { line ->
        line.split("\\s+".toRegex()).map {
            val (key, value) = it.split(":")
            key to value
        }.toMap()
    }

    val entriesWithAllRequiredFields = entries.filter { entry ->
        entry.keys.containsAll(requiredFields)
    }

    val validEntries = entriesWithAllRequiredFields.filter { entry ->
        try {
            validateEntry(entry)
        } catch (e: Exception) {
            false
        }
    }

    println("Entries with all required fields: ${entriesWithAllRequiredFields.size}")
    println("Valid entries: ${validEntries.size}")
}

private fun validateEntry(entry: Map<String, String>): Boolean {
    if (!validateYear(entry["byr"], 1920, 2002)) return false
    if (!validateYear(entry["iyr"], 2010, 2020)) return false
    if (!validateYear(entry["eyr"], 2020, 2030)) return false

    val hgt = entry["hgt"] ?: return false
    val hgtValue = hgt.substring(0, hgt.length - 2).toInt()
    when {
        hgt.endsWith("in") -> if (hgtValue !in 59..76) return false
        hgt.endsWith("cm") -> if (hgtValue !in 150..193) return false
        else -> return false
    }

    val hcl = entry["hcl"] ?: return false
    if (hcl.first() != '#' || hcl.drop(1).any { !it.isDigit() && it !in 'a'..'f' }) return false

    if (!eyeColors.contains(entry["ecl"] ?: "")) return false

    val pid = entry["pid"] ?: return false
    if (pid.length != 9 || pid.any { !it.isDigit() }) return false

    return true
}

private fun validateYear(value: String?, min: Int, max: Int): Boolean {
    val year = value?.toInt() ?: return false
    return year in min..max
}