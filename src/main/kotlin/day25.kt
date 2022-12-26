import shared.getText
import kotlin.math.pow

fun main() {
    val input = getText("day25.txt")
    println(day25A(input))
}

fun day25A(input: String): String {
    val decimalSum = input.lines().map { line ->
        line.snafuToLong()
    }.reduce { acc, number -> acc + number }
    var fiveNumeral = decimalSum.toString(5)
    var result = ""
    while (fiveNumeral.isNotEmpty()) {
        val last = fiveNumeral.last()
        fiveNumeral = fiveNumeral.take(fiveNumeral.length-1)
        when(last) {
            '4' -> {
                result = "-$result"
                fiveNumeral = fiveNumeral.incrementFiveNumeral()
            }
            '3' -> {
                result = "=$result"
                fiveNumeral = fiveNumeral.incrementFiveNumeral()
            }
            else -> {
                result = "$last$result"
            }
        }
    }
    return result
}

private fun String.snafuToLong(): Long {
    return mapIndexed { i, c ->
        val multiply = 5.0.pow(length-1-i).toLong()
        when(c) {
            '-' -> -1
            '=' -> -2
            else -> c.digitToInt().toLong()
        } * multiply
    }.reduce { acc, number -> acc + number }
}

private fun String.incrementFiveNumeral(): String {
    if(isEmpty()) return "1"
    if(last().digitToInt() < 4) {
        return "${take(length-1)}${last().digitToInt() + 1}"
    }
    return "${take(length-1).incrementFiveNumeral()}0"
}