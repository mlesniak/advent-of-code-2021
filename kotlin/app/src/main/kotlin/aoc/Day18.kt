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

        fun explode() {
            explode(0)
        }

        // ... : Continue?
        fun explode(level: Int): Boolean {
            if (leaf()) {
                return true
            }

            if (level == 4) {
                if (left!!.leaf() && right!!.leaf()) {
                    addLeft(this.parent, left!!.value!!)
                    addRight(this, right!!.value!!)

                    left = null
                    right = null
                    value = 0
                }
                return false
            }

            if (!left!!.explode(level + 1)) {
                return false
            }

            return right!!.explode(level + 1)
        }

        private fun addLeft(node: Node?, value: Int) {
            if (node == null) {
                return
            }
            if (node.left!!.leaf()) {
                node.left!!.value = node.left!!.value!! + value
                return
            }

            addLeft(node.parent, value)
        }

        private fun addRight(node: Node?, value: Int) {
            println("node=$node")
            if (node == null) {
                return
            }

            // UP
            var cur: Node? = node
            while (cur != null && cur!!.right != null && cur.parent != null && cur.parent!!.right === cur) {
                println("cur=$cur")
                cur = cur.parent
            }
            if (cur!!.parent == null) {
                // root
                return
            }
            cur = cur.parent!!.right
            println("top=$cur")

            // DOWN
            while (true) {
                println("at $cur")
                if (cur != null && cur.leaf()) {
                    cur.value = cur.value!! + value
                    return
                }

                if (cur != null && cur.left != null) {
                    if (cur.left!!.leaf()) {
                        cur.left!!.value = cur.left!!.value!! + value
                        return
                    }
                    cur = cur!!.left
                    continue
                }
                cur = cur!!.right
            }
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

        val node = Node.parse(
            null,
            // "[[[[[9,8],1],2],3],4]"
            // "[7,[6,[5,[4,[3,2]]]]]"
            // "[[6,[5,[4,[3,2]]]],1]"
            // "[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]"
            "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]"
        )

        node.debug()
        println("after 1st explode")
        node.explode()
        node.debug()
        println("\n\nafter 2nd explode")
        node.explode()
        node.debug()
    }
}
