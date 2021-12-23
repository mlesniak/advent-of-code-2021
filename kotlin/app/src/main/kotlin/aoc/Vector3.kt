package aoc

data class Vector3(val x: Int, val y: Int, val z: Int) {
    companion object {
        fun of(value: String): Vector3 {
            val parts = value.trim().split(",")
            return Vector3(
                x = parts[0].toInt(),
                y = parts[1].toInt(),
                z = parts[2].toInt()
            )
        }
    }

    operator fun plus(o: Vector3): Vector3 =
        Vector3(
            x + o.x,
            y + o.y,
            z + o.z
        )

    operator fun get(idx: Int): Int =
        when (idx) {
            0 -> x
            1 -> y
            2 -> z
            else -> throw IllegalArgumentException("Illegal index")
        }

    operator fun minus(o: Vector3): Vector3 =
        Vector3(
            x - o.x,
            y - o.y,
            z - o.z
        )
}
