package aoc

import java.io.File
import kotlin.math.sqrt

class Day19 {
    data class Scanner(val id: Int, val vectors: List<Vector3>) {
        // Beacon -> relative distances to other beacons.
        // Assumption: distances are always unique enough.
        val beaconFingerprints = mutableMapOf<Vector3, Set<Double>>()

        init {
            vectors.forEach { p ->
                val dists = mutableSetOf<Double>()
                vectors.forEach loop@{ o ->
                    if (o === p) {
                        return@loop
                    }

                    dists += sqrt(
                        +(o.x - p.x) * (o.x - p.x)
                                + (o.y - p.y) * (o.y - p.y)
                                + (o.z - p.z) * (o.z - p.z).toDouble()
                    )
                }
                beaconFingerprints[p] = dists
            }
        }

        override fun toString(): String {
            return """Scanner(
                |   id=$id,
                |   vectors=$vectors,
                |   beaconFingerprint=$beaconFingerprints
            |)""".trimMargin("|")
        }

        fun findCommonBeacons(s: Scanner): List<Pair<Vector3, Vector3>> {
            val numberOfCommonBeacons = 12 - 1

            val res = mutableListOf<Pair<Vector3, Vector3>>()
            beaconFingerprints.forEach { fp ->
                s.beaconFingerprints.forEach { sfp ->
                    val commonBeacons = fp.value.intersect(sfp.value)
                    if (commonBeacons.size >= numberOfCommonBeacons) {
                        res += Pair(fp.key, sfp.key)
                    }
                }
            }
            return res
        }
    }

    fun part1() {
        val scanner = mutableListOf<Scanner>()
        val fileInput = File("day19.txt").readLines().iterator()
        while (fileInput.hasNext()) {
            scanner += scan(fileInput)
        }

        val scannerPositions = mutableMapOf<Int, Vector3>()
        scannerPositions[0] = Vector3(0, 0, 0)

        while (scannerPositions.size < scanner.size) {
            for (i in scanner.indices) {
                for (j in scanner.indices) {
                    val sourceId = scanner[i].id
                    val targetId = scanner[j].id
                    if (scannerPositions[sourceId] == null) {
                        // No reference from starting point.
                        continue
                    }
                    if (scannerPositions[sourceId] != null && scannerPositions[targetId] != null) {
                        // Already found a pair?
                        continue
                    }
                    separator()
                    println("Examining scanner pair i=$i and j=$j")
                    println("Current scanner positions:")
                    scannerPositions.debug(indent = 2)
                    val commonBeacons = scanner[i].findCommonBeacons(scanner[j])
                    if (commonBeacons.size != 12) {
                        continue
                    }
                    println("Scanner have 12 beacon in common")

                    // Find scanner position
                    val possibilities = arrayOf(
                        arrayOf(0, 1, 2),
                        arrayOf(0, 2, 1),
                        arrayOf(1, 0, 2),
                        arrayOf(2, 0, 1),
                        arrayOf(1, 2, 0),
                        arrayOf(2, 1, 0),
                    )
                    loop@ for (p in possibilities) {
                        for (p0s in arrayOf(-1, 1)) {
                            for (p1s in arrayOf(-1, 1)) {
                                for (p2s in arrayOf(-1, 1)) {
                                    println("\nPermutation ${p0s}*[${p[0]}] ${p1s}*[${p[1]}] $p2s*[${p[2]}]")
                                    // Potential sensor position
                                    val b0 = commonBeacons[0].first
                                    val b1 = commonBeacons[0].second
                                    val sensor = Vector3(
                                        b0.x - b1[p[0]] * p0s,
                                        b0.y - b1[p[1]] * p1s,
                                        b0.z - b1[p[2]] * p2s,
                                    )
                                    println("  Sensor at $sensor")
                                    println("  b$i=$b0")
                                    println("  b$j=$b1")

                                    val c0 = commonBeacons[1].first
                                    val c1 = commonBeacons[1].second
                                    println("  c$i=$c0")
                                    println("  c$j=$c1")

                                    // Relative to coordinates of sensor_i and
                                    // relative to permutations
                                    val c1list = arrayOf(c1.x, c1.y, c1.z)
                                    val cm = Vector3(
                                        c1list[p[0]] * p0s,
                                        c1list[p[1]] * p1s,
                                        c1list[p[2]] * p2s,
                                    )
                                    println("  cm=$cm")
                                    val potentialC0 = sensor + cm
                                    println("  pt=$potentialC0")
                                    if (potentialC0 == c0) {
                                        println("=>FOUND: SENSOR=$sensor for $sourceId->$targetId")

                                        val source = scannerPositions[sourceId]!!
                                        scannerPositions[targetId] = Vector3(
                                            source.x + sensor.x,
                                            source.y + sensor.y,
                                            source.z + sensor.z,
                                        )
                                        println("  STORED=${scannerPositions[targetId]}")
                                        // Update all beacon positions of this scanner.
                                        val updatedPositions = scanner[j].vectors.map { v ->
                                            Vector3(
                                                v[p[0]] * p0s,
                                                v[p[1]] * p1s,
                                                v[p[2]] * p2s,
                                            )
                                        }
                                        scanner[j] = Scanner(j, updatedPositions)
                                        // scanner[j].vectors.forEach { v ->
                                        //     println("    $v")
                                        // }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        separator()
        scannerPositions.debug()

        separator()
        // val uniques = mutableMap<Vector3, Set<Double>>
        // val all = scanner.map { s -> s.beaconFingerprints}
        // all.debug()
        // val uniques = mutableSetOf<Vector3>()
        // for (i in scanner.indices) {
        //     val s = scanner[i]
        //     s.vectors.forEach { v ->
        //         val translated = v + scannerPositions[i]!!
        //         uniques += translated
        //     }
        // }
        // println(uniques.size)

        var maxDist = Int.MIN_VALUE
        scannerPositions.values.forEach { s1 ->
            scannerPositions.values.forEach { s2 ->
                val dist = s1.manhattan(s2)
                println("$s1 -> $s2 = $dist")
                if (dist > maxDist) {
                    maxDist = dist
                }
            }
        }
        println(maxDist)
    }

    private fun scan(iter: Iterator<String>): Scanner {
        val id = iter.next().split(" ")[2].toInt()
        val vectors = mutableListOf<Vector3>()
        while (true) {
            if (!iter.hasNext()) {
                break
            }
            val line = iter.next()
            if (line.isEmpty()) {
                break
            }
            vectors += Vector3.of(line)
        }

        return Scanner(id, vectors)
    }
}
