package dev.vanderblom.aoc

enum class Orientation(
    val char: Char,
) {
    NORTH('^'),
    EAST('>'),
    SOUTH('v'),
    WEST('<');

    fun look(direction: Direction): Orientation {
        if(direction == Direction.FORWARD) return this
        return orientationDirectionMap[this]!![direction]!!
    }

    companion object {
        private val orientationDirectionMap = mapOf(
            NORTH to mapOf(
                Direction.LEFT to WEST,
                Direction.RIGHT to EAST,
                Direction.BACKWARD to SOUTH,
            ),
            EAST to mapOf(
                Direction.LEFT to NORTH,
                Direction.RIGHT to SOUTH,
                Direction.BACKWARD to WEST,
            ),
            SOUTH to mapOf(
                Direction.LEFT to EAST,
                Direction.RIGHT to WEST,
                Direction.BACKWARD to NORTH,
            ),
            WEST to mapOf(
                Direction.LEFT to SOUTH,
                Direction.RIGHT to NORTH,
                Direction.BACKWARD to EAST,
            )
        )
    }
}