import shared.Point
import shared.getText

fun main() {
    val input = getText("day23.txt")
    println(day23A(input))
    println(day23B(input))
}

fun day23A(input: String): Int {
    val elves = toElves(input)
    val proposeOrder = mutableListOf('N', 'S', 'W', 'E')
    repeat(10) {
        moveElves(elves, proposeOrder)
        proposeOrder.add(proposeOrder.removeAt(0))
    }
    val xWidth = elves.maxOf { it.point.x } - elves.minOf { it.point.x } + 1
    val yWidth = elves.maxOf { it.point.y } - elves.minOf { it.point.y } + 1
    return (xWidth * yWidth) - elves.size
}

fun day23B(input: String): Int {
    val elves = toElves(input)
    val proposeOrder = mutableListOf('N', 'S', 'W', 'E')
    var round = 0
    while (true) {
        round++
        val moved = moveElves(elves, proposeOrder)
        if(!moved) return round
        proposeOrder.add(proposeOrder.removeAt(0))
    }
}

private fun toElves(input: String): List<Elf> {
    return input.lines().flatMapIndexed { y, line ->
        line.toCharArray().mapIndexed { x, c ->
            if(c == '#') {
                Elf(Point(x, y))
            } else {
                null
            }
        }
    }.filterNotNull()
}

private fun moveElves(elves: List<Elf>, proposeOrder: List<Char>): Boolean {
    for(elf in elves) {
        val alone = elf.adjacent().none { p -> elves.any { it.point == p } }
        if(!alone) {
            for(propose in proposeOrder) {
                elf.suggest = when {
                    propose == 'N' && elf.northAdjacent().none { elves.hasElfOn(it) } -> {
                        Point(elf.point.x, elf.point.y-1)
                    }
                    propose == 'S' && elf.southAdjacent().none { elves.hasElfOn(it) } -> {
                        Point(elf.point.x, elf.point.y+1)
                    }
                    propose == 'W' && elf.westAdjacent().none { elves.hasElfOn(it) } -> {
                        Point(elf.point.x-1, elf.point.y)
                    }
                    propose == 'E' && elf.eastAdjacent().none { elves.hasElfOn(it) } -> {
                        Point(elf.point.x+1, elf.point.y)
                    }
                    else -> null
                }
                if(elf.suggest != null) break
            }
        }
    }
    val suggestions = elves.mapNotNull { it.suggest }
    if(suggestions.isEmpty()) return false
    for(elf in elves) {
        if(suggestions.count { it == elf.suggest } == 1) {
            elf.point = elf.suggest!!
        }
        elf.suggest = null
    }
    return true
}

private fun List<Elf>.hasElfOn(point: Point) = any { it.point == point }

private class Elf(var point: Point, var suggest: Point? = null) {
    fun adjacent() = (northAdjacent() + southAdjacent() + westAdjacent() + eastAdjacent()).toSet()

    fun northAdjacent(): List<Point> {
        return listOf(
            Point(point.x-1, point.y-1),
            Point(point.x, point.y-1),
            Point(point.x+1, point.y-1),
        )
    }

    fun southAdjacent(): List<Point> {
        return listOf(
            Point(point.x-1, point.y+1),
            Point(point.x, point.y+1),
            Point(point.x+1, point.y+1),
        )
    }

    fun westAdjacent(): List<Point> {
        return listOf(
            Point(point.x-1, point.y-1),
            Point(point.x-1, point.y),
            Point(point.x-1, point.y+1),
        )
    }

    fun eastAdjacent(): List<Point> {
        return listOf(
            Point(point.x+1, point.y-1),
            Point(point.x+1, point.y),
            Point(point.x+1, point.y+1),
        )
    }
}