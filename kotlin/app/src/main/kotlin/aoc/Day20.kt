package aoc

import java.io.File

class Day20 {
    fun part1() {
        val file = File("day20.txt").readLines().iterator()
        val algorithm = file.next()
        file.next()

        val pixels = mutableSetOf<Vector2>()
        var y = 0
        while (file.hasNext()) {
            val line = file.next()
            for (x in line.indices) {
                val c = line[x]
                if (c == '#') {
                    pixels += Vector2(x, y)
                }
            }

            y++
        }
        pixels.debug()
    }
}