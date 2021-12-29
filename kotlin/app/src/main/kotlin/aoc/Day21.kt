package aoc

class Day21 {
    class Player(var position: Int) {
        var score = 0

        override fun toString(): String {
            return "Player(position=$position, score=$score)"
        }
    }

    fun part1() {
        val players = arrayOf(
            // Player(4),
            // Player(8)

            // Real input
            Player(4),
            Player(3)
        )
        var dice = 0
        var counter = 0
        var player = 0

        while (true) {
            separator()
            println("Current player $player, dice rolled $counter")
            val current = players[player]
            repeat(3) {
                counter++
                dice = dice % 100 + 1
                println("dice=$dice")
                current.position += dice
                println("position=${current.position}")
            }
            current.score += if (current.position % 10 == 0) {
                10
            } else {
                current.position % 10
            }
            println("Score: ${current.score}")
            player = (player + 1) % 2

            if (current.score >= 1000) {
                break
            }
        }

        println("Points of losing player: ${players[player].score}")
        println("Dice rolled $counter")
        println("Part1 ${players[player].score * counter}")
    }
}