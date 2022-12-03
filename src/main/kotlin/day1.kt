import shared.getText
import shared.toChunks
import shared.toIntLines

fun main() {
    val input = getText("day1.txt")
    println(day1A(input))
    println(day1B(input))
}

fun day1A(input: String): Int{
    return getElves(input).max()
}

fun day1B(input: String): Int{
    val elves = getElves(input)
    return elves.sortedDescending().take(3).sum()
}

private fun getElves(input: String) = toChunks(input).map { toIntLines(it) }.map { it.sum() }