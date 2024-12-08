package dev.vanderblom.aoc.days

import dev.vanderblom.aoc.AbstractDay
import dev.vanderblom.aoc.Grid
import dev.vanderblom.aoc.showMe
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
        val grid = Grid(input)

        val resonantFreqs= grid
            .coordsByChar()
            .filter { (k, _) -> k.isLetterOrDigit() }
            .flatMap {(char, coordsPerChar)->
                char.showMe("Finding resonantFreqs for ")
                coordsPerChar.flatMap { coordA ->
                    coordsPerChar
                        .filter { it != coordA }
                        .flatMap { coordB ->
                            coordA.showMe("coordA")
                            coordB.showMe("coordB")

                            val delta = (coordA - coordB)
                                .showMe("delta")

                            listOf(coordA + delta, coordB - delta)
                                .showMe("resonantFreqs for char $char")
                        }
                }
            }
            .toSet()
            .filter { it.isIn(grid) }
            .showMe("unique resonantFreqs")

        return resonantFreqs.count()
    }

    private fun partTwo(input: List<String>): Int {
        val grid = Grid(input)

        val resonantFreqs= grid
            .coordsByChar()
            .asSequence()
            .filter { (k, _) -> k.isLetterOrDigit() }
            .filter { (_, coordsPerChar) -> coordsPerChar.size > 1 }
            .flatMap {(char, coordsPerChar)->
                char.showMe("Finding resonantFreqs for ")
                coordsPerChar.flatMap { coordA ->
                    coordsPerChar
                        .filter { it != coordA }
                        .flatMap { coordB ->
                            coordA.showMe("coordA")
                            coordB.showMe("coordB")

                            val delta = (coordA - coordB)
                                .showMe("delta")


                            val harmonics = mutableListOf(coordA, coordB)

                            var i = 1
                            while (true){
                                val newHarmonic = coordA + (delta*i++)
                                if(!newHarmonic.isIn(grid)){
                                    newHarmonic.showMe("newHarmonic outside of grid")
                                    break;
                                }

                                newHarmonic.showMe("newHarmonic")
                                harmonics.add(newHarmonic)
                            }
                            var j = 1
                            while (true){
                                val newHarmonic = coordB - (delta*j++)
                                if(!newHarmonic.isIn(grid)){
                                    break;
                                }
                                harmonics.add(newHarmonic)
                            }

                            harmonics
                                .showMe("resonantFreqs for char $char")
                        }
                }
            }
            .toSet()
            .filter { it.isIn(grid) }
            .toList()
            .showMe("unique resonantFreqs")

        return resonantFreqs.count()
    }
}