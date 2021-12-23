package aoc

fun <T> Collection<T>.debug() {
    for (elem in this) {
        println(elem)
    }
}

fun <T, V> Map<T, V>.debug(indent: Int = 0) {
    for (elem in this) {
        println(" ".repeat(indent) + elem)
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

fun separator() {
    println("-".repeat(80))
}
