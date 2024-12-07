package dev.vanderblom.aoc.days

import dev.vanderblom.aoc.AbstractDay
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test


class Day7 : AbstractDay() {

    @Test
    @Order(1)
    fun `part one - example`() {
        assertThat(partOne(exampleInput))
            .isEqualTo(3749)
    }

    @Test
    @Order(2)
    fun `part one - actual`() {
        assertThat(partOne(actualInput))
            .isEqualTo(2664460013123)
    }

    @Test
    @Order(3)
    fun `part two - example`() {
        assertThat(partTwo(exampleInput))
            .isEqualTo(11387)
    }

    @Test
    @Order(4)
    fun `part two - actual`() {
        assertThat(partTwo(actualInput))
            .isEqualTo(426214131924213L)
    }

    private fun partOne(input: List<String>): Long {
        return input.map(::parseLine)
            .filter { (operands, outcome) -> isSolvable(operands, outcome) }
            .sumOf { (_, outcome) -> outcome }
    }

    private fun partTwo(input: List<String>): Long {
        return input.map(::parseLine)
            .filter { (operands, outcome) -> isSolvable(operands, outcome, true) }
            .sumOf { (_, outcome) -> outcome }
    }

    private fun parseLine(equationStr: String): Pair<List<Long>, Long> {
        val (outcome, operandsString) = equationStr.split(": ")
        val operands = operandsString.split(" ").map { it.toLong() }
        return operands to outcome.toLong()
    }

    private fun isSolvable(operands: List<Long>, result: Long, includeConcat: Boolean = false): Boolean {

        if (operands.size == 1) return operands[0] == result

        val remainingOperands = operands.subList(2, operands.size)

        val multiplicationResult = operands[0] * operands[1]
        if (multiplicationResult <= result
            && isSolvable(remainingOperands.prepend(multiplicationResult), result, includeConcat)) {
            return true
        }

        val plusResult = operands[0] + operands[1]
        if (plusResult <= result
            && isSolvable(remainingOperands.prepend(plusResult), result, includeConcat)) {
            return true
        }

        if(includeConcat) {
            val concatResult = (operands[0].toString() + operands[1].toString()).toLong()
            if(concatResult <= result
                && isSolvable(remainingOperands.prepend(concatResult), result, true)) {
                return true
            }
        }

        return false
    }

    private inline fun <reified T> List<T>.prepend(item: T): List<T> {
        return listOf(item, *this.toTypedArray())
    }
}