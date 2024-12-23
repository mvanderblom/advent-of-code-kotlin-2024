package dev.vanderblom.aoc

class DataGrid(private val input: List<String>) {

    val width: Int = input.map { it.length }.distinct().single()
    val height: Int = input.size
    val size: Int get() = width * height

    fun findAll(needle: Char): List<Coord> {
        return input.flatMapIndexed { lineIndex, line ->
            line
                .mapIndexedNotNull { charIndex, char ->
                    if(char == needle)
                        Coord(lineIndex, charIndex)
                    else
                        null
                }
        }
    }

    fun findSingle(needle: Char): Coord {
        return findAll(needle).single()
    }

    operator fun get(coord: Coord): Char? {
        return input[coord]
    }

    fun getSurrounding(coord: Coord, n: Int, includeSelf: Boolean = true): Surrounding {
        val (row, col) = coord

        require(row >= 0 && col >= 0) { "row and col should be greater than zero" }
        require(row < height) { "row cannot be greater than ${height - 1}" }
        require(col < width) { "col cannot be greater than ${width - 1}" }

        val canLookRight = col + n + 1 <= input[row].length
        val canLookDown = row + n + 1 <= input.size
        val canLookLeft = col - n >= 0
        val canLookUp = row - n >= 0

        val startIndex = if(includeSelf) 0 else 1
        val indices = (startIndex..n)
        return Surrounding.of(
            coord,
            input[coord]!!,
            if (canLookUp) indices.map { input[row - it][col] } else null,
            if (canLookRight) indices.map { input[row][col + it] } else null,
            if (canLookDown) indices.map { input[row + it][col] } else null,
            if (canLookLeft) indices.map { input[row][col - it] } else null,
            if (canLookUp && canLookRight) indices.map { input[row - it][col + it] } else null,
            if (canLookDown && canLookRight) indices.map { input[row + it][col + it] } else null,
            if (canLookDown && canLookLeft) indices.map { input[row + it][col - it] } else null,
            if (canLookUp && canLookLeft) indices.map { input[row - it][col - it] } else null
        )
    }

    private operator fun List<String>.get(coord: Coord): Char? {
        if(coord.row < 0 || coord.col < 0 || coord.row >= height || coord.col >= width) return null
        return this[coord.row][coord.col]
    }

    fun withCharAtReplacedBy(coord: Coord, char: Char): DataGrid {
        return DataGrid(
            input.mapIndexed { rowIndex, row ->
                if(rowIndex == coord.row)
                    row.replaceRange(coord.col..coord.col, char.toString())
                else
                    row
            }
        )
    }

    fun coordsByChar() = input
            .flatMapIndexed { lineIndex, line ->
                line
                    .mapIndexed { charIndex, char ->
                            char to Coord(lineIndex, charIndex)
                    }
            }
            .groupBy ({ it.first }, { it.second } )

    fun toList() = input.flatMapIndexed{ rowIndex, line ->
        line.mapIndexed { colIndex, char ->
            Pair(Coord(rowIndex, colIndex), char)
        }
    }

    fun showMe(): DataGrid {
        repeat(width) { print("-") }
        println()
        input.forEach(::println)
        return this
    }
}

