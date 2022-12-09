import shared.getText
import kotlin.math.abs

fun main(){
    val input = getText("day9.txt")
    println(day9A(input))
    println(day9B(input))
}

fun day9A(input: String): Int {
    val motions = input.lines()
    val head = Knot(0, 0)
    val tail = Knot(0, 0)
    val tailBeen = mutableSetOf(tail.copy())
    motions.forEach {
        val (dir, steps) = it.split(" ")
        repeat(steps.toInt()) {
            head.moveMotion(dir)
            tail.follow(head)
            tailBeen.add(tail.copy())
        }
    }
    return tailBeen.size
}

fun day9B(input: String): Int {
    val motions = input.lines()
    val head = Knot(0, 0)
    val tails = List(9){ Knot(0, 0) }
    val tailBeen = mutableSetOf(tails.last().copy())
    motions.forEach {
        val (dir, steps) = it.split(" ")
        repeat(steps.toInt()) {
            head.moveMotion(dir)
            (listOf(head) + tails).windowed(2).forEach {(follow, move) ->
                move.follow(follow)
            }
            tailBeen.add(tails.last().copy())
        }
    }
    return tailBeen.size
}

private data class Knot(var x: Int, var y: Int) {
    fun follow(other: Knot) {
        val distX = other.x - x
        val distY = other.y - y
        if(abs(distX) > 1) {
            x += if(distX > 0) 1 else -1
            y += if(distY > 0) 1 else if(distY < 0) -1 else 0
        } else if (abs(distY) > 1) {
            y += if(distY > 0) 1 else -1
            x += if(distX > 0) 1 else if(distX < 0) -1 else 0
        }
    }

    fun moveMotion(dir: String) {
        when(dir) {
            "R" -> x++
            "L" -> x--
            "U" -> y--
            "D" -> y++
        }
    }
}

