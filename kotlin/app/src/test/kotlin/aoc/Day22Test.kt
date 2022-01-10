package aoc

import aoc.Day22.Step
import aoc.Day22.Step.Switch.OFF
import aoc.Day22.Step.Switch.ON
import org.junit.Test
import kotlin.test.assertEquals

internal class Day22Test {
    private val sut = Day22()

    @Test
    fun `basic setup`() {
        val steps = mutableListOf(
            Step(ON, 1..10, 1..10, 1..10)
        )

        val (expected, current) = sut.compute(steps)
        assertEquals(expected, current)
    }

    @Test
    fun `basic setup with equal ons`() {
        val steps = mutableListOf(
            Step(ON, 1..10, 1..10, 1..10),
            Step(ON, 1..10, 1..10, 1..10),
        )

        val (expected, current) = sut.compute(steps)
        assertEquals(expected, current)
    }

    @Test
    fun `basic setup with overlapping ons`() {
        val steps = mutableListOf(
            Step(ON, 1..10, 1..10, 1..10),
            Step(ON, 5..10, 5..10, 5..10),
        )

        val (expected, current) = sut.compute(steps)
        assertEquals(expected, current)
    }

    @Test
    fun `basic setup with one one and one off, non-overlapping`() {
        val steps = mutableListOf(
            Step(ON, 1..10, 1..10, 1..10),
            Step(OFF, 11..20, 11..20, 11..20),
        )

        val (expected, current) = sut.compute(steps)
        assertEquals(expected, current)
    }

    @Test
    fun `basic setup with one one and one off, complete-overlapping`() {
        val steps = mutableListOf(
            Step(ON, 1..10, 1..10, 1..10),
            Step(OFF, 1..10, 1..10, 1..10),
        )

        val (expected, current) = sut.compute(steps)
        assertEquals(expected, current)
    }

    @Test
    fun `two offs, no overlapping`() {
        val steps = mutableListOf(
            Step(ON, 1..10, 1..10, 1..10),
            Step(OFF, 1..10, 1..10, 1..4),
            Step(OFF, 1..10, 1..10, 5..10),
        )

        val (expected, current) = sut.compute(steps)
        assertEquals(expected, current)
    }

    @Test
    fun `merge two offs`() {
        val steps = mutableListOf(
            Step(OFF, 1..10, 1..10, 1..10),
            Step(OFF, 1..10, 1..10, 5..10),
        )

        val merged = sut.merge(steps[0], steps[1])
        merged.debug()
    }

    @Test
    fun `do not merge two non-verlapping offs`() {
        val steps = mutableListOf(
            Step(OFF, 1..10, 1..10, 1..4),
            Step(OFF, 1..10, 1..10, 5..10),
        )

        val merged = sut.merge(steps[0], steps[1])
        merged.debug()
    }

    @Test
    fun `merge overlapping ons`() {
        val steps = mutableListOf(
            Step(ON, 1..10, 1..10, 1..10),
            Step(ON, 5..10, 5..10, 5..10),
        )

        val merged = sut.merge(steps[0], steps[1])
        merged.debug()
        sut.computeUsingVector(merged).debug()
    }


    @Test
    // Later: split offs, remove ons
    // Later: normal procedure, then remove offs
    fun `split initial ons, remove offs for minimum set`() {
        val steps = mutableListOf(
            Step(ON, 1..10, 1..10, 1..10),
            Step(ON, 1..10, 1..10, 5..10),
        )
        separator(description = "Current")
        println(sut.compute(steps))

        val merged = sut.merge(steps[0], steps[1])
        merged.debug()
        separator(description = "After cleanup")
        println(sut.compute(merged))
    }

}