package aoc

import aoc.Day22.Step
import aoc.Day22.Step.Switch.ON
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

internal class Day22Test {
    private val sut = Day22()

    @Test
    fun `file based test`() {
        val input = File("../day22.txt").readLines().map(Step.Companion::from)
        validateComputation(*input.toTypedArray())
    }

    @Test
    fun `check complex on case`() {
        validateComputation(
            Step(ON, 1..3, 0..3, 0..2),
            Step(ON, 0..2, 1..2, 1..3),
        )
    }

    @Test
    fun `y inside bug`() {
        val intersec = sut.split(0..3, 1..2)
        println(intersec)
        val message = (0..3).intersect(-10..-2)
        println(message)
    }



    @Test
    fun `check trivial computation`() {
        validateComputation(
            Step(ON, 1..4, 1..4, 1..4),
            Step(ON, 5..10, 5..10, 5..10)
        )
    }

    @Test
    fun `check intersected computation`() {
        validateComputation(
            Step(ON, 1..5, 1..5, 1..5),
            Step(ON, 5..10, 5..10, 5..10)
        )
    }

    @Test
    fun `intersection half`() {
        showIntersection(
            Step(ON, 1..10, 1..10, 1..10),
            Step(ON, 5..10, 5..10, 5..10))
    }

    @Test
    fun `intersection full`() {
        showIntersection(
            Step(ON, 1..10, 1..10, 1..10),
            Step(ON, 1..10, 1..10, 1..10))
    }

    @Test
    fun `intersection single line`() {
        showIntersection(
            Step(ON, 1..5, 1..5, 1..5),
            Step(ON, 5..10, 5..10, 5..10))
    }

    @Test
    fun `intersection none`() {
        showIntersection(
            Step(ON, 1..5, 1..5, 1..5),
            Step(ON, 6..10, 6..10, 6..10))
    }

    private fun validateComputation(vararg steps: Step) {
        val input = listOf(*steps)
        input.debug("input")
        val expected = sut.computeUsingVector(input)

        val part2 = sut.compute(input)
        expected.debug("expected")
        part2.debug("computed")
        assertEquals(expected, part2)
    }

    private fun showIntersection(p1: Step, p2: Step) {
        val input = listOf(p1, p2)
        input.debug("input")
        val intersection = sut.intersection(input[0], input[1])
        if (intersection == null) {
            println("No intersection")
            return
        }
        intersection.debug("intersection")
    }
}