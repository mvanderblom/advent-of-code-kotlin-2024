package dev.vanderblom.aoc

import java.math.BigInteger
import java.nio.file.Path
import java.security.MessageDigest
import java.util.function.Function

fun readInput(name: String): List<String> {
    val file = Path.of("src", "test", "resources", "input", "$name.txt").toFile()
    file.createNewFile()
    return file.readLines()
}

fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')
fun String.withoutLast(n: Int) = this.substring(0, this.length - n)

fun <T> T?.showMe(prefix: String? = null) = this.also {
    if (prefix != null) {
        print(prefix)
        print(" ")
    }
    println(this)
}

fun <T> List<T>.showMe(prefix: String? = null, func: (input: T) -> Any): List<T> {
    return this
        .also {
            if (prefix != null) {
                print(prefix)
                print(" ")
            }
            println(it.map(func))
        }
}

fun Int?.toDayName(): String {
    require(this != null && this != 0) { "Please set the day number and run again" }
    return "Day" + this.toString().padStart(2, '0')
}

fun List<String>.splitBy(separator: String = " "): List<List<Int>> = this.map{ it.split(separator).map{s -> s.toInt()} }
fun <T> List<String>.splitBy(separator: String = " ", converter: Function<String, T>): List<List<T>> = this.map{ it.split(separator).map(converter::apply) }
operator fun List<String>.get(index: Pair<Int, Int>): Char {
    val (row, col) = index
    return this[row][col]
}

fun <T> List<T>.withoutElementAt(index: Int) = this.subList(0, index) + this.subList(index + 1, this.size)
