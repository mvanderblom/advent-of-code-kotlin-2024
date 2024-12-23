package dev.vanderblom.aoc

data class Grid(val width: Int, val height: Int) {
    fun plot(coords: List<Coord>) {
        repeat(width) { print("-") }
        println()
        repeat(height) { row ->
            repeat(width) { col ->
                val count = coords.count { it == Coord(row, col) }
                if(count > 0) {
                    print(count)
                } else {
                    print(".")
                }
            }
            println()
        }
    }
}