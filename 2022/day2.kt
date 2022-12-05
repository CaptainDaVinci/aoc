fun readInput(): List<List<String>> {
    val data: MutableList<List<String>> = mutableListOf<List<String>>()
    var s = readLine()
    while (s != null) {
        val game = s.split(' ')
        data.add(game)
        s = readLine()
    }
    return data
}

fun p1(input: List<List<String>>): Int {
    return input.map { 
        it -> getValue(it[1]) + when {
            getWinningMove(it[0]) == it[1] -> 6
            getLosingMove(it[0]) == it[1] -> 0
            else -> 3 
        }
    }.sum()
}

fun getWinningMove(move: String): String {
    return when(move) {
        "A" -> "Y"
        "B" -> "Z"
        "C" -> "X"
        else -> ""
    }
}

fun getLosingMove(move: String): String {
    return when(move) {
        "A" -> "Z"
        "B" -> "X"
        "C" -> "Y"
        else -> ""
    }
}

fun getValue(move: String): Int {
    return when(move) {
        "A", "X" -> 1
        "B", "Y" -> 2
        "C", "Z" -> 3
        else -> 0
    }
}

fun p2(input: List<List<String>>): Int {
    return input.map {
            it -> when {
                it[1] == "X" -> getValue(getLosingMove(it[0]))  
                it[1] == "Y" -> 3 + getValue(it[0])
                it[1] == "Z" -> 6 + getValue(getWinningMove(it[0]))  
                else -> 0
            }
        }.sum()
}


fun main() {
    val input = readInput()
    println(p1(input))
}
