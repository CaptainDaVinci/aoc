fun readInput(): String {
    return readLine()!!
}

fun p1(data: String): Int {
    for (i in 0..data.length - 4) {
        val s = data.slice(i..i+3) 
        if (s.toSet().size == 4) {
            return i + 4
        }
    }
    return -1
}

fun p2(data: String): Int {
    for (i in 0..data.length - 14) {
        val s = data.slice(i..i+13) 
        if (s.toSet().size == 14) {
            return i + 14
        }
    }
    return -1
}


fun main() {
    val input = readInput()
    println(p2(input))
}
