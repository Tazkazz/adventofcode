package lt.tazkazz.adventofcode

import java.io.File

private fun main() {
    val entries = File("./inputs/day2.txt").readLines()
    val pattern = "(\\d+)-(\\d+) (\\w): (\\w+)".toRegex()

    val validByCount = entries.filter {
        val (minString, maxString, letterString, password) = pattern.find(it)!!.groupValues.drop(1)
        val min = minString.toInt()
        val max = maxString.toInt()
        val letter = letterString.first()
        password.count { c -> c == letter } in min..max
    }.size

    val validByPosition = entries.filter {
        val (pos1String, pos2String, letterString, password) = pattern.find(it)!!.groupValues.drop(1)
        val pos1 = pos1String.toInt() - 1
        val pos2 = pos2String.toInt() - 1
        val letter = letterString.first()

        val isAtPos1 = password[pos1] == letter
        val isAtPos2 = password[pos2] == letter

        (isAtPos1 || isAtPos2) && !(isAtPos1 && isAtPos2)
    }.size

    println("Valid by count: $validByCount")
    println("Valid by position: $validByPosition")
}