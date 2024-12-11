package dev.vanderblom.aoc

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
abstract class AbstractDay {
    protected val exampleInput: List<String>
    protected val actualInput: List<String>

    init {
        val year = Regex("y(\\d{4})")
            .find(this.javaClass.packageName)
            ?.groups?.get(1)?.value?.toInt()

        requireNotNull(year) { "Year couldn't be inferred from package name. Please put the testclass in a (sub)package com.app.y{year}.TestClass"}

        val simpleName = this::class.java.simpleName
        val dayNumber = simpleName.replace("Day", "").toInt()
        if(dayNumber != 0) {
            val dayName = dayNumber.toDayName()
            exampleInput = readInput("${dayName}-1-example", year)
            actualInput = readInput("${dayName}-2-actual", year)
        } else {
            exampleInput = emptyList()
            actualInput = emptyList()
        }
    }
}