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

        // Extend by one in each direction
        var current = initialGrid
        current = extendGrid(current, '.')
        // Simulate a single step
        val maxSteps = 50
        for (step in 0 until maxSteps) {
            separator()
            println("STEP: $step")

            val tmp = mutableListOf<CharArray>()
            current.forEach { line ->
               tmp += line.clone()
            }
            // tmp.forEach { line -> println(line) }

            for (y in current.indices) {
                for (x in current[y].indices) {
                    // println("$x/$y")
                    var sb = ""
                    for (dy in arrayOf(-1, 0, 1)) {
                        for (dx in arrayOf(-1, 0, 1)) {
                            val xx = x + dx
                            val yy = y + dy
                            // println("  $xx/$yy")
                            if (current.inside(xx, yy)) {
                                val c = current[yy][xx]
                                sb += if (c == '#') "1" else "0"
                            } else {
                                // Testcase
                                sb += "0"
                                // Realcase
                                // sb += if (step % 2 == 0) "0" else "1"
                                // println("    outside")
                            }
                        }
                    }

                    val newValue = algorithm[sb.toInt(2)]
                    // println("  BINARY $sb -> $newValue")
                    tmp[y][x] = newValue
                }
            }

            println("FINISHED")
            // tmp.forEach { line -> println(line) }

            // Testcase
            val nextBackgroundChar = '.'
            // Realcase
            // val nextBackgroundChar = if (step % 2 == 1) '.' else '#'
            current = extendGrid(tmp, nextBackgroundChar)
        }

        var s = 0
        current.forEach { line ->
            line.forEach { c ->
                if (c == '#') s++
            }
        }
        println(s)
    }

    private fun List<CharArray>.inside(x: Int, y: Int): Boolean =
        (x >= 0 && x < get(0).size && y >= 0 && y < size)

    private fun extendGrid(source: List<CharArray>, background: Char): MutableList<CharArray> {
        val current = mutableListOf<CharArray>()
        val width = source[0].size + 2
        current += CharArray(width) { background }
        source.forEach { line ->
            current += CharArray(width) { idx ->
                when (idx) {
                    0 -> background
                    width - 1 -> background
                    else -> line[idx - 1]
                }
            }
        }
        current += CharArray(width) { background }
        return current
    }
}

