package dev.vanderblom.aoc

class Grid(val input: List<String>) {
    fun findAll(needle: Char): List<Pair<Int, Int>> {
        return input.flatMapIndexed { lineIndex, line ->
            line
                .mapIndexedNotNull { charIndex, char ->
                    if(char == needle)
                        lineIndex to charIndex
                    else
                        null
                }
        }
    }

    fun getSurrounding(index: Pair<Int, Int>, n: Int): Surrounding {
        val (row, col) = index

        val canLookRight = col + n + 1 <= input[row].length
        val canLookDown = row + n + 1 <= input.size
        val canLookLeft = col - n >= 0
        val canLookUp = row - n >= 0

        val indices = (0..n)
        return Surrounding.of(
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
}