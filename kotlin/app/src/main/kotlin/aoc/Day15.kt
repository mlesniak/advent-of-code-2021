package aoc

class Day15 {
    data class Path(val path: List<Point>, val risk: Int)

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
        val goal = Point(map.width() - 1, map.height() - 1)

        while (paths.isNotEmpty()) {
            val current = paths.removeAt(paths.size - 1)

            val last = current.path.last()

            // println("= Examining $current")
            val nexts = mutableListOf<Path>()
            map.neighbors(last.x, last.y) { nx, ny, risk ->
                if (Point(nx, ny) == goal) {
                    println("*** Found path $current")

                    // Prune tree
                    minRisk = current.risk + risk
                    val before = paths.size
                    paths.removeIf { it.risk >= minRisk}
                    println("- Pruned ${before - paths.size}")
                    return@neighbors
                }


                if (current.risk + risk >= minRisk) {
                    return@neighbors
                }

                val next = Point(nx, ny)
                if (current.path.contains(next)) {
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
