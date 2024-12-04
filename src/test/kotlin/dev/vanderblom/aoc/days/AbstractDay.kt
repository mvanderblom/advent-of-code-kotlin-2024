package dev.vanderblom.aoc.days

import dev.vanderblom.aoc.readInput
import dev.vanderblom.aoc.toDayName
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
abstract class AbstractDay {
    protected val exampleInput: List<String>
    protected val actualInput: List<String>

    init {
        val simpleName = this::class.java.simpleName
        val dayNumber = simpleName.replace("Day", "").toInt()
        val dayName = dayNumber.toDayName()
        exampleInput = readInput("${dayName}-1-example")
        actualInput = readInput("${dayName}-2-actual")
    }
}