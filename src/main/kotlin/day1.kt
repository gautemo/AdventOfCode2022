import shared.getText
import shared.toChunks
import shared.toIntLines

fun main() {
    val input = getText("day1.txt")
    println(oneA(input))
    println(oneB(input))
}

fun oneA(input: String): Int{
    return getElves(input).max()
}

fun oneB(input: String): Int{
    val elves = getElves(input)
    return elves.sortedDescending().take(3).sum()
}

private fun getElves(input: String) = toChunks(input).map { toIntLines(it) }.map { it.sum() }