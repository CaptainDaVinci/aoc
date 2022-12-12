import kotlin.math.abs

fun readInput(): List<String> {
    return generateSequence {
        readLine()
    }.toList()
}


fun getCycleXMap(input: List<String>): Map<Int, Int> {
    var cycle: Int = 0
    var x = 1
    val cycleXMap = mutableMapOf<Int, Int>(0 to x)

    input.forEach {
        if (it.startsWith("noop")) {
            ++cycle
        } else {
            val v = it.split(' ')[1].toIntOrNull()!!
            cycle += 2
            x += v
            cycleXMap[cycle + 1] = x
        }
    }

    return cycleXMap
}

fun p1(input: List<String>): Int {
    val cycleXMap = getCycleXMap(input)

    var ans = 0
    listOf(20, 60, 100, 140, 180, 220).forEach b@{ 
        base -> 
            val keys: List<Int> = cycleXMap.keys.toList()
            keys.sortedByDescending { it }.forEach {
                if (it <= base) {
                    ans += base * cycleXMap[it]!!
                    return@b
                }
            }
    }

    return ans 
}

fun p2(input: List<String>) {
    val crt: MutableList<MutableList<Char>> = MutableList(6) { MutableList(40) {'.'} }
    val cycleXMap = getCycleXMap(input)
    val keys: List<Int> = cycleXMap.keys.toList().sortedByDescending { it }
    
    println(cycleXMap)
    for (cycle in 0..239) {
        var pos = keys.find { it <= cycle + 1 } 
        val crtPos = cycle % 40
        if (abs(cycleXMap[pos]!! - crtPos) <= 1) {
            crt[cycle / 40][cycle % 40] = '#'
        }
    }

    crt.forEach {
        println(it.joinToString(""))
    }
}

fun main() {
    p2(readInput())
}
