package aoc

import java.io.File

class Day24 {
    open class Command {
        class Inp(val dest: String) : Command() {
            override fun toString(): String {
                return "Inp(src='$dest')"
            }
        }

        class Mul(val a: String, val b: Int) : Command() {
            override fun toString(): String {
                return "Mul(src='$a', value=$b)"
            }
        }

        companion object {
            private fun parse(line: String): Command {
                val parts = line.split(" ")
                return when (val cmd = parts[0]) {
                    "inp" -> Inp(parts[1])
                    "mul" -> Mul(parts[1], parts[2].toInt())
                    else -> throw IllegalArgumentException("Unknown command $cmd")
                }
            }

            fun parse(lines: List<String>): List<Command> {
                return lines.map { Command.parse(it) }
            }
        }
    }

    fun part1() {
        val commands = Command.parse(File("day24.txt").readLines())
        commands.debug("Parsed input")

        val result = run("91234567890", commands)
        for ((k, v) in result) {
            println("$k=$v")
        }
    }

    fun run(input: String, commands: List<Command>): Map<String, Int> {
        val mem = mutableMapOf<String, Int>()
        var inputIdx = 0
        for (c in commands) {
            when (c) {
                is Command.Inp -> {
                    mem[c.dest] = "${input[inputIdx++]}".toInt()
                }
                is Command.Mul -> {
                    mem[c.a] = mem[c.a]!! * c.b
                }
                else -> throw IllegalArgumentException("Unknown command $c")
            }
        }

        return mem.toMap()
    }
}