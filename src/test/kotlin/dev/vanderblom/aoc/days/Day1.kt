package dev.vanderblom.aoc.days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import kotlin.math.abs

class Day1: AbstractDay() {

    @Test
    @Order(1)
    fun `part one - example`() {
        assertThat(partOne(exampleInput))
            .isEqualTo(11)
    }

    @Test
    @Order(2)
    fun `part one - actual`() {
        assertThat(partOne(actualInput))
            .isEqualTo(1651298)
    }

    @Test
    @Order(3)
    fun `part two - example`() {
        assertThat(partTwo(exampleInput))
            .isEqualTo(31)
    }

    @Test
    @Order(4)
    fun `part two - actual`() {
        assertThat(partTwo(actualInput))
            .isEqualTo(21306195)
    }

    fun partOne(input: List<String>): Int {
        val parsedInput = parse(input)

        val list1 = parsedInput.map { (l,_) -> l }.sorted()
        val list2 = parsedInput.map { (_, r) -> r }.sorted()

        return list1.zip(list2)
            .sumOf { (l, r) -> abs(l - r) }
    }

    fun partTwo(input: List<String>): Int {
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