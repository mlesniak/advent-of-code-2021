package aoc

fun <T> Collection<T>.debug(desc: String? = null) {
    if (desc != null) {
        println("$desc:")
    }
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

fun separator(count: Int = 80, char: Char = '-', description: String = "") {
    val prefix = if (description.isEmpty()) "" else "\n$char $description "
    println(prefix + "$char".repeat(count - prefix.length))
}
