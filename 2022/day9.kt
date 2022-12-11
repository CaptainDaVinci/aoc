import kotlin.math.min
import kotlin.math.abs
import kotlin.math.sign

data class Move(val dir: Char, val count: Int) 

fun readInput(): List<String> {
    return generateSequence {
        readLine()
    }.toList()
}


fun getData(input: List<String>): List<Move> {
    val data = mutableListOf<Move>()
    input.forEach {
        val (dir, count) = it.split(' ')
        data.add(Move(dir.first(), count.toIntOrNull()!!))
    }
    return data
}

data class Pos(var x: Int = 0, var y: Int = 0) {
    fun farBehind(oth: Pos): Boolean {
        return abs(oth.x - x) >= 2 || abs(oth.y - y) >= 2
    }
}

fun p1(moves: List<Move>): Int {
    val head = Pos(0, 0)
    val tail = Pos(0, 0)

    val tailMoves = mutableSetOf<Pos>(tail.copy())
    moves.forEach {
        for (i in 1..it.count) { 
            when (it.dir) {
                'R' -> head.x++
                'L' -> head.x--
                'U' -> head.y++
                'D' -> head.y--
            }

            if (tail.farBehind(head)) {
                val diffx = head.x - tail.x
                val diffy = head.y - tail.y
                tail.x += min(abs(diffx), 1) * diffx.sign
                tail.y += min(abs(diffy), 1) * diffy.sign
                tailMoves.add(tail.copy())
            }
        }
    }
    return tailMoves.size
}

fun p2(moves: List<Move>): Int {
    val body = List<Pos>(10) { Pos(0, 0) }
    val head = body[0]
    val tail = body[9]

    val tailMoves = mutableSetOf<Pos>(tail.copy())
    moves.forEach {
        for (i in 1..it.count) { 
            when (it.dir) {
                'R' -> head.x++
                'L' -> head.x--
                'U' -> head.y++
                'D' -> head.y--
            }

            for (j in 1..9) {
                if (body[j].farBehind(body[j - 1])) {
                    val diffx = body[j - 1].x - body[j].x
                    val diffy = body[j - 1].y - body[j].y
                    body[j].x += min(abs(diffx), 1) * diffx.sign
                    body[j].y += min(abs(diffy), 1) * diffy.sign
                }
            }
            tailMoves.add(tail.copy())
        }
    }

    return tailMoves.size
}

fun main() {
    val data = getData(readInput())
    println(p2(data))
}
