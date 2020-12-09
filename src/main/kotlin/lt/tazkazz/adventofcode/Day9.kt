package lt.tazkazz.adventofcode

import java.io.File

private fun main() {
    val entries = File("./inputs/day9.txt").readLines().map(String::toLong)

    val (value, index) = run {
        (25..entries.lastIndex).forEach { index ->
            val value = entries[index]
            var valid = false
            (index - 25 until index).forEach { index1 ->
                (index1 + 1 until index).forEach { index2 ->
                    if (entries[index1] + entries[index2] == value) {
                        valid = true
                    }
                }
            }
            if (!valid) {
                return@run value to index
            }
        }
        return@run -1L to -1
    }

    println("Invalid value: $value at index $index")

    run {
        (0 until entries.lastIndex).forEach outerLoop@{ index1 ->
            (index1 + 1..entries.lastIndex).forEach { index2 ->
                val entryRange = entries.drop(index1).take(index2 - index1 + 1)
                val sum = entryRange.sum()
                if (sum == value) {
                    val min = entryRange.minOrNull()!!
                    val max = entryRange.maxOrNull()!!
                    println("Correct sum range: [$index1, $index2], min: $min, max: $max, their sum: ${min + max}")
                    return@run
                }
                if (sum > value) {
                    return@outerLoop
                }
            }
        }
    }
}