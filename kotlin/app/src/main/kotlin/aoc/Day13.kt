package aoc

import java.io.File

class Day13 {
    enum class FoldType {
        HORIZONTAL,
        VERTICAL
    }

    data class Fold(val position: Int, val type: FoldType) {
        companion object {
            fun parse(line: String): Fold {
                val fold = line.split(" ")[2].split("=")
                return Fold(
                    position = fold[1].toInt(),
                    type = if (fold[0] == "y") FoldType.HORIZONTAL else FoldType.VERTICAL
                )
            }
        }
    }

    fun part1() {
        val dots = mutableSetOf<Point>()
        val folds = mutableListOf<Fold>()

        val lines = File("day13.txt").readLines()
        var parseDots = true
        for (line in lines) {
            if (line.isBlank()) {
                parseDots = false
                continue
            }
            if (parseDots) {
                dots += Point.parse(line)
            } else {
                folds += Fold.parse(line)
            }
        }

        val width: Int = dots.maxOfOrNull { it.x + 1 } ?: throw IllegalStateException("No elements")
        val height: Int = dots.maxOfOrNull { it.y + 1 } ?: throw IllegalStateException("No elements")
        val grid = Array(height) { Array(width) { '.' } }
        for (p in dots) {
            grid[p.y][p.x] = '#'
        }
        debug(grid)
        debug(folds)
    }
}

