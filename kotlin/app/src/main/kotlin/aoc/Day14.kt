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
        start.debug()
        rules.debug()
    }
}
