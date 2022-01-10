package aoc

import aoc.Day22.Step.Switch.OFF
import aoc.Day22.Step.Switch.ON
import java.io.File

class Day22 {

    data class Step(val switch: Switch, val x: IntRange, val y: IntRange, val z: IntRange) {
        enum class Switch {
            ON, OFF,
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
        val (expected, current) = compute(initialSteps)
        println("$expected == $current")
    }

    fun compute(initialSteps: Iterable<Step>): Pair<Int, Int> {
        val (ons, offs) = initialSteps.partition { it.switch == ON }

        val mergedOns = mutableSetOf<Step>()
        for (i in ons.indices) {
            var noConflict = true
            for (j in i + 1 until ons.size) {
                if (i == j) {
                    continue
                }
                // for (j in i + 1 until ons.size) {
                val o1 = ons[i]
                val o2 = ons[j]
                separator()
                println(o1)
                println(o2)
                val merged = merge(o1, o2)
                merged.debug("merged:")
                if (merged.isNotEmpty()) {
                    noConflict = false
                }
                mergedOns += merged
            }
            if (noConflict) {
                mergedOns += ons[i]
            }
        }

        separator(description = "Merged Ons")
        mergedOns.debug()
        val onscore = computeUsingVector(mergedOns)
        println("mergedScore=$onscore")
        val res2 = mergedOns.sumOf { step ->
            step.x.count() * step.y.count() * step.z.count()
        }
        println(res2)

        val mergedOffs = mutableSetOf<Step>()
        for (i in offs.indices) {
            var noConflict = true
            for (j in i + 1 until offs.size) {
                val o1 = offs[i]
                val o2 = offs[j]
                val merged = merge(o1, o2)
                if (merged.isNotEmpty()) {
                    noConflict = false
                }
                mergedOffs += merged
            }
            if (noConflict) {
                mergedOffs += offs[i]
            }
        }
        if (offs.size == 1) {
            mergedOffs += offs
        }
        separator(description = "Merged Offs")
        mergedOffs.debug()

        // exitProcess(1)

        // separator(description = "ACTUAL COMPUTATION")
        var steps = mutableSetOf<Step>()
        var todo = mutableListOf(*mergedOns.toTypedArray())
        var counter = 0
        while (todo.isNotEmpty()) {
            if (counter++ % 100 == 0) {
                println(todo.size)
            }
            // if (counter-- == 0) {
            //     exitProcess(1)
            // }

            // println()
            val on = todo.removeFirst()
            // on.debug(desc = "on")
            var noConflict = true
            for (off in mergedOffs) {
                // off.debug("against")
                val x = merge(on, off)
                // x.debug("against off")

                if (x.isNotEmpty()) {
                    noConflict = false
                    break
                }

                todo += x.filter { it.switch == ON }
            }

            if (noConflict) {
                steps += on
            }
        }

        // Corner case: no offs, all Ons are valid.
        // if (offs.isEmpty()) {
        //     steps += mergedOns
        // }

        // tasklist mit ons
        // for all offs
        // a conflict? add new ons
        // not a conflict? add to final section.

        // Ignore OFFs
        steps = steps.filter { step -> step.switch == ON }.toMutableSet()

        separator(description = "Steps")
        steps.debug()

        // Use range multiplication for actual computation.
        val res = steps.sumOf { step ->
            step.x.count() * step.y.count() * step.z.count()
        }

        val current = res
        val expected = computeUsingVector(initialSteps, "expected")

        return expected to current
    }

    internal fun merge(step1: Step, step2: Step): List<Step> {
        // println("\nstep1=$step1\nstep2=$step2")
        val dx = split(step1.switch, step1.x, step2.switch, step2.x)
        val dy = split(step1.switch, step1.y, step2.switch, step2.y)
        val dz = split(step1.switch, step1.z, step2.switch, step2.z)

        // println("dx")
        // dx.debug()
        // println("dy")
        // dy.debug()
        // println("dz")
        // dz.debug()
        // separator()

        val steps = mutableListOf<Step>()
        for (sx in dx) {
            for (sy in dy) {
                for (sz in dz) {
                    var sw = ON
                    if (sx.switch == OFF && sy.switch == OFF && sz.switch == OFF) {
                        sw = OFF
                    }
                    steps += Step(sw, sx.range, sy.range, sz.range)
                }
            }
        }

        // No overlapping at all.
        if (steps.isEmpty()) {
            return emptyList()
        }

        return steps
    }

    data class SwitchIntRange(val switch: Step.Switch, val start: Int, val endInclusive: Int) {
        val range: IntRange get() = IntRange(start, endInclusive)
        override fun toString(): String {
            return "($switch,$start,$endInclusive)"
        }
    }

    // Assuming b should be removed from a range
    private fun split(swa: Step.Switch, a: IntRange, swb: Step.Switch, b: IntRange): List<SwitchIntRange> {
        // b outside right
        if (a.last < b.first) {
            return emptyList()
        }

        // b outside left
        if (a.first > b.last) {
            return emptyList()
        }

        // b from right side
        if (a.last >= b.first && a.last <= b.last) {
            return listOf(
                SwitchIntRange(swa, a.first, b.first - 1),
                SwitchIntRange(swb, b.first, a.last),
            ).filter { !it.range.isEmpty() }
        }

        // b from left side
        if (a.first <= b.last && a.first >= b.first) {
            return listOf(
                SwitchIntRange(swa, b.last + 1, a.last),
                SwitchIntRange(swb, a.first, b.last),
            ).filter { !it.range.isEmpty() }
        }

        // b inside
        return listOf(
            SwitchIntRange(swa, a.first, b.first - 1),
            SwitchIntRange(swa, b.last + 1, a.last),
            SwitchIntRange(swb, b.first, b.last),
        ).filter { !it.range.isEmpty() }
    }
}
