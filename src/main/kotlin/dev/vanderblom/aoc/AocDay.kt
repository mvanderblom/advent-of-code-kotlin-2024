package dev.vanderblom.aoc.dev.vanderblom.aoc

import dev.vanderblom.aoc.dev.vanderblom.aoc.util.isEqualTo
import dev.vanderblom.aoc.dev.vanderblom.aoc.util.readInput
import dev.vanderblom.aoc.dev.vanderblom.aoc.util.toDayName

abstract class AocDay(
    private val p1Example: Int? = null,
    private val p1Actual: Int? = null,
    private val p2Example: Int? = null,
    private val p2Actual: Int? = null,
) {
    private val exampleInput: List<String>
    private val input: List<String>

    init {
        val simpleName = this::class.java.simpleName
        val dayName = simpleName.replace("Day", "").toInt().toDayName()
        exampleInput = readInput("${dayName}-1-example")
        input = readInput("${dayName}-2-actual")
        println("Running $simpleName")
    }

    fun runPartOne() {
        val exampleOutput = partOne(exampleInput)
        print("Part one example: ")
        exampleOutput isEqualTo p1Example

        print("Part one actual: ")
        val output = partOne(input)
        output isEqualTo p1Actual
    }

    fun runPartTwo() {
        val exampleOutput = partTwo(exampleInput)
        print("Part two example: ")
        exampleOutput isEqualTo p2Example

        val output = partTwo(input)
        print("Part two actual: ")
        output isEqualTo p2Actual
    }


    abstract fun partOne(exampleInput: List<String>): Int
    abstract fun partTwo(exampleInput: List<String>): Int
}