package aoc

class Day17 {
    data class Area(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
        operator fun contains(p: Point): Boolean =
            p.x in x1..x2 && p.y in y1..y2
    }

    fun part1() {
        // val target = Area(144, -100, 178, -76)
        val target = Area(20, -10, 30, -5)

        simulate(Point(0,0), Point(6,9), 50, target)

        val hits = mutableListOf<Int>()
        for (x in 0..20) {
            for (y in 0..20) {
                println("x=$x,y=$y")
                val start = Point(0, 0)
                val velocity = Point(x, y)
                val hit = simulate(start, velocity, 50, target)
                if (hit.hit) {
                    hits += hit.maxY
                }
            }
        }
        println("$hits")
        val maxY = hits.maxOrNull()
        println("maxY=$maxY")
    }

    data class Result(val hit: Boolean, val maxY: Int)

    private fun simulate(start: Point, velocity: Point, maxSteps: Int, target: Area): Result {
        // println("***** start=$start, velocity=$velocity")
        var vel = velocity
        var pos = start
        var maxY = Int.MIN_VALUE
        for (step in 0 until maxSteps) {
            // println("\nStep $step")
            pos += vel
            if (pos.y > maxY) {
                maxY = pos.y
            }

            // println("pos=$pos")
            if (pos in target) {
                // println("Hit target after $step steps with maxY=$maxY")
                return Result(true, maxY)
            }
            val dx = if (vel.x > 0) {
                -1
            } else if (vel.x < 0) {
                1
            } else {
                0
            }
            val dy = -1
            val ny = vel.y + dy
            vel = Point(vel.x + dx, ny)
            // println("  pos=$pos, vel=$vel")
        }

        return Result(false, 0)
    }
}