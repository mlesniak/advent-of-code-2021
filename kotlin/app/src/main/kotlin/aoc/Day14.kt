package aoc

import java.io.File

class Day14 {
    fun part1() {
        val input = File("day14.txt").readLines()
        val start = input[0]
        val rules = mutableMapOf<String, String>()
        for (lineIdx in 2 until input.size) {
            val parts = input[lineIdx].split(" -> ")
            rules[parts[0]] = parts[1]
        }
        // start.debug()
        rules.debug()

        val maxSteps = 4
        var state = start
        println()
        state.debug()
        for (step in 1..maxSteps) {
            println("=== Step $step")
            state = step(rules, state)
            state.debug()
        }

        println("NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB")
    }

    private fun step(rules: Map<String, String>, state: String): String {
        val sb = StringBuilder()
        for (i in 0..state.length-2) {
            val pattern = state.substring(i, i+2)
            // pattern.debug()
            val match = rules[pattern]
            sb.append("${pattern[0]}$match")
            // sb.insert(i+1, match)
        }
        sb.append(state[state.length-1])
        return sb.toString()
    }
}
