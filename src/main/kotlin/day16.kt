import shared.getText

fun main() {
    val input = getText("day16.txt")
    println(day16A(input))
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
