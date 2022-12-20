import shared.Point
import shared.getText

fun main() {
    val input = getText("day17.txt")
    println(day17A(input))
    println(day17B(input))
}

fun day17A(input: String) = heightAfter(input, 2022)
fun day17B(input: String) = heightAfter(input, 1000000000000)

fun heightAfter(jets: String, totalRounds: Long): Long {
    val history = History(totalRounds, jets)
    var jet = 0
    var round = 0L
    val rocks = mutableListOf<Point>()
    while (round < totalRounds) {
        history.checkCanSkip(round, jet, rocks)?.let {
            round += it
        }
        val figure = nextFigure(round, rocks.maxOfOrNull { it.y } ?: -1)
        do {
            if(jets[jet % jets.length] == '>') {
                figure.pushRight(rocks)
            } else {
                figure.pushLeft(rocks)
            }
            val stopped = figure.down(rocks)
            if(stopped) {
                rocks.addAll(figure.points)
            }
            jet++
        } while (!stopped)
        round++
    }
    return rocks.maxOf { it.y } + 1 + history.addHeight
}

data class Figure(var points: List<Point>) {
    fun pushLeft(rocks: List<Point>) {
        if(points.minOf { it.x } == 0) return
        if(points.any { rocks.contains(Point(it.x-1, it.y)) }) return
        points = points.map { Point(it.x-1, it.y) }
    }

    fun pushRight(rocks: List<Point>) {
        if(points.maxOf { it.x } == 6) return
        if(points.any { rocks.contains(Point(it.x+1, it.y)) }) return
        points = points.map { Point(it.x+1, it.y) }
    }

    fun down(rocks: List<Point>): Boolean {
        if(points.minOf { it.y } == 0) return true
        if(points.any { rocks.contains(Point(it.x, it.y-1)) }) return true
        points = points.map { Point(it.x, it.y-1) }
        return false
    }

    companion object {
        fun init(vararg points: Point) = Figure(points.toList())
    }
}

private fun nextFigure(round: Long, highestRock: Int): Figure {
    val bottomY = highestRock + 4
    return when(round % 5) {
        0L -> Figure.init(Point(2, bottomY), Point(3, bottomY), Point(4, bottomY), Point(5, bottomY))
        1L -> Figure.init(Point(3, bottomY+2), Point(2, bottomY+1), Point(3, bottomY+1), Point(4, bottomY+1), Point(3, bottomY))
        2L -> Figure.init(Point(4, bottomY+2), Point(4, bottomY+1), Point(2, bottomY), Point(3, bottomY), Point(4, bottomY))
        3L -> Figure.init(Point(2, bottomY+3), Point(2, bottomY+2), Point(2, bottomY+1), Point(2, bottomY))
        else -> Figure.init(Point(2, bottomY+1), Point(3, bottomY+1), Point(2, bottomY), Point(3, bottomY))
    }
}

private class History(private val totalRounds: Long, private val jets: String) {
    private val startConditions = mutableMapOf<Triple<Long, Int, List<Int>>, Pair<Long, Int>>()
    var addHeight = 0L

    fun checkCanSkip(round: Long, jet: Int, rocks: List<Point>): Long? {
        if(addHeight != 0L) return null
        val highestRock = rocks.maxOfOrNull { it.y } ?: 0
        val landingSpot = (0..6).map { x ->
            rocks.maxOfOrNull { if(it.x == x) it.y - highestRock else -highestRock } ?: 0
        }
        val state = Triple(round % 5, jet % jets.length, landingSpot)
        return if(startConditions.containsKey(state)) {
            val diffRound = round - startConditions[state]!!.first
            val diffHeight = highestRock - startConditions[state]!!.second
            val roundsLeft = totalRounds - round
            addHeight = diffHeight * (roundsLeft / diffRound)
            return roundsLeft - (roundsLeft % diffRound)
        } else {
            startConditions[state] = round to highestRock
            null
        }
    }
}
