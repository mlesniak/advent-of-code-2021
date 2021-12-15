package aoc

import kotlin.random.Random

class Day15 {
    data class Path(val path: List<Point>, val risk: Int) {
        val points: Set<Point> = path.toSet()

        fun contains(p: Point): Boolean = p in points
    }

    fun part1() {
        val map: Grid<Int> = readGrid("day15.txt")
        // map.debug()

        val paths = mutableListOf<Path>()
        val start = Path(
            listOf(Point(0, 0)),
            0
        )
        paths += start

        var minRisk = Int.MAX_VALUE
        var sum = 0
        for (x in 0 until map.width()) {
            sum += map[0][x]
        }
        for (y in 0 until map.height()) {
            sum += map[y][map.width() - 1]
        }
        minRisk = sum

        val goal = Point(map.width() - 1, map.height() - 1)

        val rand = Random(0)

        while (paths.isNotEmpty()) {
            val current = paths.removeAt(paths.size - 1)
            if (rand.nextDouble() < 0.001) {
                val avg = paths.map { it.risk }.sum() / paths.size
                println("${paths.size} and minRisk=$minRisk, avg=$avg")
                println("${paths.map{it.risk}}")
            }

            val last = current.path.last()

            // println("= Examining $current")
            val nexts = mutableListOf<Path>()
            map.neighbors(last.x, last.y) { nx, ny, risk ->
                if (Point(nx, ny) == goal) {
                    println("*** Found path $current")

                    // Prune tree
                    minRisk = current.risk + risk
                    val before = paths.size
                    paths.removeIf { it.risk >= minRisk }
                    println("- Pruned ${before - paths.size}")
                    return@neighbors
                }


                if (current.risk + risk >= minRisk) {
                    return@neighbors
                }

                val next = Point(nx, ny)
                if (current.contains(next)) {
                    return@neighbors
                }
                // println("-> $nx/$ny with $risk")
                val nextPath = Path(
                    current.path + next,
                    current.risk + risk
                )
                nexts += nextPath
            }

            // Sort by lowest risk
            nexts.sortBy { it.risk }
            nexts.reverse()
            paths += nexts



            // readLine()
        }

        println("minRisk=$minRisk")
    }
}
