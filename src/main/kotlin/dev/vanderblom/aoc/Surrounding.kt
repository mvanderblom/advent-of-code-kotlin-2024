package dev.vanderblom.aoc

data class Surrounding(
    val top: String?,
    val right: String?,
    val bottom: String?,
    val left: String?,
    val topRight: String?,
    val bottomRight: String?,
    val bottomLeft: String?,
    val topLeft: String?,
) {
    fun all() = listOfNotNull(top, right, bottom, left, topRight, bottomRight, bottomLeft, topLeft)
    fun diagonals() = listOfNotNull((topLeft?.reversed() ?: "") + (bottomRight?.substring(1) ?: ""), (bottomLeft?.reversed() ?: "") + (topRight?.substring(1) ?: ""))

    companion object {
        fun of(
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