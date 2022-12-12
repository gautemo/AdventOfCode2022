import shared.getText
import shared.placeInAlphabet

fun main() {
    val input = getText("day12.txt")
    println(day12A(input))
    println(day12B(input))
}

fun day12A(input: String): Int {
    val startY = input.lines().indexOfFirst { it.contains("S") }
    val startX = input.lines()[startY].indexOfFirst { it == 'S' }
    val goalY = input.lines().indexOfFirst { it.contains("E") }
    val goalX = input.lines()[goalY].indexOfFirst { it == 'E' }
    return bfs(input, Point(startX, startY), listOf(Point(goalX, goalY)), canGoUp)
}

fun day12B(input: String): Int {
    val startY = input.lines().indexOfFirst { it.contains("E") }
    val startX = input.lines()[startY].indexOfFirst { it == 'E' }
    val goals = mutableListOf<Point>()
    for(y in input.lines().indices) {
        for(x in 0 until input.lines()[y].length) {
            if(input.lines()[y][x] == 'a' || input.lines()[y][x] == 'S') {
                goals.add(Point(x, y))
            }
        }
    }
    return bfs(input, Point(startX, startY), goals, canGoDown)
}

private fun bfs(map: String, start: Point, goal: List<Point>, canGo: (from: Char, to: Char?) -> Boolean): Int {
    val explored = mutableListOf(start)
    val queue = mutableListOf(Pair(start, 0))
    while (queue.isNotEmpty()) {
        val check = queue.removeAt(0)
        if(goal.contains(check.first)) return check.second
        for(moveTo in possibleMoves(map, check.first, canGo).filter { !explored.contains(it) }) {
            queue.add(Pair(moveTo, check.second + 1))
            explored.add(moveTo)
        }
    }
    throw Exception("should have found goal by now")
}

private data class Point(val x: Int, val y: Int)

private fun String.at(point: Point) = lines().getOrNull(point.y)?.toCharArray()?.getOrNull(point.x)

private val canGoUp = fun(from: Char, to: Char?) = to != null && from.elevation() + 1 >= to.elevation()
private val canGoDown = fun(from: Char, to: Char?) = to != null && to.elevation() + 1 >= from.elevation()

private val possibleMoves = fun(map: String, point: Point, canGo: (from: Char, to: Char?) -> Boolean): List<Point>{
    val x = point.x
    val y = point.y
    return listOfNotNull(
        Point(x-1, y).let {
            if(canGo(map.at(point)!!, map.at(it))) it else null
        },
        Point(x, y-1).let {
            if(canGo(map.at(point)!!, map.at(it))) it else null
        },
        Point(x, y+1).let {
            if(canGo(map.at(point)!!, map.at(it))) it else null
        },
        Point(x+1, y).let {
            if(canGo(map.at(point)!!, map.at(it))) it else null
        },
    )
}

private fun Char.elevation() = if(this == 'S') 1 else if(this == 'E') 26 else this.placeInAlphabet()
