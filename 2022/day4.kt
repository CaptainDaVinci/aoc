data class Assignment(val elfA: Pair<Int, Int>, val elfB: Pair<Int, Int>)

fun <T> List<T>.toPair(): Pair<T, T> {
    if (this.size != 2) {
        throw IllegalArgumentException("List is not of size 2")
    }
    return Pair(this[0], this[1])
}

fun Pair<Int, Int>.isOverlapping(oth: Pair<Int, Int>): Boolean {

    return ((oth.first >= this.first && oth.first <= this.second) || 
        (this.first >= oth.first && this.first <= oth.second))
}

fun Pair<Int, Int>.isCompletelyOverlappingWith(oth: Pair<Int, Int>): Boolean {
    return ((oth.first >= this.first && oth.second <= this.second) || 
        (oth.first >= this.first && oth.first <= this.second))
}

fun readInput(): List<Assignment> {
    val data: MutableList<Assignment> = mutableListOf<Assignment>()
    var s = readLine()
    while (s != null) {
        val (first, second) = s.split(',')
        val firstPair = first.split('-').map { it -> it.toIntOrNull()!! }.toPair()
        val secondPair = second.split('-').map { it -> it.toIntOrNull()!! }.toPair()
        data.add(Assignment(firstPair, secondPair))
        s = readLine()
    }
    return data
}

fun p1(input: List<Assignment>): Int {
    var ans = 0;
    input.forEach {
        if (it.elfA.isCompletelyOverlappingWith(it.elfB) 
            || it.elfB.isCompletelyOverlappingWith(it.elfA)) {
                ++ans
            }
    }
    return ans
}

fun p2(input: List<Assignment>): Int {
    var ans = 0;
    input.forEach {
        run {
            if (it.elfA.isOverlapping(it.elfB)) {
                ++ans
            }
        }
    }
    return ans
}

fun main() {
    val input = readInput()
    println(p2(input))
}
