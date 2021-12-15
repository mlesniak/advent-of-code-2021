package aoc

import java.util.*

class Day15 {
    // Dijkstra
    fun part1() {
        var map: Grid<Int> = readGrid("day15.txt")


        // 1163751742 2274862853 3385973964 4496184175 5517295286
        // 1163751742 2274862853 3385973964 4496184175 5517295286

        // We could do some modulo magic to compute the neighbor risks
        // on the fly (maybe), but let's be pragmatic here and simply
        // create a large area.
        val factor = 5
        val input: Array<Array<Int>> = Array(map.height() * factor) {
            Array(map.width() * factor) { 0 }
        }
        for (ytile in 0 until factor) {
            for (xtile in 0 until factor) {
                for (y in map.indices) {
                    for (x in 0 until map[0].size) {
                        var v = map[y][x] + xtile + ytile
                        if (v > 9) {
                            v = v % 10 + 1
                        }
                        input[map.height() * ytile + y][map.width()*xtile + x] = v
                    }
                }
            }
        }
        map = input
        // input.debug()
        // return

        val risks = mutableMapOf<Point, Int>()
        // val unvisitedNodes = mutableSetOf<Point>()

        val compareByRisk: Comparator<Point> = compareBy { risks[it] }
        val unvisitedNodes = PriorityQueue(compareByRisk)
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
            val s = unvisitedNodes.size
            if (s % 1_000 == 0) {
                println(s)
            }

            // println()
            // Find the smallest one -- use better data structure if too slow.
            var current = unvisitedNodes.remove()
            var minRisk = risks[current]!!
            // var minRisk = Int.MAX_VALUE
            // unvisitedNodes.forEach { node ->
            //     val nodeRisk = risks[node]!!
            //     if (nodeRisk < minRisk) {
            //         minRisk = nodeRisk
            //         current = node
            //     }
            // }
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
                unvisitedNodes -= point
                unvisitedNodes += point
            }
            // unvisitedNodes.remove(current)
            // unvisitedNodes.sortBy { risks[it] }
            // readLine()
        }

        val goal = Point(map.width() - 1, map.height() - 1)
        val goalScore = risks[goal]
        println("Score for $goal=$goalScore")

        println("${System.currentTimeMillis() - now}")
    }
}
