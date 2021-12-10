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

    fun part2() {
        val scores = mutableListOf<Int>()

        File("day10.txt").readLines().forEach {
            val stack = computeIncompleteLines(it) ?: return@forEach
            var lineScore = 0
            for (elem in stack.iterator()) {
                // println(elem)
                val elemScore = when (elem) {
                    '(' -> 1
                    '[' -> 2
                    '{' -> 3
                    '<' -> 4
                    else -> throw IllegalStateException("Unknown element $elem")
                }
                lineScore = lineScore * 5 + elemScore
            }
            println("  $stack -> $lineScore")
            scores.add(lineScore)
        }

        scores.sort()
        println("${scores.size} / $scores")
        val result = scores.get(scores.size / 2)
        println("[Day 10|Part1] = $result")
    }

    private fun computeIncompleteLines(line: String): ArrayDeque<Char>? {
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
                        return null
                    }
                }
            }
        }

        return stack
    }
}