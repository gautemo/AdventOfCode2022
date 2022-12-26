import shared.Point
import shared.getText

fun main() {
    val input = getText("day24.txt")
    println(day24A(input))
    println(day24B(input))
}

fun day24A(input: String) = bfsSearch(input) - 1
fun day24B(input: String) = bfsSearch(input, Triple(false, false, false))

private fun bfsSearch(input: String, goals: Triple<Boolean, Boolean, Boolean> = Triple(false, true, true)): Int {
    val queue = mutableListOf(BlizzardMap(input, Point(1, 0), 0, goals))
    val history = mutableListOf<String>()
    while (queue.isNotEmpty()) {
        val check = queue.removeAt(0)
        if (check.goals.toList().all { it }) {
            return check.minute
        } else {
            queue.addAll(check.next().filter {
                !history.contains(it.state()) && queue.none { q -> q.state() == it.state() }
            })
        }
        val maxGoalsFound = queue.maxOf { it.goals.toList().count { g -> g } }
        queue.removeIf {
            it.goals.toList().count { g -> g } < maxGoalsFound
        }
        history.add(check.state())
    }
    throw Exception()
}

private class BlizzardMap(
    private val input: String,
    val on: Point,
    val minute: Int,
    val goals: Triple<Boolean, Boolean, Boolean>,
) {
    private val nextBlizzards = mutableListOf<Point>()
    private val maxY = input.lines().size - 2
    private val maxX = input.lines().first().length - 2

    init {
        for((y, line) in input.lines().withIndex()) {
            for((x, c) in line.withIndex()) {
                val blizzard = when (c) {
                    '^' -> {
                        val toY = (maxY - ((minute + 1) % maxY) + y) % maxY
                        Point(x, if(toY == 0) maxY else toY)
                    }
                    '>' -> {
                        Point(((minute + x) % maxX)+1, y)
                    }
                    'v' -> {
                        Point(x, ((minute + y) % maxY)+1)
                    }
                    '<' -> {
                        val toX = (maxX - ((minute + 1) % maxX) + x) % maxX
                        Point(if(toX == 0) maxX else toX, y)
                    }
                    else -> null
                }
                if (blizzard != null) nextBlizzards.add(blizzard)
            }
        }
    }

    fun next(): List<BlizzardMap> {
        return listOf(
            on,
            Point(on.x - 1, on.y),
            Point(on.x + 1, on.y),
            Point(on.x, on.y - 1),
            Point(on.x, on.y + 1),
        ).filter {
             (it.x == 1 && it.y == 0) ||
                    (it.x == maxX && it.y == maxY + 1)
                    || (it.x in 1..maxX
                            && it.y in 1..maxY
                            && !nextBlizzards.contains(it)
                    )
        }.map {
            val onStart = it.x == 1 && it.y == 0
            val onGoal = it.x == maxX && it.y == maxY + 1
            val newGoals = Triple(
                goals.first || onGoal,
                goals.second || (goals.first && onStart),
                goals.first && goals.second && onGoal,
            )
            BlizzardMap(input, it, minute + 1, newGoals)
        }
    }

    fun state() = "${on.x},${on.y}.${minute % maxX}.${minute % maxY}.$goals"
}
