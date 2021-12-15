package aoc

class Day15 {
    // Dijkstra
    fun part1() {
        val map: Grid<Int> = readGrid("day15.txt")

        val risks = mutableMapOf<Point, Int>()
        val unvisitedNodes = mutableSetOf<Point>()
        for (y in map.indices) {
            for (x in 0 until map[0].size) {
                val initialScore =
                    if (x == 0 && y == 0) {
                        0
                    } else {
                        Int.MAX_VALUE
                    }
                risks[Point(x, y)] = initialScore
                unvisitedNodes += Point(x, y)
            }
        }
        // unvisitedNodes.debug()

        val now = System.currentTimeMillis()
        while (unvisitedNodes.isNotEmpty()) {
            // println()
            // Find the smallest one -- use better data structure if too slow.
            var current = unvisitedNodes.iterator().next()
            var minRisk = Int.MAX_VALUE
            unvisitedNodes.forEach { node ->
                val nodeRisk = risks[node]!!
                if (nodeRisk < minRisk) {
                    minRisk = nodeRisk
                    current = node
                }
            }
            // println("current=$current risk=$minRisk")

            map.neighbors(current.x, current.y) { nx, ny, v ->
                val point = Point(nx, ny)
                if (point !in unvisitedNodes) {
                   return@neighbors
                }

                val thisScore = minRisk + v
                val curScore = risks[point]!!
                if (thisScore < curScore) {
                    risks[point] = thisScore
                }
            }
            unvisitedNodes.remove(current)
            // unvisitedNodes.sortBy { risks[it] }
            // readLine()
        }

        val goal = Point(map.width() - 1, map.height() - 1)
        val goalScore = risks[goal]
        println("Score for $goal=$goalScore")

        println("${System.currentTimeMillis() - now}")
    }
}
