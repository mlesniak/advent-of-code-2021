package aoc

import java.io.File
import kotlin.math.sqrt

class Day19 {
    data class Scanner(val id: Int, val vectors: List<Vector2>) {
        // Beacon -> relative distances to other beacons.
        // Assumption: distances are always unique enough.
        private val beaconFingerprints = mutableMapOf<Vector2, Set<Double>>()

        init {
            vectors.forEach { p ->
                val dists = mutableSetOf<Double>()
                vectors.forEach loop@{ o ->
                    if (o === p) {
                        return@loop
                    }

                    dists += sqrt((o.x - p.x) * (o.x - p.x) + (o.y - p.y) * (o.y - p.y).toDouble())
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

        fun equalBeacons(s: Scanner): Set<Vector2> {
            val res = mutableSetOf<Vector2>()
            beaconFingerprints.forEach { fp ->
                s.beaconFingerprints.forEach { sfp ->
                    if (fp.value == sfp.value) {
                        res += fp.key
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

        scanner.debug()

        var foundBeacon = 0
        val numberOfCommonBeacons = 3
        // Find pairs of scanner with common beacons.
        scanner.forEach { s1 ->
            scanner.forEach loop@ { s2 ->
                if (s1 === s2) {
                   return@loop
                }

                val equalBeacons = s1.equalBeacons(s2)
                equalBeacons.debug("equalBeacons=")
                if (equalBeacons.size == numberOfCommonBeacons) {
                    // TODO(mlesniak) ???
                    foundBeacon += equalBeacons.size
                }
            }
        }

        println(foundBeacon)
    }

    private fun scan(iter: Iterator<String>): Scanner {
        val id = iter.next().split(" ")[2].toInt()
        val vectors = mutableListOf<Vector2>()
        while (true) {
            if (!iter.hasNext()) {
                break
            }
            val line = iter.next()
            if (line.isEmpty()) {
                break
            }
            vectors += Vector2.of(line)
        }

        return Scanner(id, vectors)
    }
}
