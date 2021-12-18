package aoc

class Day18 {
    data class Node(var parent: Node?, var value: Int?, var left: Node?, var right: Node?) {
        companion object {
            fun parse(parent: Node?, s: String): Node {
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
                    val node = Node(
                        parent,
                        null,
                        null,
                        null,
                    )
                    node.left = parse(node, substring.substring(0, cutIndex))
                    node.right = parse(node, substring.substring(cutIndex + 1))
                    return node
                } else {
                    val num = s.toInt()
                    return Node(parent, num, null, null)
                }
            }
        }

        fun leaf(): Boolean = value != null

        fun magnitude(): Int =
            when {
                leaf() -> value!!
                else -> 3 * left?.magnitude()!! + 2 * right?.magnitude()!!
            }

        override fun toString(): String {
            fun render(self: Node, level: Int): String {
                val sb = StringBuilder()
                when {
                    self.leaf() -> sb.append(self.value)
                    else -> {
                        val ls = render(self.left!!, level + 1)
                        val rs = render(self.right!!, level + 1)
                        sb.append("[${ls},${rs}]")
                    }
                }

                return sb.toString()
            }

            return render(this, 0)
        }

        data class Result(val node: Node, val stop: Boolean)

        // fun split(): Result {
        //     if (!leaf()) {
        //         return Result(this, false)
        //     }
        //
        //     if (value!! <= 9) {
        //         return Result(this, false)
        //     }
        //
        //     return Result(
        //         Node(
        //             null,
        //             Node(value / 2),
        //             Node(value / 2 + value % 2)
        //         ),
        //         true
        //     )
        // }

        fun compute() {
            compute(0)
        }

        fun compute(level: Int) {
            if (leaf()) {
                println("${" ".repeat(level * 2)}LEAF $value on level $level")
                return
            }

            if (level == 4) {
                println("${" ".repeat(level * 2)}need to explode on level $level")
            }

            println("${" ".repeat(level * 2)}LEFT")
            left!!.compute(level + 1)

            println("${" ".repeat(level * 2)}RIGHT")
            right!!.compute(level + 1)
        }

        operator fun plus(n2: Node): Node {
            val node = Node(null, null, this, n2)
            this.parent = node
            n2.parent = node
            return node
        }
    }

    fun part1() {
        // val lines = File("day18.txt").readLines()
        // lines.debug()

        // val n1 = Node.parse("[1,2]")
        // val n2 = Node.parse("[[3,4],5]")
        // val node = n1 + n2
        // val node = Node.parse("[[[[[9,8],1],2],3],4]")
        val node = Node.parse(null, "[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]")
        // val node = Node.parse("[[1,2],[[3,4],5]]")
        node.debug()
        node.compute()
        // node.magnitude().debug()

        // Node(9).split().debug()
        // Node(10).split().debug()
        // Node(11).split().debug()
        // Node(12).split().debug()
    }
}
