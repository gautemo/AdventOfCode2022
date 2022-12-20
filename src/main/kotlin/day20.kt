import shared.getText
import kotlin.math.abs

fun main() {
    val input = getText("day20.txt")
    println(day20A(input))
    println(day20B(input))
}

fun day20A(input: String) = mix(input)
fun day20B(input: String) = mix(input, 10, 811589153)

private fun mix(input: String, times: Int = 1, decryptKey: Long = 1L): Long {
    val numbers = input.lines().mapIndexed { index, s ->
        Node(s.toInt() * decryptKey, index)
    }.toMutableList()
    repeat(times) { _ ->
        repeat(numbers.size) { index ->
            val from = numbers.indexOfFirst { it.originalIndex == index }
            val moves = numbers.first { it.originalIndex == index }.value
            if(moves != 0L) {
                var to = from + moves
                if (abs(to) >= numbers.size) {
                    to %= numbers.size - 1
                }
                if (to < 0) {
                    to += numbers.size - 1
                }
                if(from < to) {
                    numbers.add(to.toInt(), numbers.removeAt(from))
                }else if(from > to) {
                    numbers.add(to.toInt(), numbers[from])
                    numbers.removeAt(from+1)
                }
            }
        }
    }
    val zeroAt = numbers.indexOfFirst { it.value == 0L }
    return numbers[(zeroAt + 1000) % numbers.size].value +
            numbers[(zeroAt + 2000) % numbers.size].value +
            numbers[(zeroAt + 3000) % numbers.size].value
}

private class Node(val value: Long, val originalIndex: Int)

