package aoc

import java.io.File

class Day11 {
    fun part1() {
        val grid = readGrid("day11.txt")
        grid.debug()

        val c = grid.copy()
        grid[2][3] = -100
        grid.debug()
        c.debug()
    }
}

typealias Grid = Array<Array<Int>>

fun Grid.debug() {
    forEach { row ->
        println(row.contentToString())
    }
}

fun Grid.copy(): Grid {
    val rows = mutableListOf<Array<Int>>()
    forEach { row ->
        val copy  = row.copyOf()
        rows.add(copy)
    }

    return rows.toTypedArray()
}

fun readGrid(filename: String): Grid {
    val rows = mutableListOf<Array<Int>>()
    File(filename).readLines().forEach { line ->
        val cols = line.map { c -> c.toString().toInt() }.toTypedArray()
        rows.add(cols)
    }
    return rows.toTypedArray()
}
