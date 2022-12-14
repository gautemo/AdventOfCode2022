import shared.chunks
import shared.getText

fun main() {
    val input = getText("day13.txt")
    println(day13A(input))
    println(day13B(input))
}

fun day13A(input: String): Int {
    var sum = 0
    input.chunks().forEachIndexed { index, s ->
        val left = Packet(s.lines().first())
        val right = Packet(s.lines().last())
        if(left.compareTo(right) == -1) sum += index + 1
    }
    return sum
}

fun day13B(input: String): Int {
    val d1 = Packet("[[2]]")
    val d2 = Packet("[[6]]")
    val packets = input.lines().filter { it.isNotEmpty() }.map { Packet(it) } + d1 + d2
    val sortedPackets = packets.sorted()
    return (sortedPackets.indexOf(d1) + 1) * (sortedPackets.indexOf(d2) + 1)
}

private class Packet(val value: String): Comparable<Packet>{

    override fun compareTo(other: Packet): Int {
        var left = value
        var right = other.value
        while (true) {
            when {
                left.isInt() && right.isInt() -> {
                    return left.toInt().compareTo(right.toInt())
                }
                !left.isInt() && !right.isInt() -> {
                    val firstLeft = left.popFirst { newValue -> left = newValue }
                    val firstRight = right.popFirst { newValue -> right = newValue }
                    if (firstLeft == null && firstRight == null) return 0
                    if (firstLeft == null) return -1
                    if (firstRight == null) return 1
                    val compareFirst = firstLeft.compareTo(firstRight)
                    if (compareFirst != 0) return compareFirst
                }
                left.isInt() -> {
                    left = "[$left]"
                }
                right.isInt() -> {
                    right = "[$right]"
                }
            }
        }
    }

    private fun String.popFirst(update: (newValue: String) -> Unit): Packet? {
        if(this == "[]") return null
        val commaIndex = firstRootComma()
        if(commaIndex == null) {
            val firstPacket = Packet(substring(1, length-1))
            update(removeRange(1, length-1))
            return firstPacket
        }
        val firstPacket = Packet(substring(1, commaIndex))
        update(removeRange(1, commaIndex+1))
        return firstPacket
    }

    private fun String.firstRootComma(): Int? {
        for((i,c) in withIndex()){
            if(c == ',' && take(i).count { it == '[' } - 1 == take(i).count { it == ']' }){
                return i
            }
        }
        return null
    }

    private fun String.isInt() = toIntOrNull() != null
}