package aoc

import java.io.File

class Day12 {
    class CaveMap {
        private val paths: MutableMap<String, MutableList<String>> = mutableMapOf()

        fun add(from: String, to: String) {
            val dst = paths[from] ?: mutableListOf()
            dst.add(to)
            paths[from] = dst
        }

        fun add(path: String) {
            val parts = path.split("-")
            add(parts[0], parts[1])
            add(parts[1], parts[0])
        }

        fun debug() {
            for (start in paths.keys) {
                println("$start -> ${paths[start]}")
            }
        }

        fun next(path: Path): List<Path> {
            val lastCave = path.last()
            val res = mutableListOf<Path>()
            paths[lastCave]?.forEach {
                val n = path.add(it)
                res += n
            }
            return res
        }
    }

    data class Path(val caves: List<String>, val visited: MutableSet<String> = mutableSetOf()) {
        constructor(start: String) : this(mutableListOf(start)) {
            visited.add(start)
        }

        // TODO(mlesniak) Bad interface
        fun add(next: String): List<Path> {
            // println("for caves=$caves and visited=$visited trying to add $next")
            if (visited.contains(next)) {
                return emptyList()
            }
            val nextPath = listOf(*caves.toTypedArray(), next)
            val tmp = mutableSetOf<String>()
            visited.forEach { tmp.add(it) }
            if (next.lowercase() == next) {
                tmp.add(next)
            }
            return listOf(Path(caves = nextPath, visited = tmp))
        }

        fun last(): String = caves.last()
    }

    fun part1() {
        val map = CaveMap()
        File("day12.txt").readLines().forEach { line ->
            map.add(line)
        }
        map.debug()

        val finishedPaths = mutableListOf<Path>()
        var paths = mutableListOf<Path>()
        paths.add(Path("start"))

        while (paths.isNotEmpty()) {
            println("\n\nIteration...")

            val nextPaths = mutableListOf<Path>()
            for (p in paths) {
                println("\n* Current=$p ------------------")
                for (newPath in map.next(p)) {
                    println("newpath=$newPath")
                    if (newPath.last() == "end") {
                        finishedPaths += newPath
                    } else {
                        nextPaths += newPath
                    }
                }
            }
            paths = nextPaths

            // println("All paths:")
            // paths.forEach { println(it)}
            // readLine()
        }

        finishedPaths.forEach {
            println(it.caves)
        }
        println(finishedPaths.size)
    }
}