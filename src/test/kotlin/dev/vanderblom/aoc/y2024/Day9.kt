package dev.vanderblom.aoc.y2024

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
    println(toLine().joinToString(""))
}

fun List<Block>.toLine() = flatMap { block ->
    (1..block.size).map {
        if (block.type == Block.Type.FILE) {
            block.id.toString()
        } else {
            "."
        }
    }
}

fun List<Block>.size() = this.sumOf { it.size }


class DiskMap(val blocks: List<Block>) {
    fun compactBlockLevel(): List<String> {
        val line = blocks.toLine()
        val dataBlocks = blocks.toLine()
            .filter { it != "." }
            .toMutableList()
        val dataBlockCount = dataBlocks.size

        return line.mapIndexed { index, char ->
            if (index >= dataBlockCount) "."
            else if (char != ".") char
            else dataBlocks.removeLast()
        }
    }

    fun <T> MutableList<T>.replaceAt(index: Int, n: Int=1, vararg newItems: T) {
        val tempList = this.subList(0, index) + newItems + this.subList(index + n, this.size)
        this.clear()
        this.addAll(tempList)
    }

    fun compactFileLevel(): List<String> {
        val dataBlocksReversed = blocks
            .filter { it.type == Block.Type.FILE}
            .reversed()
            .toMutableList()

        val processedBlocks = mutableSetOf<Block>()

        val target = blocks.toMutableList()
        dataBlocksReversed.forEach{ blockToPlace ->
            if(processedBlocks.contains(blockToPlace)) return@forEach
            processedBlocks.add(blockToPlace)

            val oldIndex = target.indexOf(blockToPlace)

            target
                .take(oldIndex+1)
                .mapIndexed { index, targetBlock -> index to targetBlock }
                .firstOrNull{ (_, targetBlock) -> targetBlock.type == Block.Type.EMPTY_SPACE && targetBlock.size >= blockToPlace.size }
                ?.also {(targetIndex, targetBlock) ->

                    var newEmptyBlockSize = blockToPlace.size
                    var newEmptyBlockStartIndex = oldIndex
                    var replaceNItems = 1
                    target.getOrNull(oldIndex - 1)
                        ?.takeIf { it.type == Block.Type.EMPTY_SPACE && (oldIndex-1) != targetIndex }
                        ?.also { blockBefore ->
                            newEmptyBlockSize += blockBefore.size
                            newEmptyBlockStartIndex -= 1
                            replaceNItems++
                        }
                    target.getOrNull(oldIndex + 1)
                        ?.takeIf { it.type == Block.Type.EMPTY_SPACE }
                        ?.also { blockAfter ->
                            newEmptyBlockSize += blockAfter.size
                            replaceNItems++
                        }
                    target.replaceAt(newEmptyBlockStartIndex, replaceNItems, Block(null, newEmptyBlockSize, Block.Type.EMPTY_SPACE))

                    if(targetBlock.size == blockToPlace.size) {
                        target.replaceAt(targetIndex, 1, blockToPlace)
                    } else {
                        target.replaceAt(
                            targetIndex,
                            1,
                            blockToPlace,
                            targetBlock.copy(size = targetBlock.size - blockToPlace.size)
                        )
                    }
                }
        }
        return target.toLine()
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
    enum class Type{
        FILE,
        EMPTY_SPACE
    }
}