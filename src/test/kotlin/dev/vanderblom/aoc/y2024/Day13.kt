package dev.vanderblom.aoc.y2024

import dev.vanderblom.aoc.AbstractDay
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day13 : AbstractDay() {

    @Test
    @Order(1)
    fun `part one - example`() {
        assertThat(partOne(exampleInput))
            .isEqualTo(480L)
    }

    @Test
    @Order(2)
    fun `part one - actual`() {
        assertThat(partOne(actualInput))
            .isEqualTo(30413L)
    }

    @Test
    @Order(3)
    fun `part two - example`() {
        assertThat(partTwo(exampleInput))
            .isEqualTo(875318608908L)
    }

    @Test
    @Order(4)
    fun `part two - actual`() {
        assertThat(partTwo(actualInput))
            .isEqualTo(92827349540204L) // 374211323724927104 too high;
    }


    private fun partOne(input: List<String>): Long {
        val machines = getMachines(input)
        return solve(machines)
    }


    private fun partTwo(input: List<String>): Long {
        val machines = getMachines(input, true)
        return solve(machines)
    }

    private fun solve(machines: List<Machine>): Long {
        return machines.fold(0.0) { acc, machine ->
            val solution = solveLinearEquation(
                machine.buttonA.first,
                machine.buttonB.first,
                machine.buttonA.second,
                machine.buttonB.second,
                machine.prize.first,
                machine.prize.second
            )
            if (solution.first % 1.0 == 0.0 && solution.second % 1.0 == 0.0)
                acc + solution.first * 3 + solution.second
            else
                acc
        }.toLong()
    }


    val xyRegex = Regex(""".*X[+=](\d+).*Y[+=](\d+).*""")
    data class Machine(val buttonA: Pair<Long, Long>, val buttonB: Pair<Long, Long>, val prize: Pair<Long, Long>)

    private fun getMachines(input: List<String>, useConversionError: Boolean = false): List<Machine> {
        val machines = input
            .filter { it.isNotBlank() }
            .map {
                val data = xyRegex.find(it)!!
                data.groups[1]!!.value.toLong() to data.groups[2]!!.value.toLong()
            }
            .windowed(3, 3)
            .map { (buttonA, buttonB, prize) ->
                val actualPrize = if(useConversionError) prize.first + 10000000000000L to prize.second + 10000000000000L else prize
                Machine(buttonA, buttonB, actualPrize)
            }
        return machines
    }

    private fun solveLinearEquation(
        a11: Long,
        a12: Long,
        a21: Long,
        a22: Long,
        k1: Long,
        k2: Long
    ): Pair<Double, Double> {
        val det = (a11 * a22 - a21 * a12).toDouble()
        return (k1 * a22 - k2 * a12) / det to
               (k2 * a11 - k1 * a21) / det
    }
}