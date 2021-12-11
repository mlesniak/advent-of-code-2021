package aoc

import java.io.File

typealias Grid<T> = Array<Array<T>>

fun <T> Grid<T>.debug() {
    forEach { row ->
        println(row.contentToString())
    }
}

inline fun <reified T> Grid<T>.copy(): Grid<T> {
    val rows = mutableListOf<Array<T>>()
    forEach { row ->
        val copy = row.copyOf()
        rows.add(copy)
    }

    return rows.toTypedArray()
}

inline fun <reified T> readGrid(
    filename: String,
    convert: (Int) -> T
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

fun <T> Grid<T>.forEach(f: (x: Int, y: Int, v: T) -> T) {
    indices.forEach { y ->
        get(y).indices.forEach { x ->
            val v = get(y)[x]
            get(y)[x] = f(x, y, v)
        }
    }
}