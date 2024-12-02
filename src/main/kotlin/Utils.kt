package dev.vanderblom.aoc

import java.io.File
import java.math.BigInteger
import java.nio.file.Path
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String): List<String> {
    val file = Path.of("src", "main", "resources", "input", "$name.txt").toFile()
    file.createNewFile()
    return file.readLines()
}

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

infix fun Any.isEqualTo(expected: Any?): Boolean {
    if (expected == null) {
        error("Expectation not set... Actual was $this")
    }

    if (!this.equals(expected)) {
        error("Expected $expected, but got $this")
    }
    println(this)
    return true
}

fun <T> T.showMe(prefix: String? = null): T {
    if(prefix != null) {
        print(prefix)
        print(" ")
    }
    println(this)

    return this
}

fun <T> List<T>.showMe(func: (input: T) -> Any): List<T> {
    this.forEach {
        println(func(it))
    }
    return this
}

fun Int.toDayName(): String = "Day" + this.toString().padStart(2, '0')