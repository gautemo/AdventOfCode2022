import kotlinx.coroutines.*
import shared.getText

fun main() = runBlocking {
    val input = getText("day19.txt")
    println(day19A(input))
    println(day19B(input))
}

suspend fun day19A(input: String): Int  = withContext(Dispatchers.Default) {
    input.lines().map { s ->
        async {
            Factory.init(s).let { searchBest(it) * it.id }
        }
    }.sumOf { it.await() }
}

suspend fun day19B(input: String): Int = withContext(Dispatchers.Default) {
    input.lines().take(3).map{
        async {
            searchBest(Factory.init(it, 32))
        }
    }.fold(1){ acc, deferred -> acc * deferred.await() }
}

private fun searchBest(start: Factory): Int {
    val queue = mutableSetOf(start)
    var best = 0
    var giveUpAndHopeForBest = Int.MAX_VALUE
    while (queue.isNotEmpty() && giveUpAndHopeForBest > 0) {
        giveUpAndHopeForBest--
        val factory = queue.maxBy { it.heuristic() }
        queue.remove(factory)
        if(factory.geodes > best) best = factory.geodes
        queue.addAll(factory.tick())
        queue.removeIf {
            it.bestOutcome() <= best
        }
    }
    return best
}

private data class Factory(
    val id: Int,
    private val oreForOreRobot: Int,
    private val oreForClayRobot: Int,
    private val oreAndClayForObsidianRobot: Pair<Int, Int>,
    private val oreAndObsidianForGeodeRobot: Pair<Int, Int>,
    private val maxMin: Int,
    private var minute: Int = 0,
    private var ore: Int = 0,
    private var clay: Int = 0,
    private var obsidian: Int = 0,
    var geodes: Int = 0,
    private var oreRobot: Int = 1,
    private var clayRobot: Int = 0,
    private var obsidianRobot: Int = 0,
    private var geodeRobot: Int = 0,
) {
    fun tick(): List<Factory> {
        if(minute == maxMin) return listOf()
        val buildOreRobot = ore >= oreForOreRobot && oreRobot < maxOf(oreForOreRobot, oreForClayRobot, oreAndClayForObsidianRobot.first, oreAndObsidianForGeodeRobot.first)
        val buildClayRobot = ore >= oreForClayRobot && clayRobot < oreAndClayForObsidianRobot.second
        val buildObsidianRobot = oreAndClayForObsidianRobot.let { ore >= it.first && clay >= it.second } && obsidianRobot < oreAndObsidianForGeodeRobot.second
        val buildGeodeRobot = oreAndObsidianForGeodeRobot.let { ore >= it.first && obsidian >= it.second }
        ore += oreRobot
        clay += clayRobot
        obsidian += obsidianRobot
        geodes += geodeRobot
        minute++
        val addToQueue = mutableListOf<Factory>()
        if(buildOreRobot) {
            addToQueue.add(this.copy().apply {
                oreRobot++
                ore -= oreForOreRobot
            })
        }
        if(buildClayRobot) {
            addToQueue.add(this.copy().apply {
                clayRobot++
                ore -= oreForClayRobot
            })
        }
        if(buildObsidianRobot) {
            addToQueue.add(this.copy().apply {
                obsidianRobot++
                ore -= oreAndClayForObsidianRobot.first
                clay -= oreAndClayForObsidianRobot.second
            })
        }
        if(buildGeodeRobot) {
            addToQueue.add(this.copy().apply {
                geodeRobot++
                ore -= oreAndObsidianForGeodeRobot.first
                obsidian -= oreAndObsidianForGeodeRobot.second
            })
        }
        return addToQueue.plus(this)
    }

    fun heuristic() = geodeRobot * 100 + obsidianRobot * 8 + clayRobot * 4 + oreRobot

    fun bestOutcome(): Int {
        val maxExtraGeodeRobots = maxMin - minute
        var sum = geodes
        for(i in 0 until  maxExtraGeodeRobots){
            sum += geodeRobot + i
        }
        return sum
    }

    companion object {
        fun init(input: String, maxMin: Int = 24): Factory {
            val ints = Regex("""\d+""").findAll(input).map { it.value.toInt() }.toList()
            return Factory(ints[0], ints[1], ints[2], ints[3] to ints[4], ints[5] to ints[6], maxMin)
        }
    }
}