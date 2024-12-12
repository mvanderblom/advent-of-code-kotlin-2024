package dev.vanderblom.aoc

data class Coord(val row: Int, val col: Int) {
    operator fun plus(coordB: Coord) = Coord(row + coordB.row, col + coordB.col)
    operator fun minus(coordB: Coord) = Coord(row - coordB.row, col - coordB.col)
    operator fun times(i: Int) = Coord(row * i, col * i)

    fun getNeighbours() = listOf(up(), right(), down(), left())

    fun up() = Coord(row - 1, col)
    fun down() = Coord(row + 1, col)
    fun left() = Coord(row, col - 1)
    fun right() = Coord(row, col + 1)

    fun isWithIn(grid: Grid) =
        this.row >= 0 && this.col >= 0
                && this.row < grid.height
                && this.col < grid.width

}