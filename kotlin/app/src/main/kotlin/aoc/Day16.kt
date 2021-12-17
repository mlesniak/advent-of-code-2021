package aoc

import java.io.File

class Day16 {
    open class BasePacket(
        open val version: Int,
        open val type: Int,
        open val content: List<BasePacket>
    ) {
        override fun toString(): String {
            return "BasePacket(version=$version, type=$type, content=$content)"
        }

        fun debug() {
            println("version=$version, type=$type")
            content.forEach { it.debug() }
        }

        fun versionSum(): Int {
            return version + content.sumOf { it.versionSum() }
        }
    }

    class LiteralValue(
        override val version: Int,
        val value: Long
    ) : BasePacket(version, 4, emptyList()) {
        override fun toString(): String {
            return "LiteralValue(version=$version, value=$value)"
        }
    }

    class Operator(
        override val version: Int,
        override val type: Int,
        override val content: List<BasePacket>
    ) : BasePacket(version, type, content) {
        override fun toString(): String {
            return "Operator(version=$version, type=$type, content=$content)"
        }
    }

    fun part1() {
        val line = File("day16.txt").readText()
        val binary = decodeToBinary(line)
        binary.debug()
        binary.length.debug()

        var index = 0
        val buckets = mutableListOf<BasePacket>()
        while (index < binary.length - 7) {
            val res = parse(binary, index)
            buckets += res.packet
            println("=== AT $index:")
            res.packet.debug()
            index += res.nextIndex
        }

        val sum = buckets[0].versionSum()
        println("sum=$sum")

        // buckets.forEach { println(it) }
    }

    data class ParseResult(val packet: BasePacket, val nextIndex: Int)

    private fun parse(binary: String, startIndex: Int): ParseResult {
        println("\nstartIndex=$startIndex; binary=${binary.substring(startIndex)}")
        var index = startIndex
        val version = binary.substring(index, index + 3).toInt(2)
        val type = binary.substring(index + 3, index + 3 + 3).toInt(2)
        println("PACKET version=$version type=$type")
        index += 6

        when (type) {
            // 100
            4 -> {
                var binaryNumber = ""
                var stop = false
                while (!stop) {
                    val s = binary.substring(index, index + 5)
                    // s.debug()
                    stop = s[0] == '0'
                    binaryNumber += s.substring(1)
                    index += 5
                }
                println("* Trying to parse $binaryNumber")
                val number = binaryNumber.toLong(2)
                val literalValue = LiteralValue(version, number)
                literalValue.debug()
                return ParseResult(literalValue, index)
            }
            // Operators yet to be defined
            else -> {
                val lengthType = binary[index]
                index++
                if (lengthType == '0') {
                    // Length-encoded
                    println("* total length - encoding")
                    val length = binary.substring(index, index + 15).toInt(2)
                    index += 15
                    println("* length = $length")
                    val buckets = mutableListOf<BasePacket>()
                    var finalIndex = index + length
                    var nextIndex = index
                    while (nextIndex < length + index) {
                        // nextIndex = parse(binary, nextIndex)
                        val res = parse(binary, nextIndex)
                        nextIndex = res.nextIndex
                        buckets += res.packet
                    }
                    return ParseResult(
                        Operator(version, type, buckets),
                        nextIndex
                    )
                    // index = nextIndex + 1
                } else {
                    // Number encoded
                    println("* number of packets - encoding")
                    val numberOfPackets = binary.substring(index, index + 11).toInt(2)
                    println("* number=$numberOfPackets")
                    index += 11
                    val buckets = mutableListOf<BasePacket>()
                    repeat(numberOfPackets) {
                        // index = parse(binary, index)
                        val res = parse(binary, index)
                        index = res.nextIndex
                        buckets += res.packet

                    }
                    return ParseResult(
                        Operator(version, type, buckets),
                        index
                    )
                }
            }
        }

        // throw IllegalStateException("Should not be reached")
    }

    // private fun parseBasePacket(input: String): BasePacket {
    //     val version = input.substring(0, 0 + 3).toInt(2)
    //     val type = input.substring(3, 3 + 3).toInt(2)
    //
    //     return BasePacket(
    //         version = version,
    //         type = type,
    //     )
    // }

    private fun decodeToBinary(line: String): String {
        var res = ""
        for (c in line) {
            res += c
                .toString()
                .toInt(16)
                .toString(2)
                .padStart(4, '0')
        }
        return res
    }
}