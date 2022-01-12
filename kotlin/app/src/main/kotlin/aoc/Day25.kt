package aoc

import java.io.File

class Day25 {
    data class SeaFloor(val width: Int, val height: Int, val floor: Map<Vector2, Char>) {
        fun step(): SeaFloor {
            val next = mutableMapOf<Vector2, Char>()
            val moved = mutableSetOf<Vector2>()
            // >
            floor
                .filter { it.value == '>' }
                .forEach { (cell, c) ->
                    val nextPos = Vector2((cell.x+1) % width, cell.y)
                    if (nextPos in floor) {
                        // Occupied, leave it.
                        next[cell] = c
                    } else {
                        // Advance
                        next[nextPos] = c
                        moved += cell
                    }
                }

            // v
            floor
                .filter { it.value == 'v' }
                .forEach { (cell, c) ->
                    val nextPos = Vector2(cell.x, (cell.y + 1) % height)
                    if ((nextPos in floor && nextPos !in moved) || nextPos in next) {
                        // Occupied, leave it.
                        next[cell] = c
                    } else {
                        // Advance
                        next[nextPos] = c
                    }
                }

            return this.copy(floor = next)
        }

        fun debug() {
            for (i in 0 until height) {
                for (j in 0 until width) {
                    print(floor[Vector2(j, i)] ?: '.')
                }
                println()
            }
        }
    }

    fun part1() {
        val lines = File("day25.txt").readLines()
        val width = lines[0].length
        val height = lines.size
        val floor = mutableMapOf<Vector2, Char>()
        for (i in lines.indices) {
            for (j in lines[i].indices) {
                if (lines[i][j] == '.') {
                    continue
                }
                floor[Vector2(j, i)] = lines[i][j]
            }
        }
        // floor.filter { it.value != '.' }.debug()

        var cur = SeaFloor(width, height, floor)
        var step = 0
        while (true) {
            step++
            val tmp = cur.step()
            if (tmp == cur) {
                break
            }
            cur = tmp
        }
        println(step)
    }
}