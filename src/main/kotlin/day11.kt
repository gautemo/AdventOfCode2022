import shared.chunks
import shared.getText
import shared.lcm

fun main() {
    val input = getText("day11.txt")
    println(day11A(input))
    println(day11B(input))
}

fun day11A(input: String): Long {
    return findMonkeyBusiness(input, 20)
}

fun day11B(input: String): Long {
    return findMonkeyBusiness(input, 10000, true)
}

private fun findMonkeyBusiness(input: String, rounds: Int, worryFree: Boolean = false): Long {
    val divideByList = mutableListOf<Long>()
    val monkeys = input.chunks().map { monkeyInput ->
        val lines = monkeyInput.lines()
        val items = lines[1].split(":")[1].split(",").map { it.trim().toLong() }
        val operation = fun(old: Long): Long {
            val toCalculate = lines[2].split("=")[1].replace("old", old.toString())
            return if(toCalculate.contains('+')) {
                val (a, b) = toCalculate.split('+').map { it.trim().toLong() }
                a + b
            } else {
                val (a, b) = toCalculate.split('*').map { it.trim().toLong()}
                a * b
            }
        }
        val divideBy = lines[3].split(" ").last().toInt()
        divideByList.add(divideBy.toLong())
        val toMonkeyTrue = lines[4].split(" ").last().toInt()
        val toMonkeyFalse = lines[5].split(" ").last().toInt()
        val test = fun(value: Long): Int {
            return if(value % divideBy == 0L) toMonkeyTrue else toMonkeyFalse
        }
        Monkey(items.toMutableList(), operation, test)
    }
    val leastCommonMultiplier = divideByList.reduce { acc, i -> lcm(acc, i) }

    repeat(rounds) {
        monkeys.forEach { monkey ->
            while (monkey.items.isNotEmpty()) {
                val item = monkey.items.removeAt(0)
                var newItemValue = monkey.operation(item) % leastCommonMultiplier
                if(!worryFree) newItemValue /= 3
                val toMonkey = monkey.test(newItemValue)
                monkeys[toMonkey].items.add(newItemValue)
                monkey.inspectedItems++
            }
        }
    }
    return monkeys.map { it.inspectedItems }.sortedDescending().take(2).let { it[0] * it[1] }
}

private class Monkey(val items: MutableList<Long>, val operation: (old: Long) -> Long, val test: (value: Long) -> Int) {
    var inspectedItems = 0L
}