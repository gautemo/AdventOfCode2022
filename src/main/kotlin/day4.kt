import shared.getText

fun main() {
    val input = getText("day4.txt")
    println(day4A(input))
    println(day4B(input))
}

fun day4A(input: String): Int {
    val pairs = inputToPairs(input)
    return pairs.count { it.first.contains(it.second) }
}

fun day4B(input: String): Int {
    val pairs = inputToPairs(input)
    return pairs.count { it.first.overlaps(it.second) }
}

private fun IntRange.contains(other: IntRange) = other.all { contains(it) } || all { other.contains(it) }

private fun IntRange.overlaps(other: IntRange) = other.any { contains(it) }

private fun inputToPairs(input: String): List<Pair<IntRange, IntRange>> {
    return input.lines().map { line ->
        val (a, b) = line.split(',').map {
            val (from, to) = it.split('-')
            from.toInt()..to.toInt()
        }
        Pair(a,b)
    }
}