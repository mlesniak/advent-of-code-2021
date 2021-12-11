package aoc

import java.io.File

class Day11 {
    fun part1() {
        val rows = mutableListOf<Array<Int>>()
        File("day11.txt").readLines().forEach { line ->
            val cols = line.map { c -> c.toString().toInt() }.toTypedArray()
            rows.add(cols)
            // println(cols.contentToString())
        }
        val grid = rows.toTypedArray()

        grid.debug()
    }
}

fun Array<Array<Int>>.debug() {
    forEach { row ->
        println(row.contentToString())
    }
}