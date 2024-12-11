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
    data class Data(val initialValue: String, val rules: Map<String, Char>) {
        companion object{

        }
    }
    private fun performPairInsertion(input: List<String>, n: Int): Long {
        val inputStr = input[0]

        val rules = input
            .subList(2, input.size)
            .associate {
                val vals = it.split(" -> ")
                vals[0] to vals[1]
            }

        fun String.morph() = listOf(this[0]+rules[this]!!, rules[this]!!+this[1])

        val elementCount = inputStr.groupingBy { it }.eachCount().mapValues { it.value.toLong() }.toMutableMap()

        val pairCount = inputStr
            .windowed(2, step = 1)
            .groupingBy { it }
            .eachCount()
            .mapValues { (_, count) -> count.toLong() }
            .toMutableMap()

        repeat(n) {
            pairCount
                .toMap()
                .forEach{ (oldPair, oldPairCount) ->
                    pairCount[oldPair] = pairCount[oldPair]!! - oldPairCount
                    if (pairCount[oldPair] == 0L) pairCount.remove(oldPair)

                    val newElement = rules[oldPair]!![0]
                    elementCount.compute(newElement, { _, count -> count?.plus(oldPairCount) ?: oldPairCount })
                    oldPair
                        .morph()
                        .forEach { newValue ->
                            pairCount.compute(newValue, { _, count -> count?.plus(oldPairCount) ?: oldPairCount })
                        }
                }
            println(pairCount)
        }

        val elementsSortedByCount =
          elementCount.toList().sortedBy { it.second }

        return elementsSortedByCount.last().second - elementsSortedByCount.first().second
    }

}