import shared.Point
import shared.getText

fun main() {
    val input = getText("day22.txt")
    println(day22A(input))
    println(day22B(input))
}

fun day22A(input: String): Int {
    val board = Board(input)
    board.followInstructions()
    return board.password()
}

fun day22B(input: String): Int {
    val board = Board(input, true)
    board.followInstructions()
    return board.password()
}

private class Board(input: String, val cubeMode: Boolean = false) {
    var instructions = input.lines().last()
    private val map = mutableMapOf <Point, Char>()
    var on = Point(input.lines()[0].indexOfFirst { it == '.' }, 0)
    val facing = Facing()

    init {
        for(y in 0 until input.lines().size-2) {
            for(x in 0 until input.lines()[y].length) {
                map[Point(x, y)] = input.lines()[y][x]
            }
        }
    }

    fun followInstructions() {
        while (instructions.isNotEmpty()) {
            val command = instructions.takeWhile { it != 'R' && it != 'L' }.ifEmpty { instructions.take(1) }
            instructions = instructions.removePrefix(command)
            when(command) {
                "R" -> facing.turnR()
                "L" -> facing.turnL()
                else -> {
                    repeat(command.toInt()) {
                        val topY = map.filter { it.key.x == on.x && it.value != ' ' }.minOf { it.key.y }
                        val bottomY = map.filter { it.key.x == on.x && it.value != ' ' }.maxOf { it.key.y }
                        val lefX = map.filter { it.key.y == on.y && it.value != ' ' }.minOf { it.key.x }
                        val rightX = map.filter { it.key.y == on.y && it.value != ' ' }.maxOf { it.key.x }
                        when(facing.dir) {
                            'R' -> next(1, 0, Point(lefX, on.y))
                            'D' -> next(0, 1, Point(on.x, topY))
                            'L' -> next(-1, 0, Point(rightX, on.y))
                            else -> next(0, -1, Point(on.x, bottomY))
                        }
                    }
                }
            }
        }
    }

    private fun setOn(point: Point, newDir: Char? = null) {
        if(map[point] == '.') {
            on = point
            newDir?.let { facing.dir = it }
        }
    }

    private fun next(xDir: Int, yDir: Int, warpTo: Point) {
        val next = Point(on.x+xDir, on.y+yDir)
        if(map[next] == null || map[next] == ' ') {
            if(cubeMode) cubeTurn(next) else setOn(warpTo)
        } else {
            setOn(next)
        }
    }

    fun password() = 1000 * (on.y+1) + 4 * (on.x+1) + facing.value

    private fun cubeTurn(to: Point) {
        when {
            to.x == 150 -> {
                setOn(Point(99, 149 - to.y), 'L')
            }
            to.x == 49 && (0..49).contains(to.y) -> {
                setOn(Point(0, 149-to.y), 'R')
            }
            to.y == -1 && (50..99).contains(to.x) -> {
                setOn(Point(0, 100 + to.x), 'R')
            }
            to.y == -1 && (100..149).contains(to.x) -> {
                setOn(Point(to.x - 100, 199), 'U')
            }
            to.x == 49 && (50..99).contains(to.y) -> {
                setOn(Point(to.y-50, 100), 'D')
            }
            to.x == 100 && (50..99).contains(to.y) -> {
                setOn(Point(50 + to.y, 49), 'U')
            }
            to.y == 50 && (100..149).contains(to.x) -> {
                setOn(Point(99, to.x - 50), 'L')
            }
            to.y == 99 && (0..49).contains(to.x) -> {
                setOn(Point(50, to.x + 50), 'R')
            }
            to.x == -1 && (100..149).contains(to.y) -> {
                setOn(Point(50, 149 - to.y), 'R')
            }
            to.x == 100 && (100..149).contains(to.y) -> {
                setOn(Point(149, 49 - (to.y - 100)), 'L')
            }
            to.y == 150 && (50..99).contains(to.x) -> {
                setOn(Point(49, to.x + 100), 'L')
            }
            to.x == -1 && (150..199).contains(to.y) -> {
                setOn(Point(to.y - 100, 0), 'D')
            }
            to.x == 50 && (150..199).contains(to.y) -> {
                setOn(Point(to.y - 100, 149), 'U')
            }
            to.y == 200 -> {
                setOn(Point(to.x + 100, 0), 'D')
            }
        }
    }
}

private class Facing {
    var dir = 'R'
    val value: Int
        get(){
            return when(dir) {
                'R' -> 0
                'D' -> 1
                'L' -> 2
                else -> 3
            }
        }

    fun turnR() {
        dir = when(dir) {
            'R' -> 'D'
            'D' -> 'L'
            'L' -> 'U'
            else -> 'R'
        }
    }

    fun turnL() {
        dir = when(dir) {
            'R' -> 'U'
            'D' -> 'R'
            'L' -> 'D'
            else -> 'L'
        }
    }
}