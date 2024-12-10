package dev.vanderblom.aoc.days

import dev.vanderblom.aoc.AbstractDay
import dev.vanderblom.aoc.Direction
import dev.vanderblom.aoc.Grid
import dev.vanderblom.aoc.Pawn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

private const val GUARD_START_CHAR = '^'

class Day6 : AbstractDay() {

    @Test
    @Order(1)
    fun `part one - example`() {
        assertThat(partOne(exampleInput))
            .isEqualTo(41)
    }

    @Test
    @Order(2)
    fun `part one - actual`() {
        assertThat(partOne(actualInput))
            .isEqualTo(4967)
    }

    @Test
    @Order(3)
    fun `part two - example`() {
        assertThat(partTwo(exampleInput))
            .isEqualTo(6)
    }

    @Test
    @Order(4)
    fun `part two - actual`() {
        assertThat(partTwo(actualInput))
            .isEqualTo(1789)
    }

    private fun partOne(input: List<String>): Int {
        val grid = Grid(input)

        val guard = Pawn.of(grid.findSingle(GUARD_START_CHAR))
        guard.canWalkOffGrid(grid)

        return guard.path.distinct().size
    }

    private fun partTwo(input: List<String>): Int {
        val grid = Grid(input)
        val guardStartPos = grid.findSingle(GUARD_START_CHAR)

        val guard = Pawn.of(guardStartPos)
        guard.canWalkOffGrid(grid)

        val obstructionCoords = guard.path
            .filter { it != guardStartPos }
            .distinct()

        return obstructionCoords
            .count { obstructionCoord ->
                val gridWithObstruction = grid.withCharAtReplacedBy(obstructionCoord, 'O')
                val newPawn = Pawn.of(guardStartPos)
                !newPawn.canWalkOffGrid(gridWithObstruction)
            }
    }

    private fun Pawn.canWalkOffGrid(grid: Grid): Boolean {
        while (true) {
            when (grid[look(Direction.FORWARD)]) {
                in listOf('#', 'O') -> turn(Direction.RIGHT)
                in listOf('.', '^') -> move(Direction.FORWARD)
                null -> return true
            }

            // If we travelled as many locations as the grid contains, there must be a loop
            if (path.size >= grid.size) {
                return false
            }
        }
    }
}
