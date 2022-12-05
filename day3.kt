fun readInput(): List<String> {
    val data: MutableList<String> = mutableListOf<String>()
    var s = readLine()
    while (s != null) {
        data.add(s)
        s = readLine()
    }
    return data
}

fun p1(input: List<String>): Int {
    var ans = 0;
    input.forEach {
        run {
            ans += it.take(it.length / 2)
                .toSet()
                .intersect(it.takeLast(it.length / 2).toSet())
                .map { 
                    it -> when {
                        it.isUpperCase() -> 27 + (it - 'A')
                        else -> 1 + (it - 'a')
                    }
                }.sum()
        }
    }
    return ans
}

fun main() {
    val input = readInput()
    println(p1(input))
}
