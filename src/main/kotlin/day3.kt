import shared.getText
import shared.placeInAlphabet

fun main() {
    val input = getText("day3.txt")
    println(day3A(input))
    println(day3B(input))
}

fun day3A(input: String): Int {
    return input.lines().sumOf {
        val middle = it.length/2
        val inCommon = itemInCommon(it.take(middle), it.takeLast(middle))
        inCommon.placeInAlphabet()
    }
}

fun day3B(input: String): Int {
    return input.lines().chunked(3).sumOf {
        val inCommon = itemInCommon(it[0], it[1], it[2])
        inCommon.placeInAlphabet()
    }
}

private fun itemInCommon(vararg items: String): Char {
    return items.first().first { itemToCheck -> items.all { it.contains(itemToCheck) } }
}
