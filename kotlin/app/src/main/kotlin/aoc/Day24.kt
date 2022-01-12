package aoc

import java.io.File

class Day24 {
    open class Command {
        class Inp(val dest: String) : Command() {
            override fun toString(): String {
                return "Inp(src='$dest')"
            }
        }

        class Mul(val a: String, val b: String) : Command() {
            override fun toString(): String {
                return "Mul(src='$a', value=$b)"
            }
        }

        class Add(val a: String, val b: String) : Command() {
            override fun toString(): String {
                return "Add(a='$a', b='$b')"
            }
        }

        class Div(val a: String, val b: String) : Command() {
            override fun toString(): String {
                return "Div(a='$a', b='$b')"
            }
        }

        class Mod(val a: String, val b: String) : Command() {
            override fun toString(): String {
                return "Mod(a='$a', b='$b')"
            }
        }

        class Eql(val a: String, val b: String) : Command() {
            override fun toString(): String {
                return "Eql(a='$a', b='$b')"
            }
        }

        companion object {
            private fun parse(line: String): Command {
                val parts = line.split(" ")
                return when (val cmd = parts[0]) {
                    "inp" -> Inp(parts[1])
                    "mul" -> Mul(parts[1], parts[2])
                    "eql" -> Eql(parts[1], parts[2])
                    "add" -> Add(parts[1], parts[2])
                    "div" -> Div(parts[1], parts[2])
                    "mod" -> Mod(parts[1], parts[2])
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
        // commands.debug("Parsed input")

        separator(description = "Running program")
        val result = run("13579246899999", commands)
        for ((k, v) in result) {
            println("$k=$v")
        }
    }

    fun run(input: String, commands: List<Command>): Map<String, Int> {
        val mem = mutableMapOf(
            "w" to 0,
            "x" to 0,
            "y" to 0,
            "z" to 0,
        )
        var inputIdx = 0
        for (c in commands) {
            // println("Executing $c       ${mem}")
            when (c) {
                is Command.Inp -> {
                    mem[c.dest] = "${input[inputIdx++]}".toInt()
                }
                is Command.Mul -> {
                    mem[c.a] = mem[c.a]!! * (toNumber(c.b) ?: mem[c.b]!!)
                }
                is Command.Eql -> {
                    mem[c.a] = if (mem[c.a]!! == (toNumber(c.b) ?: mem[c.b]!!)) 1 else 0
                }
                is Command.Add -> {
                    mem[c.a] = mem[c.a]!! + (toNumber(c.b) ?: mem[c.b]!!)!!
                }
                is Command.Div -> {
                    mem[c.a] = mem[c.a]!! / (toNumber(c.b) ?: mem[c.b]!!)
                }
                is Command.Mod -> {
                    mem[c.a] = mem[c.a]!! % (toNumber(c.b) ?: mem[c.b]!!)
                }

                else -> throw IllegalArgumentException("Unknown command $c")
            }
        }

        return mem.toMap()
    }

    private fun toNumber(s: String): Int? {
        try {
            return s.toInt()
        } catch (e: NumberFormatException) {
            return null
        }
    }
}