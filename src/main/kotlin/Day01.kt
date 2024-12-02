package dev.vanderblom.aoc

import kotlin.math.abs

fun main() {
    val dayName = 1.toDayName()

    fun part1(input: List<String>): Int {
        val parsedInput = input
            .map {
                val split = it.split("   ")
                split[0].toInt() to split[1].toInt()
            }

        val list1 = parsedInput.map { (l,_) -> l }.sorted()
        val list2 = parsedInput.map { (_, r) -> r }.sorted()

        return list1.zip(list2)
            .sumOf { (l, r) -> abs(l - r) }
    }

    fun part2(input: List<String>): Int {
        val parsedInput = input
            .map {
                val split = it.split("   ")
                split[0].toInt() to split[1].toInt()
            }

        val list1 = parsedInput.map { (l,_) -> l }
        val mapEachCount = parsedInput.map { (_, r) -> r }.groupingBy { it }.eachCount()

        return list1
            .sumOf { it * (mapEachCount[it] ?: 0) }
    }

    val testInput = readInput("${dayName}_test")
    val input = readInput(dayName)

    // Part 1

    val testOutputPart1 = part1(testInput)
    testOutputPart1 isEqualTo 11

    val outputPart1 = part1(input)
    outputPart1 isEqualTo 1651298

    // Part 2

    val testOutputPart2 = part2(testInput)
    testOutputPart2 isEqualTo 31

    val outputPart2 = part2(input)
    outputPart2 isEqualTo null
}