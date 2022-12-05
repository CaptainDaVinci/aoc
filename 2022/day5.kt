import kotlin.math.ceil
import kotlin.text.StringBuilder

typealias Stack<T> = MutableList<T>
data class Move(val from: Int, val to: Int, val n: Int) 

fun <T> Stack<T>.push(element: T) {
    this.add(element)
}

fun <T> Stack<T>.pop(): T {
    return this.removeLast()
}

fun <T> Stack<T>.peek(): T {
    return this.last()
}

fun <T> Stack<T>.pushMany(elements: List<T>) {
    this.addAll(elements)
}

fun <T> Stack<T>.popMany(n: Int): List<T> {
    val poppedElements: MutableList<T> = mutableListOf()
    for (i in 1..n) {
        poppedElements.add(this.pop())
    }
    return poppedElements
}

fun <T> Stack<T>.popManyInOrder(n: Int): List<T> {
    return this.popMany(n).reversed()
}

fun readInput(): Pair<List<Stack<Char>>, List<Move>> {
    val data: MutableList<Stack<Char>> = mutableListOf<Stack<Char>>()
    val moveData: MutableList<Move> = mutableListOf<Move>()
    var s = readLine()
    while (s != null) {
        if (s.startsWith("move")) {
            val words = s.split(' ')
            moveData.add(
                Move(words[3].toIntOrNull()!! - 1, words[5].toIntOrNull()!! - 1, words[1].toIntOrNull()!!)
            )
        } else {
            val line = s.toList()
            for (i in line.indices step 4) {
                val stackId = ceil((i + 1) / 4.0).toInt()
                if (data.size < stackId) {
                    (1..stackId).forEach {
                        data.add(mutableListOf<Char>())
                    }
                }
                if (line[i] == '[') {
                    data[stackId - 1].push(line[i + 1])
                }
            }
        }
        s = readLine()
    }
    data.forEach {
        it.reverse()
    }
    return Pair(data, moveData)
}

fun p1(data: List<Stack<Char>>, moves: List<Move>): String {
    moves.forEach {
        val poppedElements = data[it.from].popMany(it.n)
        data[it.to].pushMany(poppedElements)
    }
    val s = StringBuilder()
    data.forEach {
        s.append(if (it.isNotEmpty()) it.last() else "")
    }
    return s.toString()
}

fun p2(data: List<Stack<Char>>, moves: List<Move>): String {
    moves.forEach {
        val poppedElements = data[it.from].popManyInOrder(it.n)
        data[it.to].pushMany(poppedElements)
    }
    val s = StringBuilder()
    data.forEach {
        s.append(if (it.isNotEmpty()) it.last() else "")
    }
    return s.toString()
}

fun main() {
    val (data, move) = readInput()
    println(p2(data, move))
}
