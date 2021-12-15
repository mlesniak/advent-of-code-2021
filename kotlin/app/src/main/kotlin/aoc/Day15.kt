package aoc

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

        // Greedy algorithm.
        var current = start
        while (true) {
            val tip = current.path.last()
            if (tip == goal) {
                break
            }

            var nextPoint = Point(0,0)
            var minRisk = Int.MAX_VALUE
            map.neighbors(tip.x, tip.y) { nx, ny, risk ->
                if (Point(nx, ny) in current.path) {
                    return@neighbors
                }
                if (risk < minRisk) {
                    nextPoint = Point(nx, ny)
                    minRisk = risk
                }
            }
            if (minRisk == Int.MAX_VALUE) {
                println("Np path for...")
                current.path.forEach { println("  $it") }
                throw IllegalStateException("no path continuation")
            }

            current = Path(
                path = current.path + nextPoint,
                risk = current.risk + minRisk
            )
        }

        println(current)


        // val rand = Random(0)
        //
        // while (paths.isNotEmpty()) {
        //     // println("\n\nIteration")
        //     // paths.forEach { println("  * $it") }
        //
        //     val current = paths.removeAt(paths.size - 1)
        //     if (rand.nextDouble() < 0.001) {
        //         val avg = paths.map { it.risk }.sum() / paths.size
        //         println("${paths.size} and minRisk=$minRisk, avg=$avg")
        //         // println("${paths.map{it.risk}}")
        //     }
        //
        //     val last = current.path.last()
        //
        //     // println("= Examining $current")
        //     val nexts = mutableListOf<Path>()
        //     map.neighbors(last.x, last.y) { nx, ny, risk ->
        //         if (Point(nx, ny) == goal) {
        //             println("*** Found path $current.risk")
        //
        //             // Prune tree
        //             minRisk = current.risk + risk
        //             val before = paths.size
        //             paths.removeIf { it.risk >= minRisk }
        //             println("- Pruned ${before - paths.size}")
        //             return@neighbors
        //         }
        //
        //
        //         if (current.risk + risk >= minRisk) {
        //             return@neighbors
        //         }
        //
        //         val next = Point(nx, ny)
        //         if (current.contains(next)) {
        //             return@neighbors
        //         }
        //         // println("-> $nx/$ny with $risk")
        //         val nextPath = Path(
        //             current.path + next,
        //             current.risk + risk
        //         )
        //         nexts += nextPath
        //     }
        //     if (nexts.isEmpty()) {
        //         continue
        //     }
        //
        //     // Sort by lowest risk
        //     nexts.sortBy { it.risk }
        //     nexts.reverse()
        //     // println("Choosing nexts:")
        //     // nexts.forEach { println("  $it") }
        //     paths += nexts
        //
        //     paths.sortBy {
        //         it.path.size / it.risk
        //     }
        //     paths.reverse()
        //
        //     // readLine()
        // }
        //
        // println("minRisk=$minRisk")
    }
}
