package dev.vanderblom.aoc

data class Pawn(var row: Int, var col: Int, var orientation: Orientation = Orientation.NORTH, val path: MutableList<Coord> = mutableListOf()) {

    val position get() = Coord(row, col)

    fun turn(direction: Direction): Pawn {
        orientation = orientation.look(direction)
        return this
    }

    fun move(direction: Direction, n: Int = 1): Pawn {
        orientation = orientation.look(direction)
        move(orientation, n)
        return this
    }

    fun move(orientation: Orientation, n: Int = 1): Pawn {
        when (orientation) {
            Orientation.NORTH -> row -= n
            Orientation.EAST -> col += n
            Orientation.SOUTH -> row += n
            Orientation.WEST -> col -= n
        }
        path.add(position)
        return this
    }

    fun look(direction: Direction, n: Int = 1): Coord {
        return this.copy().move(direction, n).position
    }

    fun isOn(grid: Grid) = col < grid.width && row < grid.height && col >= 0 && row >= 0

    fun copy() = Pawn(row, col, orientation, path.toMutableList())

    companion object {
        fun of(row: Int, col: Int, orientation: Orientation = Orientation.NORTH) = Pawn(row, col, orientation, mutableListOf(Coord(row, col)))
        fun of(coord: Coord, orientation: Orientation = Orientation.NORTH) = Pawn(coord.row, coord.col, orientation, mutableListOf(Coord(coord.row, coord.col)))
    }
}

