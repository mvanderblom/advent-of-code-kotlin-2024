package dev.vanderblom.aoc.days

import dev.vanderblom.aoc.AbstractDay
import dev.vanderblom.aoc.Direction
import dev.vanderblom.aoc.Grid
import dev.vanderblom.aoc.Pawn
import dev.vanderblom.aoc.showMe
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
        val guard = Pawn(grid.findSingle(GUARD_START_CHAR))

        guard.canWalkOffGrid(grid)

        return guard.path.distinct().size
    }

    private fun partTwo(input: List<String>): Int {
        val grid = Grid(input)
        val guardStartPos = grid.findSingle(GUARD_START_CHAR)
        val guard = Pawn(guardStartPos)

        guard.canWalkOffGrid(grid)

        val obstructionCoords = guard.path
            .toMutableSet()
            .apply { remove(guardStartPos)}

        return obstructionCoords
            .filter { obstructionCoord ->
                val gridWithObstruction = grid.withCharAtReplacedBy(obstructionCoord, 'O')
                val newPawn = Pawn(guardStartPos)
                !newPawn.canWalkOffGrid(gridWithObstruction)
            }
            .onEach { print("${it.row}.${it.col},")}
            .count()
            .showMe("Possibilities that lead to a loop")
    }

    private fun Pawn.canWalkOffGrid(grid: Grid): Boolean {
        while (true) {
            val charInFront = grid[look(Direction.FORWARD)]
            when (charInFront) {
                in listOf('#', 'O') -> turn(Direction.RIGHT)
                in listOf('.', '^') -> move(Direction.FORWARD)
                null -> return true
            }

            if(path.size >= grid.size) {
                return false
            }
        }
    }
}
