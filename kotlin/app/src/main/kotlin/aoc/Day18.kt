package aoc

import java.io.File

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

        fun split(): Boolean {
            if (leaf()) {
                if (value!! <= 9) {
                    return true
                }

                // println("Splitting $this")
                val node = Node(
                    this.parent,
                    null,
                    null,
                    null
                )
                node.left = Node(node, value!! / 2, null, null)
                node.right = Node(node, value!! / 2 + value!! % 2, null, null)
                if (parent!!.left === this) {
                    parent!!.left = node
                } else {
                    parent!!.right = node
                }
                // println("  new node $node")
                return false
            }

            val c1 = left?.split() ?: true
            if (c1) {
                val c2 = right?.split() ?: true
                return c2
            } else {
                return false
            }
        }

        fun explode(): Boolean = explode(0)

        // ... : Continue?
        fun explode(level: Int): Boolean {
            if (leaf()) {
                return true
            }

            if (level == 4) {
                if (left!!.leaf() && right!!.leaf()) {
                    // println("exploding $this")
                    addLeft(this, left!!.value!!)
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

        // private fun addLeft(node: Node?, value: Int) {
        //     if (node == null) {
        //         return
        //     }
        //     if (node.left!!.leaf()) {
        //         node.left!!.value = node.left!!.value!! + value
        //         return
        //     }
        //
        //     addLeft(node.parent, value)
        // }

        private fun addLeft(node: Node?, value: Int) {
            // println("node=$node")
            if (node == null) {
                return
            }

            // UP
            var cur: Node? = node
            while (cur != null && cur!!.left != null && cur.parent != null && cur.parent!!.left === cur) {
                // println("cur=$cur")
                cur = cur.parent
            }
            if (cur!!.parent == null) {
                // root
                return
            }
            cur = cur.parent!!.left
            // println("top=$cur")

            // DOWN
            while (true) {
                // println("at $cur")
                if (cur != null && cur.leaf()) {
                    cur.value = cur.value!! + value
                    return
                }

                if (cur != null && cur.right != null) {
                    if (cur.right!!.leaf()) {
                        cur.right!!.value = cur.right!!.value!! + value
                        return
                    }
                    cur = cur!!.right
                    continue
                }
                cur = cur!!.left
            }
        }


        private fun addRight(node: Node?, value: Int) {
            // println("node=$node")
            if (node == null) {
                return
            }

            // UP
            var cur: Node? = node
            while (cur != null && cur!!.right != null && cur.parent != null && cur.parent!!.right === cur) {
                // println("cur=$cur")
                cur = cur.parent
            }
            if (cur!!.parent == null) {
                // root
                return
            }
            cur = cur.parent!!.right
            // println("top=$cur")

            // DOWN
            while (true) {
                // println("at $cur")
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
        val lines = File("day18.txt").readLines()
        // lines.debug()

        // var line = Node.parse(null, lines[0])
        // println("INITIAL=$line\n")
        // for (i in 1 until lines.size) {
        //     println("\n\nAdding ${lines[i]}")
        //     val cur = Node.parse(null, lines[i])
        //     line = line + cur
        //     perform(line)
        //     println("RESULT")
        //     line.debug()
        //     // return
        // }
        //
        // println("\n\nFinal")
        // line.debug()
        //
        // val amgn = line.magnitude()
        // println(amgn)

        var magn = Int.MIN_VALUE
        for (i in lines.indices) {
            for (j in lines.indices) {
                if (i == j) {
                    continue
                }
                println("$i -> $j")
                val l1 = Node.parse(null, lines[i])
                val l2 = Node.parse(null, lines[j])
                val cur = l1+l2
                perform(cur)
                val m = cur.magnitude()
                if (magn < m) {
                    println("magn=$magn")
                    magn = m
                }
            }
        }
        println(magn)

        // val n1 = Node.parse(
        //     null,
        //     // "[[[[[9,8],1],2],3],4]"
        //     "[7,[6,[5,[4,[3,2]]]]]"
        //     // "[[6,[5,[4,[3,2]]]],1]"
        //     // "[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]"
        //     // "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]"
        //     // "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]"
        //
        //     // "[10,3]"
        //     // "[[[[4,3],4],4],[7,[[8,4],9]]]"
        // )
        // n1.explode()
        // n1.debug()

        // val n2 = Node.parse(
        //     null,
        //     "[1,1]"
        // )
        //
        // var node = n1 + n2
        // node.debug()
        //
        // perform(node)
        //
        // // println("cur=$cur")
        // println("\n\nFinal:")
        // node.debug()
    }

    private fun perform(node: Node) {
        var car = ""
        while (car != node.toString()) {
            car = node.toString()
            var cur = ""
            // println("\nexploding...")
            // node.debug()
            while (cur != node.toString()) {
                cur = node.toString()
                // println("explode cur=$cur")
                // node.debug()
                node.explode()
            }

            cur = ""
            // println("\nsplitting...: $node")
            // while (cur != node.toString()) {
                cur = node.toString()
                // println("split cur=$cur")
                // node.debug()
                node.split()
            // }
        }
    }
}
