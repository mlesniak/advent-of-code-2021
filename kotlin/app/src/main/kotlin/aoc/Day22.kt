package aoc

import java.io.File

class Day22 {

    data class Step(val switch: Switch, val x: IntRange, val y: IntRange, val z: IntRange) {
        enum class Switch {
            ON,
            OFF,
        }

        companion object {
            private fun toRange(s: String): IntRange {
                val parts = s.split("..")
                return IntRange(parts[0].toInt(), parts[1].toInt())
            }

            fun from(s: String): Step {
                val basicParts = s.split(" ")
                val switch = if (basicParts[0] == "on") Switch.ON else Switch.OFF
                val ranges = basicParts[1].split(",").map { it.split("=")[1] }

                return Step(
                    switch = switch,
                    x = toRange(ranges[0]),
                    y = toRange(ranges[1]),
                    z = toRange(ranges[2]),
                )
            }
        }
    }

    fun part1() {
        val steps = File("day22.txt").readLines().map(Step.Companion::from)
        steps.debug()
    }
}