package dev.vanderblom.aoc

import kotlin.math.abs
import kotlin.math.sign

private fun List<Int>.isSafe(): Boolean = windowed(2)
    .map { (a, b) -> b - a }
    .let{ reportDeltas ->
        (reportDeltas.all { it > 0 } || reportDeltas.all { it < 0 })
                && reportDeltas.none { levelDelta -> abs(levelDelta) > 3 }
    }

private fun List<Int>.isSafePt2(): Boolean {
    if(this.isSafe()) {
        println("$this - safe without mods")
        return true
    }

    // Delta > 3 || Delta == 0
    val indexedDeltas = windowed(2)
        .mapIndexed { index, (a, b) ->  index to abs(b - a) }
    val firstInvalidDeltaIndex = indexedDeltas.firstOrNull { (_, value) -> value > 3 || value == 0 }?.let { it.first + 1 }

    if(firstInvalidDeltaIndex != null && this.withoutElementAtIndex(firstInvalidDeltaIndex).isSafe()) {
        println("$this - safe after removing invalid delta: ${this.withoutElementAtIndex(firstInvalidDeltaIndex)}")
        return true
    }

    // Delta sign shift (+1 -1)
    val indexedDeltaSigns = windowed(2)
        .mapIndexed { index, (a, b) ->  index to (b - a).sign }

    val firstSignShiftIndex = indexedDeltaSigns
        .windowed(2)
        .firstOrNull {(left, right) -> left.second != right.second}
        ?.first()?.first?.let { it + 2 }

    if(firstSignShiftIndex != null && this.withoutElementAtIndex(firstSignShiftIndex).isSafe()) {
        println("$this - safe after removing invalid sign shift: ${this.withoutElementAtIndex(firstSignShiftIndex)}")
        return true
    }

    if(firstSignShiftIndex != null && this.withoutElementAtIndex(firstSignShiftIndex-1).isSafe()) {
        println("$this - 2 safe after removing invalid sign shift: ${this.withoutElementAtIndex(firstSignShiftIndex)}")
        return true
    }
    if(firstSignShiftIndex != null && this.withoutElementAtIndex(firstSignShiftIndex-2).isSafe()) {
        println("$this - 3 safe after removing invalid sign shift: ${this.withoutElementAtIndex(firstSignShiftIndex)}")
        return true
    }

    println("$this - unsafe")
    return false
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

//    val testOutputPart1 = part1(testInput)
//    testOutputPart1 isEqualTo 2

//    val outputPart1 = part1(input)
//    outputPart1 isEqualTo 663

    // Part 2

    val testOutputPart2 = part2(testInput)
    testOutputPart2 isEqualTo 4

    val outputPart2 = part2(input)
    outputPart2 isEqualTo null // 679 too low; 681 too low; 685 too low; 686 ??;
}
