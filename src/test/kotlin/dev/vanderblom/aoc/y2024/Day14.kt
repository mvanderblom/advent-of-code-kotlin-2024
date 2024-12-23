package dev.vanderblom.aoc.y2024

import dev.vanderblom.aoc.AbstractDay
import dev.vanderblom.aoc.Coord
import dev.vanderblom.aoc.Grid
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day14 : AbstractDay() {

    @Test
    @Order(1)
    fun `part one - example`() {
        val grid = Grid(11, 7)
        val robots = exampleInput.map { Robot.parse(it) }

        assertThat(partOne(grid, robots))
            .isEqualTo(12)
    }

    @Test
    @Order(2)
    fun `part one - actual`() {
        val grid = Grid(101, 103)
        val robots = actualInput.map { Robot.parse(it) }
        assertThat(partOne(grid, robots))
            .isEqualTo(225943500) // 225125376 too low;
    }

    @Test
    @Order(3)
    fun `part two - example`() {
//        assertThat(partTwo(exampleInput))
//            .isEqualTo(1)
    }

    @Test
    @Order(4)
    fun `part two - actual`() {
        val grid = Grid(101, 103)
        val robots = actualInput.map { Robot.parse(it) }
        assertThat(partTwo(grid, robots))
            .isEqualTo(6377)
    }

    private fun partOne(grid: Grid, robots: List<Robot>): Int {
        var theRobots = robots.toList()
        repeat(100) {
            theRobots = theRobots.map {
                val nextPos = (it.position + it.velocity).withIn(grid)
                Robot(nextPos, it.velocity)
            }
        }
        return calculateSafetyScore(theRobots, grid)
    }


    private fun partTwo(grid: Grid, robots: List<Robot>): Int {
        var theRobots = robots.toList()

        return (0 until 10_000)
            .map {
                theRobots = theRobots.map {
                    val nextPos = (it.position + it.velocity).withIn(grid)
                    Robot(nextPos, it.velocity)
                }
                val safetyScore = calculateSafetyScore(theRobots, grid)
                if(it == 6376) grid.plot(theRobots.map(Robot::position))
                it to safetyScore
            }
            .minBy { it.second }
            .first + 1

    }

    private fun calculateSafetyScore(theRobots: List<Robot>, grid: Grid) = theRobots
        .map { it.position }
        .filter { it.row != (grid.height - 1) / 2 }
        .filter { it.col != (grid.width - 1) / 2 }
        .partition { it.row < (grid.height - 1) / 2 }
        .let { (top, bottom) ->
            top.partition { it.col < (grid.width - 1) / 2 }.toList() +
                    bottom.partition { it.col < (grid.width - 1) / 2 }.toList()
        }
        .fold(1) { acc, coords ->
            acc * coords.groupingBy { it }.eachCount().values.sum()
        }

    data class Robot(val position: Coord, val velocity: Coord){
        companion object {
            fun parse(input: String): Robot {
                var cleansedInput = input.replace("p=", "")
                cleansedInput = cleansedInput.replace("v=", "")
                val (pos, velo) = cleansedInput.split(" ")
                val (col, row) = pos.split(",").map { it.toInt() }
                val (horSpeed, vertSpeed) = velo.split(",").map { it.toInt() }
                return Robot(Coord(row, col), Coord(vertSpeed, horSpeed))
            }
        }
    }
}