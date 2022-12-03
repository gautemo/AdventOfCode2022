import shared.getText
import shared.toLines

fun main() {
    val input = getText("day2.txt")
    println(day2A(input))
    println(day2B(input))
}

fun day2A(input: String): Int{
    val rounds = toLines(input).map {
        val pair = it.split(" ")
        val opponent = when(pair[0]) {
            "A" -> Rock()
            "B" -> Paper()
            "C" -> Scissors()
            else -> throw Exception()
        }
        val you = when(pair[1]) {
            "X" -> Rock()
            "Y" -> Paper()
            "Z" -> Scissors()
            else -> throw Exception()
        }
        Pair(you, opponent)
    }
    return rounds.sumOf { it.first.fight(it.second) + it.first.points }
}

fun day2B(input: String): Int{
    val rounds = toLines(input).map {
        val pair = it.split(" ")
        when(pair[0]) {
            "A" -> {
                when(pair[1]) {
                    "X" -> return@map Pair(Scissors(), Rock())
                    "Y" -> return@map Pair(Rock(), Rock())
                    "Z" -> return@map Pair(Paper(), Rock())
                    else -> throw Exception()
                }
            }
            "B" -> {
                when(pair[1]) {
                    "X" -> return@map Pair(Rock(), Paper())
                    "Y" -> return@map Pair(Paper(), Paper())
                    "Z" -> return@map Pair(Scissors(), Paper())
                    else -> throw Exception()
                }
            }
            "C" -> {
                when(pair[1]) {
                    "X" -> return@map Pair(Paper(), Scissors())
                    "Y" -> return@map Pair(Scissors(), Scissors())
                    "Z" -> return@map Pair(Rock(), Scissors())
                    else -> throw Exception()
                }
            }
            else -> throw Exception()
        }
    }
    return rounds.sumOf { it.first.fight(it.second) + it.first.points }
}

private sealed class Item {
    abstract val points: Int
    abstract fun fight(item: Item): Int
}

private class Rock : Item() {
    override val points = 1
    override fun fight(item: Item): Int {
        return when(item) {
            is Rock -> 3
            is Paper -> 0
            is Scissors -> 6
        }
    }
}

private class Paper : Item() {
    override val points = 2
    override fun fight(item: Item): Int {
        return when(item) {
            is Rock -> 6
            is Paper -> 3
            is Scissors -> 0
        }
    }
}

private class Scissors : Item() {
    override val points = 3
    override fun fight(item: Item): Int {
        return when(item) {
            is Rock -> 0
            is Paper -> 6
            is Scissors -> 3
        }
    }
}