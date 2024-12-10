package dev.vanderblom.aoc.days

import dev.vanderblom.aoc.AbstractDay
import dev.vanderblom.aoc.Grid
import dev.vanderblom.aoc.Orientation
import dev.vanderblom.aoc.Pawn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day10 : AbstractDay() {

    @Test
    @Order(1)
    fun `part one - example`() {
        assertThat(partOne(exampleInput))
            .isEqualTo(36)
    }

    @Test
    @Order(2)
    fun `part one - actual`() {
        assertThat(partOne(actualInput))
            .isEqualTo(552)
    }

    @Test
    @Order(3)
    fun `part two - example`() {
        assertThat(partTwo(exampleInput))
            .isEqualTo(81)
    }

    @Test
    @Order(4)
    fun `part two - actual`() {
        assertThat(partTwo(actualInput))
            .isEqualTo(1225)
    }

    private fun partOne(input: List<String>): Int {
        val grid = Grid(input)

        val trailHeadScores = grid.findAll('0').map {start ->
            val acc = mutableListOf<Pawn>()
            val pawn = Pawn.of(start)
            walk(pawn, grid, acc)
            println("$start, ${acc.size}")
            acc.distinctBy { it.path.last() }.count()
        }

        return trailHeadScores
            .sum()
    }

    private fun walk(pawn: Pawn, grid: Grid, acc: MutableList<Pawn>) {
        if(grid[pawn.position] == '9') {
            acc.add(pawn)
            return
        }

        val surrounding = grid.getSurrounding(pawn.position,1, false)
        val nextValue = surrounding.char.toString().toLong()+1
        if(nextValue == (surrounding.top?.toLong() ?: 0)) {
            walk(pawn.copy().move(Orientation.NORTH), grid, acc)
        }
        if(nextValue == (surrounding.right?.toLong() ?: 0)) {
            walk(pawn.copy().move(Orientation.EAST), grid, acc)
        }
        if(nextValue == (surrounding.bottom?.toLong() ?: 0)) {
            walk(pawn.copy().move(Orientation.SOUTH), grid, acc)
        }
        if(nextValue == (surrounding.left?.toLong() ?: 0)) {
            walk(pawn.copy().move(Orientation.WEST), grid, acc)
        }

    }

    private fun partTwo(input: List<String>): Int {
        val grid = Grid(input)

        val trailHeadScores = grid.findAll('0').map {start ->
            val acc = mutableListOf<Pawn>()
            val pawn = Pawn.of(start)
            walk(pawn, grid, acc)
            println("$start, ${acc.size}")
            acc.distinct().count()
        }

        return trailHeadScores
            .sum()
    }
}