package dev.vanderblom.aoc

import kotlin.math.abs

private fun List<Int>.isSafe(): Boolean = windowed(2)
    .map { (a, b) -> b - a }
    .let{ reportDeltas ->
        (reportDeltas.all { it > 0 } || reportDeltas.all { it < 0 })
                && reportDeltas.none { levelDelta -> abs(levelDelta) > 3 }
    }

private fun List<Int>.isSafePt2(): Boolean {
    if(this.isSafe()) {
        return true
    }

    return indices
        .any { this.withoutElementAt(it).isSafe() }
}

fun main() {
    val dayName = 2.toDayName()

    fun part1(input: List<String>): Int {
        return input
            .splitBy(" ")
            .count { report -> report.isSafe() }
    }

    fun part2(input: List<String>): Int {
        return input
            .splitBy(" ")
            .count { it.isSafePt2() }
    }

    val testInput = readInput("${dayName}_test")
    val input = readInput(dayName)

    // Part 1

    val testOutputPart1 = part1(testInput)
    testOutputPart1 isEqualTo 2

    val outputPart1 = part1(input)
    outputPart1 isEqualTo 663

    // Part 2

    val testOutputPart2 = part2(testInput)
    testOutputPart2 isEqualTo 4

    val outputPart2 = part2(input)
    outputPart2 isEqualTo 692 // 679 too low; 681 too low; 685 too low; 686 ??;
}
