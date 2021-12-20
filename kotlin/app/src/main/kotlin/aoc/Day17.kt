package aoc

class Day17 {
    data class Area(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
        operator fun contains(p: Vector2): Boolean =
            p.x in x1..x2 && p.y in y1..y2
    }

    fun part1() {
        val target = Area(144, -100, 178, -76)
        // val target = Area(20, -10, 30, -5)

        simulate(Vector2(0,0), Vector2(6,9), 50, target)

        var counter = 0
        val hits = mutableListOf<Result>()
        for (x in 0..1000) {
            println("$x")
            for (y in -500..500) {
                // println("x=$x,y=$y")
                val start = Vector2(0, 0)
                val velocity = Vector2(x, y)
                val hit = simulate(start, velocity, 1000, target)
                if (hit.hit) {
                    counter++
                    hits += hit
                }
            }
        }
        println("$hits")
        val maxY = hits.maxOf { it.maxY }
        println("maxY=$maxY")
        println("counter=$counter")
    }

    data class Result(val hit: Boolean, val maxY: Int)

    private fun simulate(start: Vector2, velocity: Vector2, maxSteps: Int, target: Area): Result {
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
            vel = Vector2(vel.x + dx, ny)
            // println("  pos=$pos, vel=$vel")
        }

        return Result(false, 0)
    }
}