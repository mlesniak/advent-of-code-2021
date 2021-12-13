package aoc

import java.io.File

class Day13 {
    fun part1() {
        val lines = File("day13.txt").readLines()
        val points = mutableSetOf<Point>()
        for (line in lines) {
            if (line.isBlank()) {
                break
            }
            points += Point.parse(line)
        }

        val width: Int = points.maxOfOrNull { it.x + 1 } ?: throw IllegalStateException("No elements")
        val height: Int = points.maxOfOrNull { it.y + 1} ?: throw IllegalStateException("No elements")
        val grid = Array(height) { Array(width) { '.' } }
        for (p in points) {
            grid[p.y][p.x] = '#'
        }
        debug(grid)
    }
}

