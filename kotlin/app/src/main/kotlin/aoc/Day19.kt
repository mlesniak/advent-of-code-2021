package aoc

import java.io.File

class Day19 {
    data class Scanner(val id: Int, val points: List<Point>)

    fun part1() {
        val scanner = mutableListOf<Scanner>()
        val iter = File("day19.txt").readLines().iterator()
        while (iter.hasNext()) {
            scanner += scan(iter)
        }

        scanner.debug()
    }

    private fun scan(iter: Iterator<String>): Scanner {
        val id = iter.next().split(" ")[2].toInt()
        val points = mutableListOf<Point>()
        while (true) {
            if (!iter.hasNext()) {
                break
            }
            val line = iter.next()
            if (line.isEmpty()) {
                break
            }
            points += Point.of(line)
        }

        return Scanner(id, points)
    }
}
