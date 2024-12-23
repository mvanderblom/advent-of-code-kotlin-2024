package dev.vanderblom.aoc

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DataGridTest {
    val testGrid = DataGrid(listOf(
        "XOX",
        "OXO",
        "XOX"
    ))

    @Test
    fun `all X's are found`() {
        val allXes = testGrid.findAll('X')
        assertThat(allXes)
            .hasSize(5)
    }

    @Test
    fun `all O's are found`() {
        val allOs = testGrid.findAll('O')
        assertThat(allOs)
            .hasSize(4)
    }

    @Test
    fun `all Z's are found`() {
        val allZs = testGrid.findAll('Z')
        assertThat(allZs)
            .hasSize(0)
    }

    @Test
    fun `surrounding - all looks from center outwards in a clockwise pattern`() {
        val surrounding = testGrid.getSurrounding(Coord(1,1), 1)
        assertThat(surrounding.all())
            .hasSize(8)
            .containsExactly("XO", "XX", "XO", "XX", "XO", "XX", "XO", "XX")
        assertThat(surrounding.diagonals())
            .hasSize(2)
            .containsExactly("XXX", "XXX")
    }

    @Test
    fun `surrounding - topleft`() {
        val surrounding = testGrid.getSurrounding(Coord(0, 0), 1)
        assertThat(surrounding.all())
            .hasSize(3)
            .containsExactly("XO", "XX", "XO")
        assertThat(surrounding.diagonals())
            .hasSize(1)
            .containsExactlyInAnyOrder("XX")
    }

    @Test
    fun `surrounding - topRight`() {
        val surrounding = testGrid.getSurrounding(Coord(0, 2), 1)
        assertThat(surrounding.all())
            .hasSize(3)
            .containsExactly("XO", "XX", "XO")
        assertThat(surrounding.diagonals())
            .hasSize(1)
            .containsExactlyInAnyOrder("XX")
    }

    @Test
    fun `surrounding - bottomRight`() {
        val surrounding = testGrid.getSurrounding(Coord(2, 2), 1)
        assertThat(surrounding.all())
            .hasSize(3)
            .containsExactlyInAnyOrder("XO", "XX", "XO")
        assertThat(surrounding.diagonals())
            .hasSize(1)
            .containsExactly("XX")
    }

    @Test
    fun `surrounding - bottomLeft`() {
        val surrounding = testGrid.getSurrounding(Coord(2, 0), 1)
        assertThat(surrounding.all())
            .hasSize(3)
            .containsExactlyInAnyOrder("XO", "XX", "XO")
        assertThat(surrounding.diagonals())
            .hasSize(1)
            .containsExactly("XX")
    }

    @Test
    fun `surrounding diagonals finds diagonals from topleft left to right`() {
        val testGrid = DataGrid(listOf(
            "H...O",
            ".E.L.",
            "..L..",
            ".U.L.",
            "L...O",
        ))
        val surrounding = testGrid.getSurrounding(Coord(2, 2), 2)
        assertThat(surrounding.diagonals())
            .hasSize(2)
            .containsExactly("HELLO", "LULLO")
    }

    @Test
    fun `invalid grid throws`() {
        assertThrows<IllegalArgumentException> {
            DataGrid(listOf(
                "123",
                "12",
                "123",
            ))
        }
    }

    @Test
    fun `getSurounding for invalid index throws`() {
        assertThrows<IllegalArgumentException> {
            testGrid.getSurrounding(Coord(3, 3), 1)
        }
        assertThrows<IllegalArgumentException> {
            testGrid.getSurrounding(Coord(2, 3), 1)
        }
    }

    @Test
    fun replaceCharAt() {
        testGrid
            .withCharAtReplacedBy(Coord(0,0), 'A')
            .showMe()
    }
}