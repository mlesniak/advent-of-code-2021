package aoc

fun <T> debug(c: Collection<T>) {
    for (elem in c) {
        println(elem)
    }
}

fun <T> debug(grid: Grid<T>) {
    for (row in grid) {
        println(row.joinToString(""))
    }
}

fun panic(msg: String) {
    throw IllegalStateException("PANIC: $msg")
}

