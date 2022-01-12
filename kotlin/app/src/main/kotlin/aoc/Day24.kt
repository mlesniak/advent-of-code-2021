package aoc

import java.io.File
import kotlin.math.abs
import kotlin.random.Random

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
                return lines
                    .filter { it.isNotBlank() }
                    .map { parse(it) }
            }
        }
    }

    /**
    inp w       -- w

    mul x 0
    add x z
    mod x 26
    div z 1     -- zDiv
    add x 14    -- addX
    eql x w
    eql x 0

    mul y 0
    add y 25
    mul y x
    add y 1     -- addY
    mul z y
    mul y 0
    add y w
    add y 0
    mul y x
    add z y
     **/

    data class Mem(var ws: Array<Int> = emptyArray(), var x: Int = 0, var y: Int = 0, var w: Int = 0, var z: Int = 0) {
        override fun toString(): String = "(x=$x, y=$y, w=$w, z=$z)"
    }

    fun compute(mem: Mem, widx: Int, addX: Int, addY: Int): Mem {
        val zDiv = if (addX < 0) 26 else 1
        mem.apply {
            // Compute x = (z % 26) + addX
            x = 0
            x += z
            x %= 26
            z /= zDiv
            x += addX
            // If equal to input, skip next part, since x will be 0,
            // hence y below will be 0 and z *= 1.
            // If not equal to input, z = z*26 + ws[idx] + addY
            x = if (x == ws[widx]) 1 else 0
            x = if (x == 0) 1 else 0

            // Compute z*26
            y = 0
            y += 25
            y *= x
            y += 1
            z *= y

            // Compute ws[idx] + addY
            y = 0
            y += ws[widx]
            y += addY
            y *= x

            // Sum up
            z += y
        }

        return mem
    }

    fun part1() {
        val commands = Command.parse(File("day24.txt").readLines())
        // commands.debug("Parsed input")

        // 14 times the same computation as shown here!
        // val coms = mutableListOf<Command>()
        // var idx = 0
        // var zDiv = 0
        // var addX = 0
        // var addY = 0
        // var widx = -1
        // for (c in commands) {
        //     if (c is Command.Inp) {
        //         // println(coms)
        //         if (widx != -1) {
        //             println("compute(mem, $widx, $zDiv, $addX ,$addY)")
        //         }
        //         coms.clear()
        //         idx = 0
        //         widx++
        //         continue
        //     }
        //
        //     idx++
        //     when (idx) {
        //         4 -> zDiv = (c as Command.Div).b.toInt()
        //         5 -> addX = (c as Command.Add).b.toInt()
        //         15 -> addY = (c as Command.Add).b.toInt()
        //     }
        //     coms += c
        // }
        // // println(coms)
        // println("compute(mem, $widx, $zDiv, $addX ,$addY)")

        // separator(description = "Running program")
        //
        // for (i in 1..1_000_000) {
        //     val id = randomSerialNumber()
        //     println("$i -> $id")
        //     val result = run("13579246899999", commands)
        //     if (result["z"] == 0) {
        //         println("Found")
        //         exitProcess(1)
        //     }
        // }

        val test = "13579246899999"
        println(run(test, commands))
        println(hardcoded(test, Mem()))
    }

    fun hardcoded(input: String, mem: Mem): Mem {
        val m = mem.copy(ws = input.map { "$it".toInt() }.toTypedArray())

        // Parameters for each run
        // m, ws[idx], zDiv, addX, addY
        //
        // zDiv -> 26 if addX negative
        // zDiv ->  1 if addX positive
        // (replacec in compute call)
        //
        // each seven cases
        //
        // all with multiplies of 26 -> base26
        //
        // idea: z is a stack of digits in base26, hence
        //      push *26
        //      pop  /26
        //
        // addX positive: push ws[idx] + addY
        // addX negative: pop from stack (z /= 26)
        //                if popped + addX not equal to ws[idx], push input + addY
        //
        // Since z is base26, and we want z == 0, stack empty?

        // addX check
        // addY offset

        // @formatter:off
        compute(m, 0, 14, 0)        // push ws[0] + 0
        compute(m, 1, 13, 12)       // push ws[1] + 12
        compute(m, 2, 15, 14)       // push ws[2] + 14
        compute(m, 3, 13, 0)        // push ws[3] + 0
        compute(m, 4, -2, 3)        // pop. ws[4] = pop - 2
        compute(m, 5, 10, 15)       // push ws[5] + 15
        compute(m, 6, 13, 11)       // push ws[6] + 11
        compute(m, 7, -15, 12)      // pop. ws[7] = pop -15
        compute(m, 8, 11, 1)        // push ws[8] + 1
        compute(m, 9, -9, 12)       // pop. ws[9] = pop - 9
        compute(m, 10, -9, 3)       // pop. ws[10]= pop - 9
        compute(m, 11, -7, 10)      // pop. ws[11]= pop - 7
        compute(m, 12, -4, 14)      // pop. ws[12]= pop - 4
        compute(m, 13, -6, 12)      // pop. ws[13]= pop - 6
        // @formatter:on

        /**
        Simulating stack operations

        ws[4] = ws[3] - 2
        ws[7] = ws[6] - 4
        ws[9] = ws[8] - 8
        ws[10]= ws[5] + 6
        ws[11]= ws[2] + 7
        ws[12]= ws[1] + 8
        ws[13]= ws[0] - 6

        // Start with the biggest number that is in range 9..1
        0: 9
        1: 1
        2: 2
        3: 9
        4: 7
        5: 3
        6: 9
        7: 5
        8: 9
        9: 1
        10:9
        11:9
        12:9
        13:3

        91297395919993

         // Start with smallest number that is in range 1..9
         0: 7
         1: 1
         2: 1
         3: 3
         4: 1
         5: 1
         6: 5
         7: 1
         8: 9
         9: 1
         10:7
         11:8
         12:9
         13:1

         71131151917891
         **/

        return m
    }

    fun randomSerialNumber(): String {
        val rnd: Random = Random.Default
        val chars = "123456789"
        val sb = StringBuilder()
        repeat(14) {
            sb.append(chars[abs(rnd.nextInt()) % chars.length])
        }
        return sb.toString()
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