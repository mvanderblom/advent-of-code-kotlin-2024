package dev.vanderblom.aoc.y2024

import dev.vanderblom.aoc.AbstractDay
import dev.vanderblom.aoc.Coord
import dev.vanderblom.aoc.Grid
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day12 : AbstractDay() {

    @Test
    @Order(1)
    fun `part one - example`() {
        assertThat(partOne(exampleInput))
            .isEqualTo(1930L)
    }

    @Test
    @Order(2)
    fun `part one - actual`() {
        assertThat(partOne(actualInput))
            .isEqualTo(1) // 934632 too low;
    }

    @Test
    @Order(3)
    fun `part two - example`() {
        assertThat(partTwo(exampleInput))
            .isEqualTo(1)
    }

    @Test
    @Order(4)
    fun `part two - actual`() {
        assertThat(partTwo(actualInput))
            .isEqualTo(1)
    }

    @Test
    fun groupsByCharFirstExample() {
        val groupsByChar = getGroupsByChar(
            listOf(
                "AAAA",
                "BBCD",
                "BBCC",
                "EEEC",
            )
        )

        assertThat(groupsByChar)
            .hasSize(5)

        assertThat(groupsByChar.keys.sorted().joinToString(""))
            .isEqualTo("ABCDE")

        groupsByChar.forEach { (char, groups) ->
            assertThat(groups)
                .withFailMessage("Expected $char to be in 1 group, but was in ${groups.size}")
                .hasSize(1)
        }

        val groupA = groupsByChar['A']!![0]
        assertThat(groupA.getPerimeter()).isEqualTo(10)
        assertThat(groupA.getPrice()).isEqualTo(40)

        val groupB = groupsByChar['B']!![0]
        assertThat(groupB.getPerimeter()).isEqualTo(8)
        assertThat(groupB.getPrice()).isEqualTo(32)

        val groupC = groupsByChar['C']!![0]
        assertThat(groupC.getPerimeter()).isEqualTo(10)
        assertThat(groupC.getPrice()).isEqualTo(40)

        val groupD = groupsByChar['D']!![0]
        assertThat(groupD.getPerimeter()).isEqualTo(4)
        assertThat(groupD.getPrice()).isEqualTo(4)

        val groupE = groupsByChar['E']!![0]
        assertThat(groupE.getPerimeter()).isEqualTo(8)
        assertThat(groupE.getPrice()).isEqualTo(24)

        assertThat(groupsByChar.values.flatten().sumOf { it.getPrice() })
            .isEqualTo(140)
    }

    @Test
    fun groupsByCharSecondExample() {
        val groupsByChar = getGroupsByChar(
            listOf(
                "OOOOO",
                "OXOXO",
                "OOOOO",
                "OXOXO",
                "OOOOO"
            )
        )

        assertThat(groupsByChar)
            .hasSize(2)

        assertThat(groupsByChar.keys.sorted().joinToString(""))
            .isEqualTo("OX")

        assertThat(groupsByChar['O'])
            .hasSize(1)

        assertThat(groupsByChar['X'])
            .hasSize(4)

        val groupO = groupsByChar['O']!![0]
        assertThat(groupO.getPerimeter()).isEqualTo(36)
        assertThat(groupO.getPrice()).isEqualTo(756)

        groupsByChar['X']!!.forEach {
            assertThat(it.getPerimeter()).isEqualTo(4)
            assertThat(it.getPrice()).isEqualTo(4)
        }
        assertThat(groupsByChar.values.flatten().sumOf { it.getPrice() })
            .isEqualTo(772)
    }

    @Test
    fun `groupsByCharSecondExample inverted L-shape`() {
        val groupsByChar = getGroupsByChar(
            listOf(
                "..X",
                "XXX"
            )
        )
        assertThat(groupsByChar['X']!!).hasSize(1)
    }

    @Test
    fun `getPerimeter basics`() {
        // empty
        assertThat(listOf<Coord>().getPerimeter())
            .isEqualTo(0)
        // X
        assertThat(listOf(Coord(0,0)).getPerimeter())
            .isEqualTo(4)
        // XX
        assertThat(listOf(Coord(0,0), Coord(0,1)).getPerimeter())
            .isEqualTo(8)
    }

    @Test
    fun `getPerimeter irregular L-shape`() {
        // XX
        // .X
        assertThat(listOf(
            Coord(0,0), Coord(0,1),
            Coord(1,1
            )
        ).getPerimeter())
            .isEqualTo(8)
    }

    @Test
    fun `getPerimeter irregular T-shape`() {
        // XXX
        // .X.
        assertThat(listOf(
            Coord(0,0), Coord(0,1), Coord(0,2),
            Coord(1,1)
        ).getPerimeter())
            .isEqualTo(10)
    }

    @Test
    fun `getPerimeter irregular U-shape`() {
        // X.X
        // XXX
        assertThat(listOf(
            Coord(0,0), Coord(0,2),
            Coord(1,0), Coord(1,1), Coord(1,2)
        ).getPerimeter())
            .isEqualTo(12)
    }

    @Test
    fun `getPerimeter nested groups`() {
        // XXX
        // X.X
        // XXX
        assertThat(listOf(
            Coord(0,0), Coord(0,1), Coord(0,2),
            Coord(1,0), Coord(1,2),
            Coord(2,0), Coord(2, 1), Coord(2, 2),
        ).getPerimeter())
            .isEqualTo(16)
    }

    private fun partOne(input: List<String>): Long {
        val groupsByChar: Map<Char, List<List<Coord>>> = getGroupsByChar(input)
        return groupsByChar.flatMap { (char, groups) ->
            groups.map {
                val size = it.size
                val perimeter = it.getPerimeter()
                val price = size * perimeter

                println("A region of $char plants with price $size * $perimeter = $price")
                price
            }
        }.sum()
    }

    private fun partTwo(input: List<String>): Int {
        return input.size
    }

    private fun getGroupsByChar(input: List<String>): Map<Char, List<List<Coord>>> {
        val groupsByCharRowCol = mutableMapOf<Char, MutableMap<Int, MutableMap<Int, MutableList<Coord>>>>()

        val grid = Grid(input)
        grid.toList().forEach { (coord, char) ->
            // prevColumn is the same char
            val prevColumnGroup = coord.col
                .takeIf { it > 0 }
                ?.let { groupsByCharRowCol[char]?.get(coord.row)?.get(coord.col - 1) }

            if (prevColumnGroup != null) {
                groupsByCharRowCol[char]!![coord.row]!![coord.col] = prevColumnGroup
            }

            // prevRow same column is the same char
            val prevRowGroup = coord.row
                .takeIf { it > 0 }
                ?.let { groupsByCharRowCol[char]?.get(coord.row - 1)?.get(coord.col) }
                ?: run {
                    if(coord.row > 0 && coord.col < grid.width - 1) {
                        // Scan ahead in the current line to look for a group in the row above that is connected
                        var i = 1
                        while (grid[Coord(coord.row, coord.col + i++)] == char) {
//                            println("Scan ahead for $char at $coord")
                            val sameCharGroupLineAbove =
                                groupsByCharRowCol[char]?.get(coord.row - 1)?.get(coord.col + i - 1)
                            if (sameCharGroupLineAbove != null) {
                                return@run sameCharGroupLineAbove
                            }
                        }
                    }
                    null
                }

            if (prevRowGroup != null) {
                groupsByCharRowCol[char]!!.computeIfAbsent(coord.row) {
                    mutableMapOf()
                }[coord.col] = prevRowGroup
            }

            groupsByCharRowCol.computeIfAbsent(char) {
                mutableMapOf()
            }.computeIfAbsent(coord.row) {
                mutableMapOf()
            }.computeIfAbsent(coord.col) {
                mutableListOf()
            }.add(coord)
        }

        val groupsByChar: Map<Char, List<List<Coord>>> = groupsByCharRowCol
            .flatMap { (char, rowColMap) ->
                rowColMap.flatMap { (rowIndex, colMap) ->
                    colMap.map { (colIndex, group) ->
                        char to group
                    }
                }
            }
            .groupBy({ it.first }, { it.second })
            .mapValues { it.value.distinct() }
        return groupsByChar
    }

    private fun List<Coord>.getPerimeter(): Long {
        if (this.isEmpty()) return 0
        if (this.size == 1) return 4
        if (this.size == 2) return 8

        return sumOf {
            val top = if(it.copy(row = it.row - 1) in this) 0 else 1
            val bottom = if(it.copy(row = it.row + 1) in this) 0 else 1
            val left = if(it.copy(col = it.col - 1) in this) 0 else 1
            val right = if(it.copy(col = it.col + 1) in this) 0 else 1
            top + bottom + left + right * 1L
        }
    }

    private fun List<Coord>.getPrice(): Long {
        return this.size * this.getPerimeter()
    }

}