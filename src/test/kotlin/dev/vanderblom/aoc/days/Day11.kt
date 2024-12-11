package dev.vanderblom.aoc.days

import dev.vanderblom.aoc.AbstractDay
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test


class Day11 : AbstractDay() {

    @Test
    @Order(1)
    fun `part one - example`() {
        assertThat(partOne(exampleInput[0]))
            .isEqualTo(55312)
    }

    @Test
    @Order(2)
    fun `part one - actual`() {
        assertThat(partOne(actualInput[0]))
            .isEqualTo(186175)
    }

    @Test
    @Order(3)
    fun `part two - example`() {
        assertThat(partTwo(exampleInput[0], 25))
            .isEqualTo(55312)
    }

    @Test
    @Order(4)
    fun `part two - actual`() {
        assertThat(partTwo(actualInput[0]))
            .isEqualTo(220566831337810L)
    }

    private fun partOne(input: String, n: Int = 25) = input
        .split(" ")
        .let {
            var actualStoneList = it
            repeat(n) {
                actualStoneList = actualStoneList
                    .flatMap { stone ->
                        stone.morph()
                    }
            }
            actualStoneList
        }
        .size

    private fun partTwo(input: String, n: Int = 75): Long {
        val currentStoneCounts = input
            .split(" ")
            .groupingBy { it }
            .eachCount()
            .mapValues { (_, count) -> count.toLong() }
            .toMutableMap()

        repeat(n) {
            currentStoneCounts
                .toMap() // Creates a copy of the currentStoneCounts
                .entries
                .map { (oldStoneValue, oldStoneCount) ->
                    currentStoneCounts[oldStoneValue] = currentStoneCounts[oldStoneValue]!! - oldStoneCount
                    if (currentStoneCounts[oldStoneValue] == 0L) currentStoneCounts.remove(oldStoneValue)

                    oldStoneValue
                        .morph()
                        .forEach { newValue ->
                            currentStoneCounts.compute(newValue, { _, count -> count?.plus(oldStoneCount) ?: oldStoneCount })
                        }
                }
        }
        return currentStoneCounts.values.sum()
    }
}

fun String.morph(): List<String> {
    if (this == "0") return listOf("1")
    if (length % 2 == 0) return listOf(
        substring(0, length / 2),
        substring(length / 2, length).toLong().toString()
    )
    return listOf((toLong() * 2024L).toString())
}