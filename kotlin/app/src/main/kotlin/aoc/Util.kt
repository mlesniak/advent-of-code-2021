package aoc

fun <T> Collection<T>.debug() {
    for (elem in this) {
        println(elem)
    }
}

fun <T> Grid<T>.debug() {
    for (row in this) {
        println(row.joinToString(""))
    }
}

fun Any.debug() {
    println(this)
}