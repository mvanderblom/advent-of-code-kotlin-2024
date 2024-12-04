package dev.vanderblom.aoc.days

import dev.vanderblom.aoc.AbstractDay
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day3: AbstractDay() {
    private val mulRegex = Regex("mul\\((\\d{1,3},\\d{1,3})\\)")
    private val doRegex = Regex("do\\(\\)")
    private val dontRegex = Regex("don't\\(\\)")

    @Test
    @Order(1)
    fun `part one - example`() {
        assertThat(partOne(exampleInput))
            .isEqualTo(161)
    }

    @Test
    @Order(2)
    fun `part one - actual`() {
        assertThat(partOne(actualInput))
            .isEqualTo(168539636)
    }

    @Test
    @Order(3)
    fun `part two - example`() {
        assertThat(partTwo(exampleInput))
            .isEqualTo(48)
    }

    @Test
    @Order(4)
    fun `part two - actual`() {
        assertThat(partTwo(actualInput))
            .isEqualTo(97529391)
    }

    private fun partOne(input: List<String>): Int {
        val completeInput = input.joinToString("")
        return mulRegex.findAll(completeInput)
            .sumOf { mul -> handleMul(mul.groups[1]!!.value) }
    }

    private fun partTwo(input: List<String>): Int {
        val completeInput = input.joinToString("")
        val enabledByIndex = getEnabledByIndex(completeInput)
        return mulRegex.findAll(completeInput)
            .map { it.groups[1]!! }
            .filter { enabledByIndex[it.range.first] }
            .map { it.value }
            .sumOf { handleMul(it) }
    }

    private fun handleMul(input: String): Int {
        val (a,b) = input.split(",")
            .map { it.toInt() }
        return a*b
    }

    private fun getEnabledByIndex(completeInput: String): List<Boolean> {
        val enableFrom = findStartIndices(doRegex, completeInput)
        val disableFrom = findStartIndices(dontRegex, completeInput)
        var enabled = true
        val enabledByIndex = completeInput.indices
            .map {
                if (it in enableFrom) {
                    enabled = true
                } else if (it in disableFrom) {
                    enabled = false
                }
                enabled
            }
        return enabledByIndex
    }

    private fun findStartIndices(regex: Regex, input: String) = regex.findAll(input)
        .map { it.range.first }
        .toList()
}