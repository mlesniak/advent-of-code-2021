package aoc

import java.io.File

class Day10 {
    fun part1() {
        var points = 0
        File("day10.txt").readLines().forEach {
            val illegalCharacter = computeScore(it)
            val score = when (illegalCharacter) {
                null -> 0
                ')' -> 3
                ']' -> 57
                '}' -> 1197
                '>' -> 25137
                else -> throw IllegalStateException("Unknown return value $illegalCharacter")
            }
            if (score > 0) {
                println("$it = $illegalCharacter")
            }
            points += score
        }

        println("[Day 10|Part1] = $points")
    }

    private fun computeScore(line: String): Char? {
        // println("Processing $line")
        val stack = ArrayDeque<Char>()

        for (c in line) {
            // println("  ...$c")
            when (c) {
                '(', '[', '{', '<' -> stack.addFirst(c)
                ')', ']', '}', '>' -> {
                    val expected = stack.removeFirst()
                    val matching = when (c) {
                        ')' -> expected == '('
                        ']' -> expected == '['
                        '}' -> expected == '{'
                        '>' -> expected == '<'
                        else -> throw IllegalStateException("Unknown char $c")
                    }
                    if (!matching) {
                        return c
                    }
                }
            }
        }

        return null
    }
}