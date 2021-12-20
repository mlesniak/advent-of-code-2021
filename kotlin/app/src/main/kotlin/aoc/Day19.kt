package aoc

import java.io.File
import kotlin.math.sqrt

class Day19 {
    data class Scanner(val id: Int, val vectors: List<Vector>) {
        // Beacon -> relative distances to other beacons.
        // Assumption: distances are always unique enough.
        private val beaconFingerprint = mutableMapOf<Vector, Set<Double>>()

        init {
            vectors.forEach { p ->
                val dists = mutableSetOf<Double>()
                vectors.forEach loop@{ o ->
                    if (o === p) {
                        return@loop
                    }

                    dists += sqrt((o.x - p.x) * (o.x - p.x) + (o.y - p.y) * (o.y - p.y).toDouble())
                }
                beaconFingerprint[p] = dists
            }
        }

        override fun toString(): String {
            return """Scanner(
                |   id=$id,
                |   vectors=$vectors,
                |   beaconFingerprint=$beaconFingerprint
            |)""".trimMargin("|")
        }
    }

    fun part1() {
        val scanner = mutableListOf<Scanner>()
        val fileInput = File("day19.txt").readLines().iterator()
        while (fileInput.hasNext()) {
            scanner += scan(fileInput)
        }

        scanner.debug()
    }

    private fun scan(iter: Iterator<String>): Scanner {
        val id = iter.next().split(" ")[2].toInt()
        val vectors = mutableListOf<Vector>()
        while (true) {
            if (!iter.hasNext()) {
                break
            }
            val line = iter.next()
            if (line.isEmpty()) {
                break
            }
            vectors += Vector.of(line)
        }

        return Scanner(id, vectors)
    }
}
