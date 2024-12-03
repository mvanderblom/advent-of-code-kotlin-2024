package dev.vanderblom.aoc.days

import dev.vanderblom.aoc.AocDay

class Day0 : AocDay(null, null, null, null) {
    override fun partOne(input: List<String>): Int {
        return input.size
    }

    override fun partTwo(input: List<String>): Int {
        return input.size
    }
}

fun main() {
    val day = Day0()
    day.runPartOne()
    day.runPartTwo()
}

