package aoc

import java.io.File

class Day11 {
    fun part1() {
        val grid = readGrid("day11.txt")
        grid.debug()
    }

}

fun Array<Array<Int>>.debug() {
    forEach { row ->
        println(row.contentToString())
    }
}

fun readGrid(filename: String): Array<Array<Int>> {
    val rows = mutableListOf<Array<Int>>()
    File(filename).readLines().forEach { line ->
        val cols = line.map { c -> c.toString().toInt() }.toTypedArray()
        rows.add(cols)
    }
    return rows.toTypedArray()
}
