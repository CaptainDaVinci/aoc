import kotlin.math.max

fun readData(): List<List<Int>> {
    val data: MutableList<List<Int>> = mutableListOf<List<Int>>()
    var s: String? = "a"
    while (s != null) {
        s = readLine()
        val miniData: MutableList<Int> = mutableListOf()
        while (s != null && !s.isBlank()) {
            miniData.add(s.toInt())  
            s = readLine()
        }
        data.add(miniData)
    }
    return data
}

fun main() {
    val data = readData()
    println(data.sortedByDescending { it.sum() }.take(3).fold(0) { sum, element -> sum + element.sum() }) }
