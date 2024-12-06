package dev.vanderblom.aoc

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PawnTest{
    @Test
    fun name() {
        val pawn = Pawn(3, 3, Orientation.NORTH)

        pawn.move(Direction.FORWARD)
        assertThat(pawn.position)
            .isEqualTo(Coord(2,3))

        pawn.move(Direction.FORWARD)
        assertThat(pawn.position)
            .isEqualTo(Coord(1,3))

        pawn.move(Direction.FORWARD)
        assertThat(pawn.position)
            .isEqualTo(Coord(0,3))

        pawn.move(Direction.FORWARD)
        assertThat(pawn.position)
            .isEqualTo(Coord(-1,3))

        assertThat(pawn.look(Direction.FORWARD))
            .isEqualTo(Coord(-2,3))

        assertThat(pawn.position)
            .isEqualTo(Coord(-1,3))

        pawn.move(Direction.BACKWARD)

        assertThat(pawn.position)
            .isEqualTo(Coord(0,3))

        assertThat(pawn.orientation)
            .isEqualTo(Orientation.SOUTH)
    }

    @Test
    fun `test turn right`() {
        val pawn = Pawn(3, 3, Orientation.NORTH)
        pawn.move(Direction.RIGHT)
        pawn.move(Direction.RIGHT)
        pawn.move(Direction.RIGHT)
        pawn.move(Direction.RIGHT)

        assertThat(pawn.position)
            .isEqualTo(Coord(3,3))
    }
}