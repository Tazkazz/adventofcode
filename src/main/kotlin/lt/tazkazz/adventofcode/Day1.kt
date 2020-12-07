package lt.tazkazz.adventofcode

import java.io.File

private fun main() {
    val numbers = File("./inputs/day1.txt").readLines().map(String::toInt)
    numbers.forEachIndexed { index, number ->
        numbers.drop(index + 1).forEachIndexed { index2, number2 ->
            if (number + number2 == 2020) {
                println("$number & $number2 : ${number * number2}")
            }
        }
    }
    numbers.forEachIndexed { index, number ->
        numbers.drop(index + 1).forEachIndexed { index2, number2 ->
            numbers.drop(index + index2 + 2).forEachIndexed { index3, number3 ->
                if (number + number2 + number3 == 2020) {
                    println("$number & $number2 & $number3 : ${number * number2 * number3}")
                }
            }
        }
    }
}