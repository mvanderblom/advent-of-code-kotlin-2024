package dev.vanderblom.aoc.days

import dev.vanderblom.aoc.AbstractDay
import dev.vanderblom.aoc.showMe
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

fun List<String>.checksum(): Long {
    return this.filter { it != "." }
        .map { it.toString().toLong() }
        .mapIndexed { index, fileId -> index * fileId}
        .sum()
        .showMe("checksum")
}

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
            .filter{it != "."}
            .toMutableList()
        val dataBlockCount = dataBlocks.size

        val compactedLine = mutableListOf<String>()
        line.indices.forEach { index ->
            if (index >= dataBlockCount) compactedLine.add(".")
            else if(line[index] != ".") compactedLine.add(line[index])
            else compactedLine.add(dataBlocks.removeLast())
        }

        return compactedLine
    }


    fun size() = blocks.sumOf { it.size }

    fun print(): DiskMap {
        println(toLine())
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

// 0..111....22222
// 0..111....22222
// 00...111...2...333.44.5555.6666.777.888899
// 00...111...2...333.44.5555.6666.777.888899
// 022111222......
// 022111222......
// 022111222122222
// 022111222122222
// 0099811188827773336446555566..............
// 0099811188827773336446555566..............