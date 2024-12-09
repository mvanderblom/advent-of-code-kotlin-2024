package dev.vanderblom.aoc.days

import dev.vanderblom.aoc.AbstractDay
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day9 : AbstractDay() {

    @Test
    @Order(1)
    fun `part one - example`() {
        assertThat(partOne(exampleInput[0]))
            .isEqualTo(1928)
    }

    @Test
    @Order(2)
    fun `part one - actual`() {
        assertThat(partOne(actualInput[0]))
            .isEqualTo(6430446922192) //90950452031; too low
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

    private fun partOne(input: String): Long {
        val diskMap = DiskMap(input)
        return diskMap.compact().checksum()
    }

    private fun partTwo(input: String): Long {
        return 1
    }
}

fun List<String>.checksum() = this.filter { it != "." }
    .map { it.toString().toLong() }
    .mapIndexed { index, fileId -> index * fileId}
    .sum()

class DiskMap(val input: String) {
    val blocks: MutableList<Block>

    init {
        var fileCounter = 0L
        blocks = input
            .mapIndexed { index, s ->
                var id: Long? = null
                var type = Block.Type.EMPTY_SPACE
                if (index % 2 == 0) {
                    id = fileCounter++
                    type = Block.Type.FILE
                }
                Block(id, s.toString().toLong(), type)
            }
            .toMutableList()
    }

    fun compact(): List<String> {
        val line = toLine()
        val dataBlocks = toLine()
            .filter { it != "." }
            .toMutableList()
        val dataBlockCount = dataBlocks.size

        return line.mapIndexed { index, char ->
            if (index >= dataBlockCount) "."
            else if (char != ".") char
            else dataBlocks.removeLast()
        }
    }


    fun size() = blocks.sumOf { it.size }

    fun print(): DiskMap {
        println(toLine().joinToString(""))
        return this
    }

    fun toLine() = blocks.flatMap { block ->
        (1..block.size).map {
            if (block.type == Block.Type.FILE) {
                block.id.toString()
            } else {
                "."
            }
        }
    }
}

data class Block(val id: Long?, val size: Long, val type: Type) {
    enum class Type{
        FILE,
        EMPTY_SPACE
    }
}