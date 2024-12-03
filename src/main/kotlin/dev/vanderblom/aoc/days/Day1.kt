package dev.vanderblom.aoc.days

import dev.vanderblom.aoc.AocDay
import kotlin.math.abs

class Day1 : AocDay(11, 1651298, 31, 21306195) {
    override fun partOne(input: List<String>): Int {
        val parsedInput = parse(input)

        val list1 = parsedInput.map { (l,_) -> l }.sorted()
        val list2 = parsedInput.map { (_, r) -> r }.sorted()

        return list1.zip(list2)
            .sumOf { (l, r) -> abs(l - r) }
    }

    override fun partTwo(input: List<String>): Int {
        val parsedInput = parse(input)

        val list1 = parsedInput.map { (l,_) -> l }
        val mapEachCount = parsedInput.map { (_, r) -> r }.groupingBy { it }.eachCount()

        return list1
            .sumOf { it * (mapEachCount[it] ?: 0) }
    }

    private fun parse(input: List<String>) = input
        .map {
            val split = it.split("   ")
            split[0].toInt() to split[1].toInt()
        }
}

fun main() {
    val day = Day1()
    day.runPartOne()
    day.runPartTwo()
}