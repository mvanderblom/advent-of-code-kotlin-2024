package dev.vanderblom.aoc.days

import dev.vanderblom.aoc.AbstractDay
import dev.vanderblom.aoc.replaceAt
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
            .isEqualTo(6430446922192) //90950452031 too low;
    }

    @Test
    @Order(3)
    fun `part two - example`() {
        assertThat(partTwo(exampleInput[0]))
            .isEqualTo(2858)
    }

    @Test
    @Order(4)
    fun `part two - actual`() {
        assertThat(partTwo(actualInput[0]))
            .isEqualTo(6460170593016L) //6460120102583 too low; 7506186446615 too high;
    }

    private fun partOne(input: String): Long {
        val diskMap = DiskMap.of(input)
        return diskMap.compactBlockLevel().checksum()
    }

    private fun partTwo(input: String): Long {
        val diskMap = DiskMap.of(input)
        return diskMap.compactFileLevel().checksum()
    }
}

fun List<String>.checksum() = this
    .map {
        if(it == ".") 0L
        else it.toLong()
    }
    .mapIndexed { index, fileId -> index * fileId}
    .sum()


fun List<Block>.print() {
    println(toStrings().joinToString(""))
}

fun List<Block>.toStrings() = flatMap { block ->
    (1..block.size).map {
        if (block.type == Block.Type.FILE) {
            block.id.toString()
        } else {
            "."
        }
    }
}

fun List<Block>.size() = this.sumOf { it.size }


class Line(initialBlocks: List<Block>) {
    val blocks = initialBlocks.toMutableList()

    fun getFilesRightToLeft() = blocks
        .filter { it.type == Block.Type.FILE }
        .reversed()

    fun setEmptySpace(index: Int, lookBackToIndex: Int, size: Long) {
        var newEmptyBlockSize = size
        var newEmptyBlockStartIndex = index
        var replaceNItems = 1

        blocks.getOrNull(index - 1)
            ?.takeIf { it.type == Block.Type.EMPTY_SPACE && (index - 1) > lookBackToIndex }
            ?.also { blockBefore ->
                newEmptyBlockSize += blockBefore.size
                newEmptyBlockStartIndex -= 1
                replaceNItems++
            }
        blocks.getOrNull(index + 1)
            ?.takeIf { it.type == Block.Type.EMPTY_SPACE }
            ?.also { blockAfter ->
                newEmptyBlockSize += blockAfter.size
                replaceNItems++
            }
        blocks.replaceAt(
            newEmptyBlockStartIndex,
            replaceNItems,
            listOf(Block(null, newEmptyBlockSize, Block.Type.EMPTY_SPACE))
        )

    }

    fun setBlocks(index: Int, newBlocks: List<Block>) {
        blocks.replaceAt(index, 1, newBlocks)
    }

    fun tryMoveBlock(oldIndex: Int) {
        val block = blocks[oldIndex]
        blocks
            .take(oldIndex)
            .withIndex()
            .firstOrNull { (_, possibleTargetBlock) -> block.fitsIn(possibleTargetBlock) }
            ?.also { (targetIndex, targetBlock) ->
                setEmptySpace(oldIndex, targetIndex, block.size)
                setBlocks(targetIndex, targetBlock.replaceWith(block))
            }
    }

    fun toStrings() = blocks.toStrings()
}

class DiskMap(val blocks: List<Block>) {
    fun compactBlockLevel(): List<String> {
        val line = blocks.toStrings()
        val dataBlocks = blocks.toStrings()
            .filter { it != "." }
            .toMutableList()
        val dataBlockCount = dataBlocks.size

        return line.mapIndexed { index, char ->
            if (index >= dataBlockCount) "."
            else if (char != ".") char
            else dataBlocks.removeLast()
        }
    }

    fun compactFileLevel(): List<String> {
        val line = Line(blocks)
        line.getFilesRightToLeft().indices.forEach { index ->
            line.tryMoveBlock(index)
        }
        return line.toStrings()
    }

    companion object {
        fun of(input: String): DiskMap {
            var fileCounter = 0L
            val blocks = input
                .mapIndexed { index, s ->
                    var id: Long? = null
                    var type = Block.Type.EMPTY_SPACE
                    if (index % 2 == 0) {
                        id = fileCounter++
                        type = Block.Type.FILE
                    }
                    Block(id, s.toString().toLong(), type)
                }
            return DiskMap(blocks)
        }
    }
}

data class Block(val id: Long?, val size: Long, val type: Type) {
    fun fitsIn(targetBlock: Block): Boolean {
        return targetBlock.type == Block.Type.EMPTY_SPACE && targetBlock.size >= size
    }

    fun replaceWith(replacement: Block) = listOf(
         replacement,
         copy(size = size - replacement.size)
    ).filter { it.size > 0  }

    enum class Type{
        FILE,
        EMPTY_SPACE
    }
}