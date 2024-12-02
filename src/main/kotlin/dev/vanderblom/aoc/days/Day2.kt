package dev.vanderblom.aoc.dev.vanderblom.aoc.impl

import dev.vanderblom.aoc.dev.vanderblom.aoc.AocDay
import dev.vanderblom.aoc.dev.vanderblom.aoc.util.splitBy
import dev.vanderblom.aoc.dev.vanderblom.aoc.util.withoutElementAt
import kotlin.math.abs

class Day2 : AocDay(2,663,4,692) {
    override fun partOne(input: List<String>): Int {
        return input
            .splitBy(" ")
            .count { report -> report.isSafe() }
    }

    override fun partTwo(input: List<String>): Int {
        return input
            .splitBy(" ")
            .count { it.isSafePt2() }
    }

    private fun List<Int>.isSafe(): Boolean = windowed(2)
        .map { (a, b) -> b - a }
        .let{ reportDeltas ->
            (reportDeltas.all { it > 0 } || reportDeltas.all { it < 0 })
                    && reportDeltas.none { levelDelta -> abs(levelDelta) > 3 }
        }

    private fun List<Int>.isSafePt2() = this.isSafe() || indices
        .any { this.withoutElementAt(it).isSafe() }

}

fun main() {
    val day = Day2()
    day.runPartOne()
    day.runPartTwo()
}
