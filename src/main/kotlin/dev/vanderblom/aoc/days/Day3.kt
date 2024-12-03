package dev.vanderblom.aoc.days

import dev.vanderblom.aoc.AocDay

class Day3 : AocDay(161, 168539636, 48, 97529391) {
    private val mulRegex = Regex("mul\\((\\d{1,3},\\d{1,3})\\)")
    private val doRegex = Regex("do\\(\\)")
    private val dontRegex = Regex("don't\\(\\)")

    private fun handleMul(input: String): Int {
        val (a,b) = input.split(",")
            .map { it.toInt() }
        return a*b
    }

    private fun findStartIndices(regex: Regex, input: String) = regex.findAll(input)
        .map { it.range.first }
        .toList()

    override fun partOne(input: List<String>): Int {
        val completeInput = input.joinToString("")
        return mulRegex.findAll(completeInput)
                    .sumOf { mul -> handleMul(mul.groups[1]!!.value) }
    }

    override fun partTwo(input: List<String>): Int {
        val completeInput = input.joinToString("")
        val enabledByIndex = getEnabledByIndex(completeInput)
        return mulRegex.findAll(completeInput)
            .map { it.groups[1]!! }
            .filter { enabledByIndex[it.range.first] }
            .map { it.value }
            .sumOf { handleMul(it) }
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
}

fun main() {
    val day = Day3()
    day.runPartOne()
    day.runPartTwo()
}

