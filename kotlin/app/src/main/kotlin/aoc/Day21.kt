package aoc

class Day21 {
    data class Player(val position: Int, val score: Int = 0) {

        override fun toString(): String {
            return "P($position,$score)"
        }
    }

    data class State(val players: List<Player>, val currentPlayer: Int)

    val cache = mutableMapOf<State, Pair<Long, Long>>()

    fun part2() {
        val root = State(listOf(
            Player(4),
            Player(8),
        ), 0)
        root.debug("Initial state")

        val result = compute(root)
        val r = if (result.first > result.second) result.first else result.second
        println("Part 2: $r")
    }

    // TODO(mlesniak) Add caching later
    private fun compute(state: State): Pair<Long, Long> {
        // Recursive call, computeIfAbsent not working, maybe own function?
        val cv = cache[state]
        if (cv != null) {
            return cv
        }

        var p0 = 0L
        var p1 = 0L
        forDiracDice { x, y, z ->
            val ps = computeNextState(state, x, y, z)
            p0 += ps.first
            p1 += ps.second
        }

        val v = p0 to p1
        cache[state] = v
        return v
    }

    private fun computeNextState(state: State, x: Int, y: Int, z: Int): Pair<Long, Long> {
        val currentPlayer = state.players[state.currentPlayer]

        // println()
        // println("$x, $y, $z")
        val advance = x + y + z
        // println("  $advance")
        val newPos = currentPlayer.position + advance
        val scoreDelta = if (newPos % 10 == 0) 10 else newPos % 10
        val newScore = currentPlayer.score + scoreDelta

        // Check if finished
        val winScore = 21
        if (newScore >= winScore) {
            return if (state.currentPlayer == 0) {
                1L to 0L
            } else {
                0L to 1L
            }
        }

        val players = when (val cp = state.currentPlayer) {
            0 -> listOf(
                Player(newPos % 10, newScore),
                // Player(newPos, newScore),
                state.players[1],
            )
            1 -> listOf(
                state.players[0],
                Player(newPos % 10, newScore),
                // Player(newPos, newScore),
            )
            else -> throw IllegalStateException("currentPlayer=$cp")
        }
        // players.debug()

        // Dive into children
        val newState = State(
            players,
            (state.currentPlayer + 1) % 2
        )

        return compute(newState)
    }

    private fun forDiracDice(f: (Int, Int, Int) -> Unit) {
        val diceSides = listOf(1, 2, 3)
        diceSides.forEach { x ->
            diceSides.forEach { y ->
                diceSides.forEach { z ->
                    f(x, y, z)
                }
            }
        }
    }
}