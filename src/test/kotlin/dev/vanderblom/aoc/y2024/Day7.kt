package dev.vanderblom.aoc.y2024

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
        return solve(input, Solver(listOf(Operator.MULTIPLY, Operator.ADD)))
    }

    private fun partTwo(input: List<String>): Long {
        return solve(input, Solver(listOf(Operator.ADD, Operator.MULTIPLY, Operator.CONCAT)))
    }

    private fun solve(input: List<String>, solver: Solver) = input
        .map { parseLine(it) }
        .filter { (operands, outcome) -> solver.isSolvable(operands, outcome) }
        .sumOf { (_, outcome) -> outcome }

    private fun parseLine(equationStr: String): Pair<List<Long>, Long> {
        val (outcome, operandsString) = equationStr.split(": ")
        val operands = operandsString.split(" ").map { it.toLong() }
        return operands to outcome.toLong()
    }
}

class Solver(private val operators: List<Operator>) {
    fun isSolvable(operands: List<Long>, outcome: Long, acc: Long = 0): Boolean {
        if (operands.isEmpty()) return acc == outcome

        val remainingOperands = operands.subList(1, operands.size)

        operators.forEach {
            val operatorResult = it.execute(acc, operands[0])
            if (operatorResult <= outcome
                && isSolvable(remainingOperands, outcome, operatorResult)) {
                return true
            }
        }
        return false
    }
}

enum class Operator(val execute: (Long, Long)-> Long) {
    ADD({ a, b -> a + b }),
    MULTIPLY({ a, b -> a * b }),
    CONCAT({ a, b -> "$a$b".toLong() }),
}
