import shared.getText
import shared.toLines

fun main() {
    val input = getText("day3.txt")
    println(day3A(input))
    println(day3B(input))
}

fun day3A(input: String): Int {
    val backpacks = toLines(input)
    return backpacks.sumOf {
        val middle = it.length/2
        val inCommon = itemInCommon(it.take(middle), it.takeLast(middle))
        inCommon.placeInAlphabet()
    }
}

fun day3B(input: String): Int {
    val groups = toLines(input).chunked(3)
    return groups.sumOf {
        val inCommon = itemInCommon(it[0], it[1], it[2])
        inCommon.placeInAlphabet()
    }
}

private fun itemInCommon(vararg items: String): Char {
    return items.first().first { itemToCheck -> items.all { it.contains(itemToCheck) } }
}

private fun Char.placeInAlphabet(): Int {
    if(code < 97) return code - 65 + 27
    return code - 96
}