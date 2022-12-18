import shared.getText

fun main() {
    val input = getText("day18.txt")
    println(day18A(input))
    println(day18B(input))
}

fun day18A(input: String): Int {
    val grid = input.lines().map { line ->
        line.split(",").map { it.toInt() }.let { XYZ(it[0], it[1], it[2]) }
    }
    return grid.sumOf {
        it.sides().count { side -> !grid.contains(side) }
    }
}

fun day18B(input: String): Int {
    val lava = input.lines().map { line ->
        line.split(",").map { it.toInt() }.let { XYZ(it[0], it[1], it[2]) }
    }
    val minX = lava.minOf { it.x }
    val maxX = lava.maxOf { it.x }
    val minY = lava.minOf { it.y }
    val maxY = lava.maxOf { it.y }
    val minZ = lava.minOf { it.z }
    val maxZ = lava.maxOf { it.z }

    return lava.sumOf {  cube ->
        cube.sides().count { side ->
            !lava.contains(side) && dfsSearchWater(side, lava,minX..maxX, minY..maxY, minZ..maxZ)
        }
    }
}

fun dfsSearchWater(start: XYZ, lava: List<XYZ>, x: IntRange, y: IntRange, z: IntRange): Boolean {
    val explored = mutableListOf<XYZ>()
    val queue = mutableSetOf(start)
    while (queue.isNotEmpty()) {
        val check = queue.last()
        queue.remove(check)
        if(!x.contains(check.x) || !y.contains(check.y) || !z.contains(check.z)) return true
        explored.add(check)
        queue.addAll(check.sides().filter { !explored.contains(it) && !lava.contains(it) })
    }
    return false
}

data class XYZ(val x: Int, val y: Int, val z: Int) {
    fun sides(): List<XYZ> {
        return listOf(
            XYZ(x - 1, y, z),
            XYZ(x, y - 1, z),
            XYZ(x, y, z - 1),
            XYZ(x + 1, y, z),
            XYZ(x, y + 1, z),
            XYZ(x, y, z + 1),
        )
    }
}