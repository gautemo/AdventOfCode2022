import shared.getText

/*
This day was hard. This code is slow. This code is messy. But it works if you want to wait some hours.
Not sure if I want to spend more time on it, making it more readable or re-thinking the solution to be faster.
 */

fun main() {
    val input = getText("day16.txt")
    println(day16A(input))
    println(day16B(input))
}

fun day16A(input: String): Int {
    val valves = input.lines().map {
        val name = it.split(" ")[1]
        val rate = Regex("""(?<=rate=)\d+""").find(it)!!.value.toInt()
        val tunnels = Regex("""(?<=tunnels? leads? to valves? ).*""").find(it)!!.value.split(",").map { v -> v.trim() }
        Valve(name, rate, tunnels)
    }
    return search(valves.find { it.name == "AA" }!!, valves)
}

data class Valve(val name: String, val rate: Int, val tunnels: List<String>)

private fun search(start: Valve, valves: List<Valve>): Int {
    val ignore = mutableListOf(Triple(0, "AA", setOf<String>()))
    val queue = mutableListOf(Path(0, 0, listOf(start.name)))
    var best = 0
    var count = 0
    while (queue.isNotEmpty()) {
        val check = queue.removeAt(0)
        if(check.been.size != count) {
            if(queue.size > 1000){
                val b = queue.maxOf { it.total }
                queue.removeIf { it.total * 2 < b }
            }
            count = check.been.size
            println(check.been.size)
            println(ignore.size)
            println(queue.size)
        }
        if(check.allOpened(valves)){
            val end = check.wait(30 - check.been.size)
            best = maxOf(best, end.res())
            continue
        }
        if(check.been.size == 30) {
            best = maxOf(best, check.res())
            continue
        }
        val currentValve = valves.getByName(check.been.last())
        if(currentValve.rate > 0 && !check.opened.contains(currentValve.name)){
            val newPath = check.open(currentValve.rate)
            if(ignore.none { newPath.total <= it.first && newPath.been.last() == it.second && newPath.opened == it.third }) {
                queue.add(newPath)
                ignore.removeIf {
                    it.second == newPath.been.last() && newPath.opened.containsAll(it.third) && newPath.total >= it.first
                }
                ignore.add(Triple(newPath.total, newPath.been.last(), newPath.opened))
            }
        }
        for(move in currentValve.tunnels) {
            val newPath = check.next(move)
            if(ignore.none { newPath.total <= it.first && newPath.been.last() == it.second && newPath.opened == it.third }) {
                queue.add(newPath)
                ignore.removeIf {
                    it.second == newPath.been.last() && newPath.opened.containsAll(it.third) && newPath.total >= it.first
                }
                ignore.add(Triple(newPath.total, newPath.been.last(), newPath.opened))
            }
        }
    }
    println(best)
    return best
}

private data class Path(val total: Int, var pressure: Int, val been: List<String>, val opened: Set<String> = setOf()){
    fun open(addPressure: Int): Path {
        return Path(total + pressure, pressure + addPressure, been.plus(been.last()), opened.plus(been.last()))
    }

    fun next(to: String): Path {
        return Path(total + pressure, pressure, been.plus(to), opened)
    }

    fun allOpened(valves: List<Valve>) = valves.filter { it.rate > 0 }.all { opened.contains(it.name) }

    fun wait(minutes: Int) = Path(total + (minutes * pressure), pressure, been, opened)

    fun res() = total + pressure
}

private fun List<Valve>.getByName(name: String) = find { it.name == name}!!
private fun List<Valve>.getByName2(name: String) = find { it.name == name}

fun day16B(input: String): Int {
    val valves = input.lines().map {
        val name = it.split(" ")[1]
        val rate = Regex("""(?<=rate=)\d+""").find(it)!!.value.toInt()
        val tunnels = Regex("""(?<=tunnels? leads? to valves? ).*""").find(it)!!.value.split(",").map { v -> v.trim() }
        Valve(name, rate, tunnels)
    }
    return search3(valves)
}

private fun search3(valves: List<Valve>): Int {
    val ignore = mutableListOf(Triple(0, setOf("AA", "AA"), setOf<String>()))
    val queue = mutableListOf(Approach("AA" to "AA"))
    var best = 0
    var count = 0
    while (queue.isNotEmpty()) {
        val check = queue.removeAt(0)
        if(check.moves.first.length/2 != count) {
            if(queue.size > 1000){
                val b = queue.maxOf { it.total(valves) }
                queue.removeIf { it.total(valves) * 1.3 < b }
            }
            count = check.moves.first.length/2
            println(count)
            println(ignore.size)
            println(queue.size)
        }
        val on = check.on()
        if(check.moves.first.length/2 == 26) {
            best = maxOf(best, check.total(valves))
            continue
        }
        if(check.allOpened(valves)){
            if(check.total(valves) > best) {
                best = check.total(valves)
                queue.add(Approach("${check.moves.first}ww" to "${check.moves.second}ww"))
            }
            continue
        }
        val v1 = valves.getByName(on.first)
        val v2 = valves.getByName(on.second)
        val canOpenV1 = v1.rate > 0 && !check.hasOpened(v1.name)
        val canOpenV2 = v2.rate > 0 && !check.hasOpened(v2.name)
        val goTo = mutableSetOf<Approach>()
        if(canOpenV1 && canOpenV2 && on.first != on.second) {
            goTo.add(Approach("${check.moves.first}${on.first}" to "${check.moves.second}${on.second}"))
        }
        if(canOpenV1){
            for(move in v2.tunnels) {
                goTo.add(Approach("${check.moves.first}${on.first}" to "${check.moves.second}$move"))
            }
        }
        if(canOpenV2) {
            for(move in v1.tunnels) {
                goTo.add(Approach("${check.moves.first}$move" to "${check.moves.second}${on.second}"))
            }
        }
        for(move1 in v1.tunnels) {
            for(move2 in v2.tunnels) {
                goTo.add(Approach("${check.moves.first}$move1" to "${check.moves.second}$move2"))
            }
        }
        val bestOpened = goTo.maxOf { it.hasOpened().size }
        ignore.removeIf { it.third.size * 1.3 < bestOpened && bestOpened > 4 }

        val canGoTo = goTo.fold(listOf<Approach>()) { acc, approach ->
            if(acc.none { other -> other.moves.toList().toSet() == approach.moves.toList().toSet() }){
                acc + listOf(approach)
            } else{
                acc
            }
        }.filter { !it.isBadMove() }
        for(approach in canGoTo) {
            val total = approach.total(valves)
            val onSet = setOf(approach.on().first, approach.on().second)
            val hasOpened = approach.hasOpened()
            if(ignore.none { total <= it.first && onSet == it.second && it.third.containsAll(hasOpened)}) {
                queue.add(approach)
                val newIgnore = Triple(total, onSet, hasOpened)
                ignore.removeIf {
                    newIgnore.first >= it.first && newIgnore.second == it.second && newIgnore.third == it.third
                }
                ignore.add(newIgnore)
            }
        }
    }
    println(best)
    return best
}

private data class Approach(val moves: Pair<String, String>) {

    fun on() = Pair(moves.first.takeLast(2), moves.second.takeLast(2))
    fun total(valves: List<Valve>): Int {
        if(moves.first.length <= 2) return 0
        var total = 0
        var pressure = 0
        for (i in 2 until moves.first.length step 2) {
            total += pressure
            if(moves.first.substring(i, i+2) == moves.first.substring(i-2, i)){
                val valve = valves.getByName2(moves.first.substring(i, i+2))
                pressure += valve?.rate ?: 0
            }
            if(moves.second.substring(i, i+2) == moves.second.substring(i-2, i)){
                val valve = valves.getByName2(moves.second.substring(i, i+2))
                pressure += valve?.rate ?: 0
            }
        }
        return total + pressure
    }

    fun allOpened(valves: List<Valve>) = valves.filter { it.rate > 0 }.all { hasOpened(it.name) }

    fun hasOpened(name: String): Boolean {
        return moves.first.windowed(4, 2).any { it == "$name$name" } ||
                moves.second.windowed(4, 2).any { it == "$name$name" }
    }

    fun isBadMove(): Boolean {
        if(moves.first.length >= 6 && moves.first.takeLast(6).let { it.substring(0,2) == it.substring(4,6) }){
            return true
        }
        if(moves.second.length >= 6 && moves.second.takeLast(6).let { it.substring(0,2) == it.substring(4,6) }){
            return true
        }
        return false
    }

    fun hasOpened(): Set<String> {
        return setOf(
            *moves.first.windowed(4, 2).filter { it.take(2) == it.takeLast(2) }.toTypedArray(),
            *moves.second.windowed(4, 2).filter { it.take(2) == it.takeLast(2) }.toTypedArray(),
        )
    }
}