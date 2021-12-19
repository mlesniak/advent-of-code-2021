package aoc

import java.io.File
import kotlin.math.absoluteValue

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point = Point(x + other.x, y + other.y)

    companion object {
        fun of(value: String): Point {
            val parts = value.trim().split(",")
            return Point(
                x = parts[0].toInt(),
                y = parts[1].toInt()
            )
        }
    }
}

// TODO(mlesniak) Proper class instead of alias
typealias Grid<T> = Array<Array<T>>

// fun <T> Grid<T>.debug() {
//     forEach { row ->
//         println(row.joinToString(""))
//     }
// }

// Property not possible
fun <T> Grid<T>.width(): Int = this[0].size

fun <T> Grid<T>.height(): Int = this.size

inline fun <reified T> Grid<T>.copy(): Grid<T> {
    val rows = mutableListOf<Array<T>>()
    forEach { row ->
        val copy = row.map { it }.toTypedArray()
        rows.add(copy)
    }

    return rows.toTypedArray()
}

inline fun <reified T> readGrid(
    filename: String,
    convert: (Int) -> T = {it as T}
): Grid<T> {
    val rows = mutableListOf<Array<T>>()
    File(filename).readLines().forEach { line ->
        val cols = line.map { c ->
            convert(c.toString().toInt())
        }.toTypedArray()
        rows.add(cols)
    }
    return rows.toTypedArray()
}

fun <T> Grid<T>.inlineMap(f: (x: Int, y: Int, v: T) -> T) {
    indices.forEach { y ->
        get(y).indices.forEach { x ->
            val v = get(y)[x]
            get(y)[x] = f(x, y, v)
        }
    }
}

fun <T> Grid<T>.forEach(f: (x: Int, y: Int, v: T) -> Unit) {
    indices.forEach { y ->
        get(y).indices.forEach { x ->
            val v = get(y)[x]
            f(x, y, v)
        }
    }
}

inline fun <reified T> Grid<T>.map(f: (x: Int, y: Int, v: T) -> T): Grid<T> {
    val rows = mutableListOf<Array<T>>()

    indices.forEach { y ->
        val cols = get(y)
        val newCols = cols.copyOf()
        cols.indices.forEach { x ->
            val v = cols[x]
            newCols[x] = f(x, y, v)
        }
        rows.add(newCols)
    }

    return rows.toTypedArray()
}

fun <T> Grid<T>.neighbors(x: Int, y: Int, f: (x: Int, y: Int, value: T) -> Unit) {
    for (dy in -1..1) {
        for (dx in -1..1) {
            val nx = x + dx
            val ny = y + dy
            if (dx == 0 && dy == 0) {
                continue
            }

            //No diagonal movements
            if (dx.absoluteValue + dy.absoluteValue == 2) {
                continue
            }

            if (nx < 0 || ny < 0) {
                continue
            }
            if (nx >= get(0).size || ny >= size) {
                continue
            }

            f(nx, ny, get(ny)[nx])
        }
    }
}