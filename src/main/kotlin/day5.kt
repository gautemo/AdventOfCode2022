import shared.getText

fun main() {
    val input = getText("day5.txt")
    println(day5A(input))
    println(day5B(input))
}

fun day5A(input: String): String {
    val (drawing, procedures) = input.split("\n\n")
    val stacks = readDrawing(drawing)
    for(procedure in procedures.lines()) {
        val moveN = procedure.split(' ')[1].toInt()
        val moveFrom = procedure.split(' ')[3].toInt() - 1
        val moveTo = procedure.split(' ')[5].toInt() - 1
        repeat(moveN) {
            stacks[moveTo].add(stacks[moveFrom].removeLast())
        }
    }
    return stacks.topWord()
}

fun day5B(input: String): String {
    val (drawing, procedures) = input.split("\n\n")
    val stacks = readDrawing(drawing)
    for(procedure in procedures.lines()) {
        val moveN = procedure.split(' ')[1].toInt()
        val moveFrom = procedure.split(' ')[3].toInt() - 1
        val moveTo = procedure.split(' ')[5].toInt() - 1
        stacks[moveTo].addAll(stacks[moveFrom].pop(moveN))
    }
    return stacks.topWord()
}

private fun readDrawing(drawing: String): List<MutableList<Char>> {
    val stacks = List(9) { mutableListOf<Char>() }
    for(line in drawing.lines().reversed()) {
        Regex("[A-Z]").findAll(line).forEach { letter ->
            val stack = letter.range.first / 4
            stacks[stack] += letter.value.first()
        }
    }
    return stacks
}

private fun MutableList<Char>.pop(n: Int): List<Char> {
    val take = takeLast(n)
    repeat(n) {
        removeLast()
    }
    return take
}

private fun List<MutableList<Char>>.topWord() = mapNotNull { it.lastOrNull() }.joinToString("")
