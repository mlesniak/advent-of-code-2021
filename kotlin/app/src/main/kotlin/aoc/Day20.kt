package aoc

import java.io.File

class Day20 {
    fun part1() {
        val lines = File("day20.txt").readLines().iterator()
        val algorithm = lines.next()
        lines.next()

        val initialGrid = mutableListOf<CharArray>()
        while (lines.hasNext()) {
            val line = lines.next().toCharArray()
            initialGrid += line
        }
        initialGrid.forEach { line -> println(line) }

        println()
        // Extend by one in each direction
        var currentBackground = '.'
        var current = mutableListOf<CharArray>()
        val width = initialGrid[0].size + 2
        current += CharArray(width) { currentBackground }
        initialGrid.forEach { line ->
            current += CharArray(width) { idx ->
                when (idx) {
                    0 -> currentBackground
                    width-1 -> currentBackground
                    else -> line[idx-1]
                }
            }
        }
        current += CharArray(width) { currentBackground }
        current.forEach { line -> println(line) }
    }
}

