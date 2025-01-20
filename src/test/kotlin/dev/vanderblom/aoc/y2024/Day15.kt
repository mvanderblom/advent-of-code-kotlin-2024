package dev.vanderblom.aoc.y2024


import dev.vanderblom.aoc.AbstractDay
import dev.vanderblom.aoc.Coord
import dev.vanderblom.aoc.DataGrid
import dev.vanderblom.aoc.readInput
import dev.vanderblom.aoc.splitOnce
import dev.vanderblom.aoc.toMove

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

private const val WALL_CHAR = '#'
private const val BOX_CHAR = 'O'
private const val EMPTY_SPACE = '.'
private const val ROBOT_CHAR = '@'


class Day15 : AbstractDay() {
    @Test
    @Order(0)
    fun `part one - small`() {
        val smallInput = readInput("Day15-0-small", 2024)
        assertThat(partOne(smallInput))
            .isEqualTo(2028)
    }

    @Test
    @Order(1)
    fun `part one - example`() {
        assertThat(partOne(exampleInput))
            .isEqualTo(10092)
    }

    @Test
    @Order(2)
    fun `part one - actual`() {
        assertThat(partOne(actualInput))
            .isEqualTo(1456590)
    }

    @Test
    @Order(3)
    fun `part two - example`() {
        assertThat(partTwo(exampleInput))
            .isEqualTo(9021)
    }

    @Test
    @Order(4)
    fun `part two - actual`() {
        assertThat(partTwo(actualInput))
            .isEqualTo(1)
    }

    @Test
    fun partTwoLeftRight() {
        partTwo(
            listOf(
                "#######",
                "#.O@O.#",
                "#######",
                "",
                "<<<>>>>>>"
            )
        )
    }

    private fun partOne(input: List<String>): Int {
        var (grid, moves) = parse(input)

        var robotPosition = grid.findSingle(ROBOT_CHAR)
        grid.showMe()
        moves.forEach {
            val move = it.toMove()

            val nextPos = move.invoke(robotPosition)
            val charAtNextPos = grid[nextPos]

            println("Attempting move $it, from $robotPosition, to $nextPos, currently in that place: $charAtNextPos")

            if (charAtNextPos == EMPTY_SPACE) {
                grid = grid
                    .withCharAtReplacedBy(nextPos, ROBOT_CHAR)
                    .withCharAtReplacedBy(robotPosition, EMPTY_SPACE)
                robotPosition = nextPos
            } else if(charAtNextPos == BOX_CHAR) {
                // box, move boxes if possible
                var boxNextPosition = move.invoke(nextPos)
                while (grid[boxNextPosition] == BOX_CHAR) {
                    boxNextPosition = move.invoke(boxNextPosition)
                }
                if(grid[boxNextPosition] == EMPTY_SPACE) {
                    grid = grid
                        .withCharAtReplacedBy(boxNextPosition, BOX_CHAR)
                        .withCharAtReplacedBy(nextPos, ROBOT_CHAR)
                        .withCharAtReplacedBy(robotPosition, EMPTY_SPACE)
                    robotPosition = nextPos

                }
            }
        }

        return grid.findAll(BOX_CHAR).sumOf { (it.row * 100) + it.col }
    }

    private fun partTwo(input: List<String>): Int {
        var (grid, moves) = parse(input)
        grid.showMe()

        val newInput = grid
            .input
            .map { row ->
                row
                    .map { char ->
                        when(char) {
                            BOX_CHAR -> listOf('[',']')
                            ROBOT_CHAR -> listOf(ROBOT_CHAR, EMPTY_SPACE)
                            else -> listOf(char, char)
                        }.joinToString("")
                    }.joinToString("")
            }
        grid = DataGrid(newInput)
        grid.showMe()

        var robotPosition = grid.findSingle(ROBOT_CHAR)
        moves.forEach {
            val move = it.toMove()

            val nextPos = move.invoke(robotPosition)
            val charAtNextPos = grid[nextPos]

            println("Attempting move $it, from $robotPosition, to $nextPos, currently in that place: $charAtNextPos")

            if (charAtNextPos == EMPTY_SPACE) {
                grid = grid
                    .withCharAtReplacedBy(nextPos, ROBOT_CHAR)
                    .withCharAtReplacedBy(robotPosition, EMPTY_SPACE)
                robotPosition = nextPos
            } else if (charAtNextPos in listOf('[', ']')) {
                if (move == Coord::left || move == Coord::right) {
                    var boxNextPosition = move.invoke(nextPos)

                    val positions = mutableListOf(nextPos, boxNextPosition)
                    while (grid[boxNextPosition] in listOf('[', ']')) {
                        boxNextPosition = move.invoke(boxNextPosition)
                        positions.add(boxNextPosition)
                    }
                    if (grid[boxNextPosition] == EMPTY_SPACE) {
                        positions.reversed()
                            .windowed(2, 1, false)
                            .forEach { (a, b) ->
                                grid = grid.withCharAtReplacedBy(a, grid[b]!!)
                            }

                        grid = grid
                            .withCharAtReplacedBy(nextPos, ROBOT_CHAR)
                            .withCharAtReplacedBy(robotPosition, EMPTY_SPACE)
                        robotPosition = nextPos
                    }
                }

            }
            grid.showMe()
        }

        return return grid.findAll('[').sumOf { (it.row * 100) + it.col }
    }

    private fun parse(input: List<String>): Pair<DataGrid, String> {
        val (gridData, moves) = input.splitOnce("")
        return DataGrid(gridData) to moves.joinToString("")
    }
}