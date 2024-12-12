package dev.vanderblom.aoc.y2024

import dev.vanderblom.aoc.AbstractDay
import dev.vanderblom.aoc.Coord
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day12 : AbstractDay() {

    @Test
    @Order(1)
    fun `part one - example`() {
        assertThat(partOne(exampleInput))
            .isEqualTo(1930)
    }

    @Test
    @Order(2)
    fun `part one - actual`() {
        assertThat(partOne(actualInput))
            .isEqualTo(1477762) // 934632 too low;
    }

    @Test
    @Order(3)
    fun `part two - example`() {
        assertThat(partTwo(exampleInput))
            .isEqualTo(1206)
    }

    @Test
    @Order(4)
    fun `part two - actual`() {
        assertThat(partTwo(actualInput))
            .isEqualTo(923480)
    }

    private fun partOne(input: List<String>): Int {
        return input.buildGroups()
            .sumOf { (_, group) -> getPerimeter(group) * group.size }
    }

    private fun partTwo(input: List<String>): Int {
        return input.buildGroups()
            .sumOf { (_, group) -> getSides(group) * group.size }
    }

    private fun List<String>.buildGroups(): Set<Pair<Char, Set<Coord>>> {
        val groupsByChar = mutableSetOf<Pair<Char, Set<Coord>>>()
        withIndex().forEach { (rowIndex, row) ->
            row.withIndex().forEach { (charIndex, char) ->
                val coord = Coord(rowIndex, charIndex)

                val coordNotInExistingGroup = groupsByChar.none { (_, groupCoords) -> groupCoords.contains(coord) }
                if (coordNotInExistingGroup) {
                    groupsByChar.add(char to buildGroup(coord, setOf(coord)))
                }
            }
        }
        return groupsByChar
    }

    private fun List<String>.buildGroup(startingCoord: Coord, groupAcc: Set<Coord>): Set<Coord> {
        val neighboursWithSameChar = startingCoord.getNeighbours()
            .filter { !groupAcc.contains(it) } // Don't reprocess neighbours that are already in group
            .filter { this.containsCoord(it) } // Only look at neighbours that are actually on the grid
            .filter { this[startingCoord.row][startingCoord.col] == this[it.row][it.col] } // Only look at neighbours with the same letter
            .toSet()

        return neighboursWithSameChar.fold(groupAcc + neighboursWithSameChar) { acc, neighbour ->
            acc + buildGroup(neighbour, acc)
        }
    }

    private fun getPerimeter(group: Set<Coord>) = getEdges(group).size

    private fun getSides(group: Set<Coord>): Int {
        val allEdges = getEdges(group)
        val combinedEdges = allEdges.toMutableSet()
        allEdges.forEach { edge ->
            if (combinedEdges.contains(edge)) {
                if (edge.orientation == Edge.Orientation.BOTTOM_TO_TOP || edge.orientation == Edge.Orientation.TOP_TO_BOTTOM) {
                    combineSides(edge, combinedEdges) { it.left() }
                    combineSides(edge, combinedEdges) { it.right() }
                } else {
                    combineSides(edge, combinedEdges) { it.up() }
                    combineSides(edge, combinedEdges) { it.down() }
                }
            }
        }
        return combinedEdges.size
    }

    private fun combineSides(edge: Edge, combinedEdges: MutableSet<Edge>, getNeighbour: (Coord) -> Coord) {
        var neighbouringEdge = Edge(getNeighbour(edge.coord), edge.orientation)

        while (combinedEdges.contains(neighbouringEdge)) {
            combinedEdges.remove(neighbouringEdge)
            neighbouringEdge = Edge(getNeighbour(neighbouringEdge.coord), edge.orientation)
        }
    }

    private fun getEdges(group: Set<Coord>): Set<Edge> {
        return group.fold(mutableSetOf()) { edges, coord ->
            edges.also {
                if (!group.contains(coord.up())) {
                    it.add(Edge(coord, Edge.Orientation.BOTTOM_TO_TOP))
                }
                if (!group.contains(coord.down())) {
                    it.add(Edge(coord.down(), Edge.Orientation.TOP_TO_BOTTOM))
                }
                if (!group.contains(coord.left())) {
                    it.add(Edge(coord, Edge.Orientation.RIGHT_TO_LEFT))
                }
                if (!group.contains(coord.right())) {
                    it.add(Edge(coord.right(), Edge.Orientation.LEFT_TO_RIGHT))
                }
            }
        }
    }

    private fun List<String>.containsCoord(coord: Coord): Boolean {
        return coord.row >= 0 && coord.col >= 0 && coord.row < size && coord.col < first().length
    }
}

data class Edge(val coord: Coord, val orientation: Orientation) {
    enum class Orientation {
        BOTTOM_TO_TOP,
        TOP_TO_BOTTOM,
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT
    }
}
