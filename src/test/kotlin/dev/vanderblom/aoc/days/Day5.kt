package dev.vanderblom.aoc.days

import dev.vanderblom.aoc.AbstractDay
import dev.vanderblom.aoc.firstOrNullIndexed
import dev.vanderblom.aoc.listAfter
import dev.vanderblom.aoc.middle
import dev.vanderblom.aoc.not
import dev.vanderblom.aoc.showMe
import dev.vanderblom.aoc.splitOnce
import dev.vanderblom.aoc.splitValuesBy
import dev.vanderblom.aoc.toBoolean
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day5 : AbstractDay() {

    @Test
    @Order(1)
    fun `part one - example`() {
        assertThat(partOne(exampleInput))
            .isEqualTo(143)
    }

    @Test
    @Order(2)
    fun `part one - actual`() {
        assertThat(partOne(actualInput))
            .isEqualTo(5964)
    }

    @Test
    @Order(3)
    fun `part two - example`() {
        assertThat(partTwo(exampleInput))
            .isEqualTo(123)
    }

    @Test
    @Order(4)
    fun `part two - actual`() {
        assertThat(partTwo(actualInput))
            .isEqualTo(4719)
    }

    private fun partOne(input: List<String>): Int {
        val(printOrderByPage, printInstructions) = parse(input)

        return printInstructions
            .filter { hasCorrectOrder(printOrderByPage, it) }
            .sumOf { it.middle() }
    }


    private fun partTwo(input: List<String>): Int {
        val(printOrderByPage, printInstructions) = parse(input)

        return printInstructions
            .filter { !hasCorrectOrder(printOrderByPage, it) }
            .map { sort(printOrderByPage, it) }
            .sumOf { it.middle() }
    }

    private fun parse(input: List<String>): Pair<Map<Int, Set<Int>>, List<List<Int>>> {
        return input.splitOnce("")
            .let { (rawOrdering, rawPrintInstructions) ->

                val printOrderByPage = rawOrdering
                    .splitValuesBy("|")
                    .groupBy({ it[0] }, { it[1] })
                    .mapValues { (_,v)-> v.toSet() }

                printOrderByPage to rawPrintInstructions.splitValuesBy(",")
            }
    }

    private fun sort(printOrderByPage: Map<Int, Set<Int>>, printInstruction: List<Int>): List<Int> {
        return printInstruction.sortedWith { p1: Int, p2: Int ->
            printOrderByPage[p1]
                ?.let {if (p2 in it) { -1 } else { 1 } }
                ?: 0
        }.showMe()
    }

    private fun hasCorrectOrder(printOrderByPage: Map<Int, Set<Int>>, printInstruction: List<Int>): Boolean {
        return printInstruction
            .firstOrNullIndexed { pageIndex, page ->
                val laterPages = printInstruction.listAfter(pageIndex + 1)
                val pagesThatMustBeLater = printOrderByPage[page] ?: emptySet()
                laterPages.isNotEmpty() && pagesThatMustBeLater not { containsAll(laterPages) }
            }.toBoolean().not()
    }

}