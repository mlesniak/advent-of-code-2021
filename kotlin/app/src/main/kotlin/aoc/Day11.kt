package aoc

class Day11 {
    fun part1() {
        val current = readGrid("day11.txt")
        current.debug()

        // Increase
        current.forEach { x, y, v ->
            v+1
        }
        println()
        current.debug()


    }
}

