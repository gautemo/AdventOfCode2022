import shared.Point
import shared.getText
import kotlin.math.abs

fun main() {
    val input = getText("day15.txt")
    println(day15A(input, 2000000))
    println(day15B(input, 4000000))
}

fun day15A(input: String, rowToCheck: Int): Int {
    val pairs = input.lines().map { positions(it) }
    val mostLeft = pairs.minOf { minOf(it.first.x, it.second.x) - manhattanDist(it.first, it.second) }
    val mostRight = pairs.maxOf { maxOf(it.first.x, it.second.x) + manhattanDist(it.first, it.second) }
    return (mostLeft .. mostRight).count { x ->
        val toCheck = Point(x, rowToCheck)
        pairs.none { it.first == toCheck || it.second == toCheck } && pairs.any { toCheck.isCoveredBy(it) }
    }
}

fun day15B(input: String, max: Int): Long {
    val pairs = input.lines().map { positions(it) }
    for(y in 0 .. max) {
        var x = 0
        xLoop@while(x <= max) {
            val toCheck = Point(x, y)
            for(p in pairs){
                if(toCheck.isCoveredBy(p)){
                    x = (p.first.x + manhattanDist(p.first, p.second) + 1) - abs(toCheck.y - p.first.y)
                    continue@xLoop
                }
            }
            return x * 4000000L + y
        }
    }
    return -1
}

private fun positions(input: String): Pair<Point, Point> {
    val (sensorInput, beaconInput) = input.split(":")
    return position(sensorInput) to position(beaconInput)
}

private fun position(input: String): Point {
    val x = Regex("""(?<=x=)-?\d+""").find(input)!!.value.toInt()
    val y = Regex("""(?<=y=)-?\d+""").find(input)!!.value.toInt()
    return Point(x,y)
}

private fun manhattanDist(a: Point, b: Point) = abs(a.x-b.x) + abs(a.y-b.y)

private fun Point.isCoveredBy(pair: Pair<Point, Point>) = manhattanDist(pair.first, this) <= manhattanDist(pair.first, pair.second)