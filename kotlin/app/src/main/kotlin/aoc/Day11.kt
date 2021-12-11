package aoc

class Day11 {
    fun part1() {
        var current = readGrid("day11.txt") { it }
        val max_step = 3

        println("Before any steps:")
        current.debug()

        for (step in 1..max_step) {
            val next = current.map { x, y, v ->
                // println("$x,$y <- $v")
                var flush = 0
                current.neighbors(x, y) { nx, ny, v ->
                    // println("  $nx, $ny: $v")
                    if (v >= 9) {
                        flush++
                    }
                }
                if (v >= 9) 0 else (v + 1 + flush) % 10
            }

            println("After step $step:")
            next.debug()
            current = next
        }
    }
}

