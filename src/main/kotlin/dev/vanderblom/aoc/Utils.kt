package dev.vanderblom.aoc.dev.vanderblom.aoc.util

import java.math.BigInteger
import java.nio.file.Path
import java.security.MessageDigest
import java.util.function.Function

fun readInput(name: String): List<String> {
    val file = Path.of("src", "main", "resources", "input", "$name.txt").toFile()
    file.createNewFile()
    return file.readLines()
}

fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

infix fun Any.isEqualTo(expected: Any?): Boolean {
    if (expected == null) {
        error("Expectation not set... Actual was $this")
    }

    if (this != expected) {
        error("Expected $expected, but got $this")
    }
    println(this)
    return true
}

fun <T> T.showMe(prefix: String? = null) = prefix
    ?.also {
        print(prefix)
        print(" ")
    }
    .let {
        println(this)
        this
    }

fun <T> List<T>.showMe(prefix: String? = null, func: (input: T) -> Any) = onEach {
    func(it).showMe(prefix)
}

fun Int.toDayName(): String {
    require(this != 0) { "Please set the day number and run again" }
    return "Day" + this.toString().padStart(2, '0')
}

fun List<String>.splitBy(separator: String = " "): List<List<Int>> = this.map{ it.split(separator).map{s -> s.toInt()} }
fun <T> List<String>.splitBy(separator: String = " ", converter: Function<String, T>): List<List<T>> = this.map{ it.split(separator).map(converter::apply) }
fun <T> List<T>.withoutElementAt(index: Int) = this.subList(0, index) + this.subList(index + 1, this.size)