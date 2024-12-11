package dev.vanderblom.aoc.y2024

import dev.vanderblom.aoc.AbstractDay
import dev.vanderblom.aoc.Grid
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day4 : AbstractDay() {

    @Test
    @Order(1)
    fun `part one - example`() {
        assertThat(partOne(exampleInput))
            .isEqualTo(18)
    }

    @Test
    @Order(2)
    fun `part one - actual`() {
        assertThat(partOne(actualInput))
            .isEqualTo(2483)
    }

    @Test
    @Order(3)
    fun `part two - example`() {
        assertThat(partTwo(exampleInput))
            .isEqualTo(9)
    }

    @Test
    @Order(4)
    fun `part two - actual`() {
        assertThat(partTwo(actualInput))
            .isEqualTo(1925)
    }

    private fun partOne(input: List<String>): Int {
        val grid = Grid(input)
        return grid.findAll('X')
            .map { grid.getSurrounding(it, 3) }
            .sumOf { it.all().count { str -> str == "XMAS" } }
    }

    private fun partTwo(input: List<String>): Int {
        val grid = Grid(input)
        return grid.findAll('A')
            .map { grid.getSurrounding(it, 1) }
            .count { it.diagonals().all { str -> str == "MAS" || str == "SAM"  } }
    }
}

