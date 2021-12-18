package aoc

class Day18 {
    data class Node(val value: Int?, val left: Node?, val right: Node?) {
        constructor(left: Int, right: Int) : this(null, Node(left, null, null), Node(right, null, null))
        constructor(value: Int) : this(value, null, null)
        constructor(left: Node, right: Node) : this(null, left, right)

        companion object {
            fun parse(s: String): Node {
                // println("Parsing <$s>")
                if (s[0] == '[') {
                    val substring = s.substring(1, s.length - 1)
                    // substring.debug()
                    var cutIndex = 0
                    var openBracket = 0
                    for (i in substring.indices) {
                        when (substring[i]) {
                            ',' -> {
                                if (openBracket == 0) {
                                    cutIndex = i
                                    break
                                }
                            }
                            '[' -> openBracket++
                            ']' -> openBracket--
                            else -> {} // needed?
                        }
                    }
                    return Node(
                        null,
                        parse(substring.substring(0,cutIndex)),
                        parse(substring.substring(cutIndex+1)))
                } else {
                    val num = s.toInt()
                    return Node(num, null, null)
                }
            }
        }

        fun leaf(): Boolean = value != null

        fun magnitude(): Int =
            when {
                leaf() -> value!!
                else -> 3 * left?.magnitude()!! + 2 * right?.magnitude()!!
            }

        fun debug() {
            println(toString())
        }

        override fun toString(): String {
            return render(0)
        }

        private fun render(level: Int): String {
            val sb = StringBuilder()
            when {
                leaf() -> sb.append(value)
                else -> {
                    val ls = left?.render(level + 1)
                    val rs = right?.render(level + 1)
                    sb.append("[${ls},${rs}]")
                }
            }

            return sb.toString()
        }
    }

    fun part1() {
        // val lines = File("day18.txt").readLines()
        // lines.debug()

        // println(Node(9, 1).magnitude())
        // val node = Node(Node(1, 2), Node(Node(3, 4), Node(5)))
        // Node.parse("1").debug()
        val node = Node.parse("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]")
        // val node = Node.parse("[[1,2],[[3,4],5]]")
        node.debug()
        node.magnitude().debug()
    }
}
