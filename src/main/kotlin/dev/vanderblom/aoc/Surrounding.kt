package dev.vanderblom.aoc

data class Surrounding(
    val index: Coord,
    val char: Char,
    val top: String?,
    val right: String?,
    val bottom: String?,
    val left: String?,
    val topRight: String?,
    val bottomRight: String?,
    val bottomLeft: String?,
    val topLeft: String?,
) {
    fun all() = listOfNotNull(top, topRight, right, bottomRight, bottom, bottomLeft, left, topLeft)
    fun diagonals() = listOfNotNull(
        if(topLeft != null || bottomRight != null) (topLeft?.reversed()?.withoutLast(1) ?: "") + char + (bottomRight?.substring(1) ?: "") else null,
        if(bottomLeft != null || topRight != null) (bottomLeft?.reversed()?.withoutLast(1) ?: "") + char + (topRight?.substring(1) ?: "") else null,
    )

    fun charAtEquals(orientation: Orientation, char: Char): Boolean {
        return char == when(orientation) {
            Orientation.NORTH -> top
            Orientation.EAST -> right
            Orientation.SOUTH -> bottom
            Orientation.WEST -> left
        }?.let { it[0] }
    }

    fun getPlusPattern() = listOfNotNull(top, right, bottom, left)

    fun getXPattern() = listOfNotNull(topRight, bottomRight, bottomLeft, topLeft)

    companion object {
        fun of(
            index: Coord,
            char: Char,
            top: List<Char>?,
            right: List<Char>?,
            bottom: List<Char>?,
            left: List<Char>?,
            topRight: List<Char>?,
            bottomRight: List<Char>?,
            bottomLeft: List<Char>?,
            topLeft: List<Char>?,
        ): Surrounding {
            return Surrounding(
                index,
                char,
                top?.joinToString(""),
                right?.joinToString(""),
                bottom?.joinToString(""),
                left?.joinToString(""),
                topRight?.joinToString(""),
                bottomRight?.joinToString(""),
                bottomLeft?.joinToString(""),
                topLeft?.joinToString(""),
            )
        }
    }
}