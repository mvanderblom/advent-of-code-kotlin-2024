package dev.vanderblom.aoc.days

import dev.vanderblom.aoc.AbstractDay
import dev.vanderblom.aoc.splitBy
import dev.vanderblom.aoc.withoutElementAt
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import kotlin.math.abs

class Day2: AbstractDay() {

    @Test
    @Order(1)
    fun `part one - example`() {
        assertThat(partOne(exampleInput))
            .isEqualTo(2)
    }

    @Test
    @Order(2)
    fun `part one - actual`() {
        assertThat(partOne(actualInput))
            .isEqualTo(663)
    }

    @Test
    @Order(3)
    fun `part two - example`() {
        assertThat(partTwo(exampleInput))
            .isEqualTo(4)
    }

    @Test
    @Order(4)
    fun `part two - actual`() {
        assertThat(partTwo(actualInput))
            .isEqualTo(692)
    }

    fun partOne(input: List<String>): Int {
        return input
            .splitBy(" ")
            .count { report -> report.isSafe() }
    }

    fun partTwo(input: List<String>): Int {
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