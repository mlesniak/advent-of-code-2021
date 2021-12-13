package aoc

class Day11 {
    fun part1() {
        var current = readGrid("day11.txt") { it }
        val max_step = 10000

        var numFlashes = 0

        println("Before any steps:")
        current.debug()

        for (step in 1..max_step) {
            println("\n === step=$step")

            // First, the energy level of each octopus increases by 1.
            current = current.map { _, _, v -> v + 1 }

            // Then, any octopus with an energy level greater than 9 flashes.
            var flashed = false
            val flashedCoords = mutableListOf<Point>()
            do {
                flashed = false
                // println("DEBUG/current=")
                // current.debug()
                // println("----")

                val tmp = current.copy()
                current.inlineMap { x, y, v ->
                    if (v >= 10) {
                        flashed = true
                        numFlashes++
                        flashedCoords.add(Point(x, y))
                        current.neighbors(x, y) { nx, ny, nv ->
                            if (nv < 10) {
                                tmp[ny][nx] = tmp[ny][nx] + 1
                            }
                        }
                        tmp[y][x] = 0
                    }
                    v
                }
                current = tmp
                current =current.map { x, y, v ->
                    if (flashedCoords.contains(Point(x, y))) {
                        0
                    } else {
                        v
                    }
                }
            } while (flashed)

            println("\nAfter step $step:")
            current.debug()

            // Part 2
            var allZero = true
            current.map { _, _, v ->
                if (v != 0) {
                    allZero = false
                }
                v
            }
            if (allZero) {
                println("Synchronized")
                break
            }
        }

        println(numFlashes)
    }
}

