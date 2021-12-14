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

        val maxSteps = 10
        var state = start
        println()
        state.debug()
        for (step in 1..maxSteps) {
            // println("=== Step $step")
            state = step(rules, state)
            // state.debug()
        }

        val sizes = state.groupBy { it }.map { it.value.size }
        val result = sizes.maxOrNull()!! - sizes.minOrNull()!!
        println("Part 1 = $result")
    }

    private fun step(rules: Map<String, String>, state: String): String {
        val sb = StringBuilder()
        for (i in 0..state.length - 2) {
            val pattern = state.substring(i, i + 2)
            // pattern.debug()
            val match = rules[pattern]
            sb.append("${pattern[0]}$match")
            // sb.insert(i+1, match)
        }
        sb.append(state[state.length - 1])
        return sb.toString()
    }

    fun part2() {
        val input = File("day14.txt").readLines()
        val start = input[0]
        val rules = mutableMapOf<String, String>()
        for (lineIdx in 2 until input.size) {
            val parts = input[lineIdx].split(" -> ")
            rules[parts[0]] = parts[1]
        }
        // start.debug()
        // rules.debug()

        var state = toMap(start)
        // state.debug()

        val maxSteps = 40
        for (step in 1..maxSteps) {
            println("\n===Step $step")
            state = stepState(rules, state)
            // state.debug()
            // state.values.sum().debug("Summed length")
        }

        // println("Expected")
        //     NBCCNBBBCBHCB
        // toMap("NBBBCNCCNBBNBNBBCHBHHBCHB").debug()

        // state.debug()
        // +1 due to pair-based approach
        // state.values.sum().debug("Summed length")
        val counts = mutableMapOf<Char, Long>()
        for (entry in state) {
            counts[entry.key[0]] = counts.getOrDefault(entry.key[0], 0) + entry.value
            counts[entry.key[1]] = counts.getOrDefault(entry.key[1], 0) + entry.value
        }

        // println()
        // counts.debug()
        val sizes = counts.values.map { it / 2 }
        val result = sizes.maxOrNull()!! - sizes.minOrNull()!! + 1
        println("\n\nPart 2 = $result")
    }

    private fun toMap(start: String): MutableMap<String, Long> {
        val state = mutableMapOf<String, Long>()
        for (idx in 0..start.length - 2) {
            state[start.substring(idx, idx + 2)] = (state[start.substring(idx, idx + 2)] ?: 0) + 1
        }
        return state
    }

    private fun stepState(rules: MutableMap<String, String>, state: MutableMap<String, Long>): MutableMap<String, Long> {
        val res = mutableMapOf<String, Long>()

        state.keys.forEach { pair ->
            // repeat(state[pair]!!.toInt()) {
            // println("PAIR=$pair")
            val match = rules[pair]
            // println("  MATCH=$match")
            // println("  Creating ${pair[0]}$match")
            // println("  Creating $match${pair[1]}")
            res["${pair[0]}$match"] = (res["${pair[0]}$match"] ?: 0) + state[pair]!!
            res["$match${pair[1]}"] = (res["$match${pair[1]}"] ?: 0) + state[pair]!!
            // }
        }

        return res
    }
}
