import shared.getText

fun main() {
    val input = getText("day21.txt")
    println(day21A(input))
    println(day21B(input))
}

fun day21A(input: String) = yellGame(input)
fun day21B(input: String) = yellGame(input, true)

private fun yellGame(input: String, withUnkown: Boolean = false): Long {
    val monkeys = input.lines().associate {
        it.split(":").let { (monkey, yell) -> monkey to yell.trim() }
    }.toMutableMap()
    if(withUnkown) monkeys["humn"] = "x"

    fun replace(monkey: String): String {
        if(monkeys[monkey]!!.toLongOrNull() != null || monkeys[monkey]!! == "x") return monkeys[monkey]!!
        val (left, symbol, right) = monkeys[monkey]!!.split(" ")
        val replaceLeft = replace(left)
        val replaceRight = replace(right)
        if(replaceLeft.toLongOrNull() != null && replaceRight.toLongOrNull() != null) {
            return when(symbol) {
                "+" -> (replaceLeft.toLong() + replaceRight.toLong()).toString()
                "-" -> (replaceLeft.toLong() - replaceRight.toLong()).toString()
                "*" -> (replaceLeft.toLong() * replaceRight.toLong()).toString()
                else -> (replaceLeft.toLong() / replaceRight.toLong()).toString()
            }
        }
        return "($replaceLeft$symbol$replaceRight)"
    }
    if(!withUnkown) return replace("root").toLong()
    val (left, _, right) = monkeys["root"]!!.split(" ")
    var equationLeft = replace(left)
    var equationRight = replace(right).toLong()
    while (equationLeft != "x") {
        equationLeft = equationLeft.removePrefix("(").removeSuffix(")")
        val symbolAt = equationLeft.rootSymbolAt()
        val symbol = equationLeft[symbolAt]
        val a = equationLeft.substring(0, symbolAt)
        val b = equationLeft.substring(symbolAt+1, equationLeft.length)
        if(a.toLongOrNull() == null) {
            when(symbol) {
                '+' -> equationRight -= b.toLong()
                '-' -> equationRight += b.toLong()
                '*' -> equationRight /= b.toLong()
                '/' -> equationRight *= b.toLong()
            }
            equationLeft = a
        } else {
            when(symbol) {
                '+' -> equationRight -= a.toLong()
                '-' -> equationRight = a.toLong() - equationRight
                '*' -> equationRight /= a.toLong()
                '/' -> equationRight = a.toLong() / equationRight
            }
            equationLeft = b
        }
    }
    return equationRight
}

private fun String.rootSymbolAt(): Int {
    for((i,c) in withIndex()){
        if(listOf('+', '-', '*', '/').contains(c) && take(i).count { it == '(' } == take(i).count { it == ')' }){
            return i
        }
    }
    throw Exception("should have root symbol")
}