package dev.vanderblom.aoc.days

import dev.vanderblom.aoc.AbstractDay
import dev.vanderblom.aoc.showMe
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test


class Day11 : AbstractDay() {

    @Test
    @Order(1)
    fun `part one - example`() {
        assertThat(partOne(exampleInput[0]))
            .isEqualTo(55312)
    }

    @Test
    @Order(2)
    fun `part one - actual`() {
        assertThat(partOne(actualInput[0]))
            .isEqualTo(186175)
    }

    @Test
    @Order(3)
    fun `part two - example`() {
        assertThat(partTwo(exampleInput[0]))
            .isEqualTo(1)
    }

    @Test
    @Order(4)
    fun `part two - actual`() {
        assertThat(partTwo(actualInput[0]))
            .isEqualTo(1)
    }

    private fun partOne(input: String) = input
        .split(" ")
        .map { Stone(it.toLong()) }
        .let {
            var actualStoneList = it
            repeat(25 ) {iteration ->
                actualStoneList = actualStoneList
                    .flatMap { stone ->
                        stone.morph()
                    }.showMe{"number of stones after $iteration: " + it.size}
            }
            actualStoneList
        }.size



    private fun partTwo(input: String): Int {
        return 1
    }
}

fun List<Stone>.showMe(): List<Stone> {
    this.map { it.value }.joinToString(" ")
    return this
}

data class Stone(val value: Long) {
    fun morph(): List<Stone> {
        if(value == 0L) return listOf(Stone(1L))
        val stringValue = value.toString()
        if(stringValue.length % 2 == 0 ) return listOf(
            Stone(stringValue.substring(0, stringValue.length / 2).toLong()),
            Stone(stringValue.substring(stringValue.length / 2, stringValue.length).toLong())
        )
        return listOf(Stone(value * 2024L))
    }
}
//expected: 1 2024 1 0 9 9 2021976
//actual  : 1 2024 1 0 9 9 2021976
/**
 * If the stone is engraved with the number 0,
 *  it is replaced by a stone engraved with the number 1.
 * If the stone is engraved with a number that has an even number of digits,
 *  it is replaced by two stones. The left half of the digits are engraved on the new left stone, and the right half of the digits are engraved on the new right stone. (The new numbers don't keep extra leading zeroes: 1000 would become stones 10 and 0.)
 * If none of the other rules apply,
 *  the stone is replaced by a new stone;
 *  the old stone's number multiplied by 2024 is engraved on the new stone.
 */