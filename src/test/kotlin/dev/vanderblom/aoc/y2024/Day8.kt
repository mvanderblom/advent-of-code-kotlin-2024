package dev.vanderblom.aoc.y2024

import dev.vanderblom.aoc.AbstractDay
import dev.vanderblom.aoc.Grid
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day8 : AbstractDay() {

    @Test
    @Order(1)
    fun `part one - example`() {
        assertThat(partOne(exampleInput))
            .isEqualTo(14)
    }

    @Test
    @Order(2)
    fun `part one - actual`() {
        assertThat(partOne(actualInput))
            .isEqualTo(273)
    }

    @Test
    @Order(3)
    fun `part two - example`() {
        assertThat(partTwo(exampleInput))
            .isEqualTo(34)
    }

    @Test
    @Order(4)
    fun `part two - actual`() {
        assertThat(partTwo(actualInput))
            .isEqualTo(1017)
    }

    private fun partOne(input: List<String>): Int {
        return Grid(input)
            .coordsByChar()
            .asSequence()
            .filter { (k, _) -> k.isLetterOrDigit() }
            .flatMap { (char, coordsPerChar) ->
                coordsPerChar.flatMap { coordA ->
                    coordsPerChar
                        .filter { it != coordA }
                        .flatMap { coordB ->
                            val delta = (coordA - coordB)
                            setOf(coordA + delta, coordB - delta)
                        }
                }
            }
            .distinct()
            .count { it.isWithIn(Grid(input)) }
    }

    private fun partTwo(input: List<String>): Int {
        return Grid(input)
            .coordsByChar()
            .asSequence()
            .filter { (k, _) -> k.isLetterOrDigit() }
            .flatMap { (char, coordsPerChar) ->
                coordsPerChar.flatMap { coordA ->
                    coordsPerChar
                        .filter { it != coordA }
                        .flatMap { coordB ->
                            val delta = (coordA - coordB)

                            val harmonics = mutableSetOf(coordA, coordB)

                            var i = 1
                            while (true) {
                                val newHarmonic = coordA + (delta * i++)
                                if (!newHarmonic.isWithIn(Grid(input))) {
                                    break;
                                }

                                harmonics.add(newHarmonic)
                            }

                            var j = 1
                            while (true) {
                                val newHarmonic = coordB - (delta * j++)
                                if (!newHarmonic.isWithIn(Grid(input))) {
                                    break;
                                }
                                harmonics.add(newHarmonic)
                            }

                            harmonics
                        }
                }
            }
            .distinct()
            .count()
    }
}