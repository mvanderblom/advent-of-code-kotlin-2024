package dev.vanderblom.aoc

fun main() {
    val dayName = 0.toDayName()

    fun part1(input: List<String>): Int = input.size

    fun part2(input: List<String>): Int = input.size

    val testInput = readInput("${dayName}_test")
    val input = readInput(dayName)

    // Part 1

    val testOutputPart1 = part1(testInput)
    testOutputPart1 isEqualTo null

    val outputPart1 = part1(input)
    outputPart1 isEqualTo null

    // Part 2

    val testOutputPart2 = part2(testInput)
    testOutputPart2 isEqualTo null

    val outputPart2 = part2(input)
    outputPart2 isEqualTo null
}