package aoc

fun <T> Collection<T>.debug() {
    for (elem in this) {
        println(elem)
    }
}

fun <T, V> Map<T, V>.debug() {
    for (elem in this) {
        println(elem)
    }
}


fun <T> Grid<T>.debug() {
    for (row in this) {
        println(row.joinToString(""))
    }
}

fun Any.debug(desc: String? = null) {
    if (desc != null) {
        print("$desc: ")
    }
    println(this)
}