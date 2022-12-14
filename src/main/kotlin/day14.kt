import shared.Point
import shared.getText

fun main() {
    val input = getText("day14.txt")
    println(day14A(input))
    println(day14B(input))
}

fun day14A(input: String) = fallingSand(input)
fun day14B(input: String) = fallingSand(input, true)

private fun fallingSand(input: String, hasFloor: Boolean = false): Int {
    val cave = createCave(input)
    val start = Point(500, 0)
    val bottom = cave.maxOf { it.key.y } + 2
    while (true) {
        var dropping = start
        while (true) {
            when {
                dropping.y + 1 == bottom && hasFloor -> {
                    cave[dropping] = '+'
                    break
                }
                dropping.y + 1 == bottom -> {
                    return cave.count { it.value == '+' }
                }
                cave[dropping.down()] == null -> dropping = dropping.down()
                cave[dropping.downLeft()] == null -> dropping = dropping.downLeft()
                cave[dropping.downRight()] == null -> dropping = dropping.downRight()
                else -> {
                    if(dropping == start){
                        return cave.count { it.value == '+' } + 1
                    }
                    cave[dropping] = '+'
                    break
                }
            }
        }
    }
}

private fun createCave(input: String): MutableMap<Point, Char> {
    val cave = mutableMapOf<Point, Char>()
    input.lines().forEach {
        val points = it.split("->").map { point ->
            point.trim().split(",").let { (x,y) -> Point(x.toInt(), y.toInt()) }
        }
        points.windowed(2).forEach { (a,b) ->
            for(y in minOf(a.y, b.y) .. maxOf(a.y, b.y)) {
                for(x in minOf(a.x, b.x) .. maxOf(a.x, b.x)) {
                    cave[Point(x, y)] = '#'
                }
            }
        }
    }
    return cave
}

private fun Point.down() = Point(x, y+1)
private fun Point.downLeft() = Point(x-1, y+1)
private fun Point.downRight() = Point(x+1, y+1)