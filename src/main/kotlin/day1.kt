import shared.chunks
import shared.getText

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

private fun getElves(input: String) = input.chunks().map { chunk -> chunk.lines().map { it.toInt() } }.map { it.sum() }