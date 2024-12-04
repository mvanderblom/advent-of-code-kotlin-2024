package dev.vanderblom.aoc.days

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day4: AbstractDay() {

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

class Grid(val input: List<String>) {
    fun findAll(needle: Char): List<Pair<Int, Int>> {
        return input.flatMapIndexed { lineIndex, line ->
            line
                .mapIndexedNotNull { charIndex, char ->
                    if(char == needle)
                        lineIndex to charIndex
                    else
                        null
                }
        }
    }

    fun getSurrounding(index: Pair<Int, Int>, n: Int): Surrounding {
        val (row, col) = index

        val canLookRight = col + n + 1 <= input[row].length
        val canLookDown = row + n + 1 <= input.size
        val canLookLeft = col - n >= 0
        val canLookUp = row - n >= 0

        val indices = (0..n)
        return Surrounding.of(
            if (canLookUp) indices.map { input[row - it][col] } else null,
            if (canLookRight) indices.map { input[row][col + it] } else null,
            if (canLookDown) indices.map { input[row + it][col] } else null,
            if (canLookLeft) indices.map { input[row][col - it] } else null,
            if (canLookUp && canLookRight) indices.map { input[row - it][col + it] } else null,
            if (canLookDown && canLookRight) indices.map { input[row + it][col + it] } else null,
            if (canLookDown && canLookLeft) indices.map { input[row + it][col - it] } else null,
            if (canLookUp && canLookLeft) indices.map { input[row - it][col - it] } else null
        )
    }
}

data class Surrounding(
    val top: String?,
    val right: String?,
    val bottom: String?,
    val left: String?,
    val topRight: String?,
    val bottomRight: String?,
    val bottomLeft: String?,
    val topLeft: String?,
) {
    fun all() = listOfNotNull(top, right, bottom, left, topRight, bottomRight, bottomLeft, topLeft)
    fun diagonals() = listOfNotNull((topLeft?.reversed() ?: "") + (bottomRight?.substring(1) ?: ""), (bottomLeft?.reversed() ?: "") + (topRight?.substring(1) ?: ""))

    companion object {
        fun of(
            top: List<Char>?,
            right: List<Char>?,
            bottom: List<Char>?,
            left: List<Char>?,
            topRight: List<Char>?,
            bottomRight: List<Char>?,
            bottomLeft: List<Char>?,
            topLeft: List<Char>?,
        ): Surrounding {
            return Surrounding(
                top?.joinToString (""),
                right?.joinToString (""),
                bottom?.joinToString (""),
                left?.joinToString (""),
                topRight?.joinToString (""),
                bottomRight?.joinToString (""),
                bottomLeft?.joinToString (""),
                topLeft?.joinToString (""),
            )
        }
    }
}