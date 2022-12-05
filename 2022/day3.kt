fun readInput(): List<String> {
    val data: MutableList<String> = mutableListOf<String>()
    var s = readLine()
    while (s != null) {
        data.add(s)
        s = readLine()
    }
    return data
}

fun getPriority(c: Char): Int {
    return if (c.isUpperCase()) {
                27 + (c - 'A')
            } else {
                1 + (c - 'a')
            }
}

fun p1(input: List<String>): Int {
    var ans = 0;
    input.forEach {
        run {
            ans += it.take(it.length / 2)
                .toSet()
                .intersect(it.takeLast(it.length / 2).toSet())
                .map { 
                    it -> getPriority(it)
                }.sum()
        }
    }
    return ans
}

fun p2(input: List<String>): Int {
    var ans = 0;
    for (i in input.indices step 3) {
        val setA = input[i].toSet()
        val setB = input[i + 1].toSet()
        val setC = input[i + 2].toSet()

        val badge = setA.intersect(setB).intersect(setC).first()
        ans += getPriority(badge)
    }
    return ans
}

fun main() {
    val input = readInput()
    println(p2(input))
}
