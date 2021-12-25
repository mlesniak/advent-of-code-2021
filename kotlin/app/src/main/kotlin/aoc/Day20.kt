package aoc

import java.io.File

class Day20 {
    fun part1() {
        val file = File("day20.txt").readLines().iterator()
        val algorithm = file.next()
        file.next()

        // alle koordinaten aus ursprungsbild
        var pixels = mutableSetOf<Vector2>()
        var row = 0
        while (file.hasNext()) {
            val line = file.next()
            for (x in line.indices) {
                val c = line[x]
                if (c == '#') {
                    pixels += Vector2(x, row)
                }
            }

            row++
        }
        pixels.render()

        for (step in 1..2) {
            separator()
            println("STEP $step")
            val next = mutableSetOf<Vector2>()
            val x1 = pixels.minOf { it.x } - 4
            val y1 = pixels.minOf { it.y } - 4
            val x2 = pixels.maxOf { it.x } + 4
            val y2 = pixels.maxOf { it.y } + 4
            for (y in y1..y2) {
                for (x in x1..x2) {
                    // println("Examining $x/$y")
                    val sb = StringBuilder()

                    for (dy in arrayOf(-1, 0, 1)) {
                        for (dx in arrayOf(-1, 0, 1)) {
                            val v = Vector2(x + dx, y + dy)
                            // wenn im ursprungsbild
                            if (v in pixels) {
                                sb.append("1")
                            } else {
                                sb.append("0")
                            }
                            // sonst abhaengig von schritt modulo an oder aus
                        }
                    }

                    val index = sb.toString().toInt(2)
                    if (algorithm[index] == '#') {
                        next += Vector2(x, y)
                    }
                }
            }
            next.render()
            // ursprungsbild erweitern
            pixels = next
        }

        println("Pixels=${pixels.size}")
    }
}

fun Set<Vector2>.render() {
    val x1 = minOf { it.x } - 4
    val y1 = minOf { it.y } - 4
    val x2 = maxOf { it.x } + 4
    val y2 = maxOf { it.y } + 4

    for (y in y1..y2) {
        for (x in x1..x2) {
            if (Vector2(x, y) in this) {
                print("#")
            } else {
                print(".")
            }
        }
        println()
    }
}