package dev.vanderblom.aoc.y2021

import dev.vanderblom.aoc.AbstractDay
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day14 : AbstractDay() {

    @Test
    @Order(1)
    fun `part one - example`() {
        assertThat(performPairInsertion(exampleInput, 10))
            .isEqualTo(1588)
    }

    @Test
    @Order(2)
    fun `part one - actual`() {
        assertThat(performPairInsertion(actualInput, 10))
            .isEqualTo(2375)
    }

    @Test
    @Order(3)
    fun `part two - example`() {
        assertThat(performPairInsertion(exampleInput, 40))
            .isEqualTo(2188189693529)
    }

    @Test
    @Order(4)
    fun `part two - actual`() {
        assertThat(performPairInsertion(actualInput, 40))
            .isEqualTo(1976896901756L)
    }

    private fun performPairInsertion(input: List<String>, n: Int): Long {

        val data = Data.of(input)

        val elementCount = data.elementCount.toMutableMap()
        val pairCount = data.pairCount.toMutableMap()

        repeat(n) {
            pairCount
                .toMap()
                .forEach{ (oldPair, oldPairCount) ->
                    pairCount[oldPair] = pairCount[oldPair]!! - oldPairCount
                    if (pairCount[oldPair] == 0L) pairCount.remove(oldPair)

                    val newElement = data[oldPair]!!
                    elementCount.compute(newElement, { _, count -> count?.plus(oldPairCount) ?: oldPairCount })
                    data
                        .morph(oldPair)
                        .forEach { newValue ->
                            pairCount.compute(newValue, { _, count -> count?.plus(oldPairCount) ?: oldPairCount })
                        }
                }
        }

        val elementsSortedByCount =
          elementCount.toList().sortedBy { it.second }

        return elementsSortedByCount.last().second - elementsSortedByCount.first().second
    }

}

data class Data(val initialValue: String, val rules: Map<String, Char>) {

    operator fun get(key: String) = rules[key]

    fun morph(string: String) = listOf(string[0]+""+rules[string]!!, rules[string]!!+""+string[1])

    val elementCount = initialValue.groupingBy { it }.eachCount().mapValues { it.value.toLong() }

    val pairCount = initialValue
        .windowed(2, step = 1)
        .groupingBy { it }
        .eachCount()
        .mapValues { (_, count) -> count.toLong() }

    companion object{
        fun of(input: List<String>): Data {
            val inputStr = input[0]

            val rules = input
                .subList(2, input.size)
                .associate {
                    val vals = it.split(" -> ")
                    vals[0] to vals[1][0]
                }
            return Data(
                inputStr,
                rules
            )
        }
    }
}