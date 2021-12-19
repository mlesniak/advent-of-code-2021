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
                dots += Point.of(line)
            } else {
                folds += Fold.parse(line)
            }
        }

        val width: Int = dots.maxOfOrNull { it.x + 1 } ?: throw IllegalStateException("No elements")
        val height: Int = dots.maxOfOrNull { it.y + 1 } ?: throw IllegalStateException("No elements")
        var grid: Grid<Char> = createGrid(width, height)
        for (p in dots) {
            grid[p.y][p.x] = '#'
        }
        // grid.debug()
        // folds.debug()

        for (fold in folds) {
            val result = fold(grid, fold)
            grid = result
        }
        grid.debug()

        // val result = fold(grid, folds[0])
        // // result.debug()
        // var count = 0
        // result.forEach { _, _, v ->
        //     if (v == '#') {
        //         count++
        //     }
        // }
        // println("Day13/Part 1 = $count")
    }

    private fun createGrid(width: Int, height: Int) = Array(height) { Array(width) { '.' } }

    private fun fold(grid: Grid<Char>, fold: Day13.Fold): Grid<Char> {
        lateinit var res: Grid<Char>
        when (fold.type) {
            FoldType.HORIZONTAL -> {
                res = createGrid(grid.width(), grid.height() / 2)
                grid.forEach { x, y, v ->
                    if (v == '.') {
                        return@forEach
                    }
                    if (y < fold.position) {
                        res[y][x] = v
                    } else {
                        // println("x=$x, y=$y")
                        val delta = y - fold.position
                        res[fold.position - delta][x] = v
                    }
                }
            }
            FoldType.VERTICAL -> {
                res = createGrid(grid.width() / 2, grid.height())
                grid.forEach { x, y, v ->
                    if (v == '.') {
                        return@forEach
                    }
                    if (x < fold.position) {
                        res[y][x] = v
                    } else {
                        val delta = x - fold.position
                        res[y][fold.position - delta] = v
                    }
                }

            }
        }

        return res
    }
}

