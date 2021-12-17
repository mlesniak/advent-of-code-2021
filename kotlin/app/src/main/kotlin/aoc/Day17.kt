package aoc

class Day17 {
    data class Area(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
        operator fun contains(p: Point): Boolean =
            p.x in x1..x2 && p.y in y1..y2
    }

    fun part1() {
        // val target = Area(144, -100, 178, -76)
        val target = Area(20, -10, 30, -5)

        val start = Point(0, 0)
        val velocity = Point(7, 2)
        simulate(start, velocity, 10, target)
    }

    private fun simulate(start: Point, velocity: Point, maxSteps: Int, target: Area): Boolean {
        println("***** start=$start, velocity=$velocity")
        var vel = velocity
        var pos = start
        for (step in 0 until maxSteps) {
            println("\nStep $step")
            pos += vel
            // println("pos=$pos")
            if (pos in target) {
                println("Hit target after $step steps")
                return true
            }
            val dx = if (vel.x > 0) {
                -1
            } else if (vel.x < 0) {
                1
            } else {
                0
            }
            val dy = -1
            vel = Point(vel.x + dx, vel.y + dy)
            println("  pos=$pos, vel=$vel")
        }

        return false
    }
}