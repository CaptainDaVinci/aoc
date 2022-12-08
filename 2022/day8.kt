import kotlin.math.min
import kotlin.math.max


fun readInput(): List<String> {
    return generateSequence { readLine() }.toList()
}

fun getData(input: List<String>): List<List<Int>> {
    val data: MutableList<List<Int>> = mutableListOf()
    input.forEach {
        data.add(it.toList().map { it.digitToInt() })
    }
    return data
}

data class Direction(var top: Int = -1, var left: Int = -1, var right: Int = -1, var bottom: Int = -1) {
    fun allDirs(v: Int) {
        top = v
        bottom = v
        left = v
        right = v
    }

    fun lowest(): Int {
        return minOf(top, bottom, min(left, right))
    }
}

fun p1(data: List<List<Int>>): Int {
    val minHeight: MutableList<MutableList<Direction>> = MutableList(data.size) { MutableList(data[0].size) { Direction() } }

    for (j in data[0].indices) {
        minHeight[0][j].allDirs(data[0][j])
        minHeight[data.size - 1][j].allDirs(data[data.size - 1][j])
    }
    for (i in data.indices) {
        minHeight[i][0].allDirs(data[i][0])
        minHeight[i][data[0].size - 1].allDirs(data[i][data[0].size - 1])
    }
    
    for (i in 1..data.size - 2) {
        for (j in 1..data[i].size - 2) {
            minHeight[i][j].top = max(minHeight[i][j].top, max(minHeight[i - 1][j].top, data[i - 1][j]))
            minHeight[i][j].left = max(minHeight[i][j].left, max(minHeight[i][j - 1].left, data[i][j - 1]))
        }
    }
    for (i in data.size - 2 downTo 1) {
        for (j in data[0].size - 2 downTo 1) {
            minHeight[i][j].bottom = max(minHeight[i][j].bottom, max(minHeight[i + 1][j].bottom, data[i + 1][j]))
            minHeight[i][j].right = max(minHeight[i][j].right, max(minHeight[i][j + 1].right, data[i][j + 1]))
        }
    }

    var ans = data.size * 2 + data[0].size * 2 - 4
    for (i in 1..data.size - 2) {
        for (j in 1..data[i].size - 2) {
            if (data[i][j] > minHeight[i][j].lowest()) {
                ++ans
            }
        }
    }
    return ans
}

fun p2(data: List<List<Int>>): Int {
    var ans = 0
    for (i in 1..data.size - 2) {
        for (j in 1..data[i].size - 2) {
            var topViewDist = 1
            var bottomViewDist = 1
            var rightViewDist = 1
            var leftViewDist = 1
            for (top in i - 1 downTo 1) {
                if (data[top][j] < data[i][j]) {
                    ++topViewDist
                } else {
                    break
                }
            }
            for (bottom in i + 1..data.size - 2) {
                if (data[bottom][j] < data[i][j]) {
                    ++bottomViewDist
                } else {
                    break
                }
            }
            for (left in j - 1 downTo 1) {
                if (data[i][left] < data[i][j]) {
                    ++leftViewDist
                } else {
                    break
                }
            }
            for (right in j + 1..data[0].size - 2) {
                if (data[i][right] < data[i][j]) {
                    ++rightViewDist
                } else {
                    break
                }
            }
            ans = max(ans, topViewDist * bottomViewDist * leftViewDist * rightViewDist)
        }
    }
    return ans
}

fun main() {
    val input = readInput()
    val data = getData(input)
    println(p2(data))
}
