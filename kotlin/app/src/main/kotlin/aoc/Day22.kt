package aoc

import aoc.Day22.Step.Switch.NONE
import aoc.Day22.Step.Switch.OFF
import aoc.Day22.Step.Switch.ON
import java.io.File

class Day22 {
    data class Step(val switch: Switch, val x: IntRange, val y: IntRange, val z: IntRange) {
        enum class Switch {
            ON,
            OFF,
            NONE,
        }

        companion object {
            private fun toRange(s: String): IntRange {
                val parts = s.split("..")
                return IntRange(
                    parts[0].toInt(),
                    parts[1].toInt(),
                )
            }

            fun from(s: String): Step {
                val basicParts = s.split(" ")
                val switch = if (basicParts[0] == "on") ON else OFF
                val ranges = basicParts[1].split(",").map { it.split("=")[1] }

                return Step(
                    switch = switch,
                    x = toRange(ranges[0]),
                    y = toRange(ranges[1]),
                    z = toRange(ranges[2]),
                )
            }
        }
    }

    fun part1() {
        val steps = File("day22.txt").readLines().map(Step.Companion::from)
        println("PART1: ${computeUsingVector(steps)}")
    }

    fun computeUsingVector(steps: Iterable<Step>, desc: String = ""): Int {
        val cubes = mutableSetOf<Vector3>()

        for (step in steps) {
            for (x in step.x) {
                if (x < -50 || x > 50) {
                    continue
                }
                for (y in step.y) {
                    if (y < -50 || y > 50) {
                        continue
                    }
                    for (z in step.z) {
                        if (z < -50 || z > 50) {
                            continue
                        }
                        val cube = Vector3(x, y, z)
                        when (step.switch) {
                            ON -> cubes += cube
                            OFF -> cubes -= cube
                        }
                    }
                }
            }
        }

        return cubes.size
    }

    fun part2() {
        val initialSteps = File("day22.txt").readLines().map(Step.Companion::from)
        initialSteps.debug("Initial steps")

        val result = compute(initialSteps)
        println("Part2: $result")

        /**
         * Algorithm: Parse the input cuboids. Then start with an empty list (clist)
         * of cuboids. For each cuboid from the input, calculate the intersections
         * with the cuboids in the clist. If the intersection is non-empty add it
         * to the clist with inverted sign w.r.t to the current cuboid in clist.
         * If that cuboid from the input is set on, add it to the clist as well.
         * Then add together all the volumes (cuboids with a negative sign will
         * have a negative volume).
         **/
    }

    fun compute(initialSteps: List<Step>): Long {
        val processed = mutableListOf<Step>()
        for (step in initialSteps) {
            // separator()
            // step.debug("step")
            val tmpProc = mutableListOf<Step>()
            for (proc in processed) {
                // proc.debug("proc")
                val intersec = intersection(step, proc)
                if (intersec != null) {
                    // intersec.debug("intersec\n  ")
                    tmpProc += intersec.copy(
                        switch = if (proc.switch == ON) OFF else ON
                    )
                }
            }
            processed += tmpProc
            if (step.switch == ON) {
                processed += step
            }
        }

        // separator(description = "Computed cubes")
        // processed.debug()

        // separator(description = "Result")
        val result = processed.sumOf {
            val signum = if (it.switch == ON) 1L else -1L
            it.x.count().toLong() * it.y.count().toLong() * it.z.count().toLong() * signum
        }
        return result
    }

    fun intersection(s: Step, t: Step): Step? {
        val ix = split(s.x, t.x)
        val iy = split(s.y, t.y)
        val iz = split(s.z, t.z)

        if (ix == null || iy == null || iz == null) {
            return null
        }

        return Step(NONE, ix, iy, iz)
    }

    internal fun split(i: IntRange, j: IntRange): IntRange? {
        // val elems = i.intersect(j)
        // if (elems.isEmpty()) {
        //     return null
        // }
        //
        // return elems.minOrNull()!!..elems.maxOrNull()!!

        // Outside right
        if (i.last < j.first) {
            return null
        }

        // Outside left
        if (i.first > j.last) {
            return null
        }

        // Fully inside j
        if (i.first < j.first && i.last >= j.last) {
            return j.first..j.last
        }

        // Fully inside i
        if (i.first >= j.first && i.last <= j.last) {
            return i.first..i.last
        }

        // Partial inside right
        if (i.last >= j.first && i.last < j.last) {
            return j.first..i.last
        }

        // Partial inside left
        if (i.first <= j.last && i.last > j.last) {
            return i.first..j.last
        }

        throw IllegalStateException("Unknown constellation: i=$i, j=$j")
    }
}
