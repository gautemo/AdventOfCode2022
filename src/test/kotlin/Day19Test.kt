import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

private val input = """
    Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
    Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.
""".trimIndent()

class Day19Test {
    @Test
    fun `quality level sum is 33`() {
        runBlocking {
            assertEquals(33, day19A(input))
        }
    }

    @Test
    fun `multiply geodes is 56 times 62`() {
        runBlocking {
            assertEquals(56 * 62, day19B(input))
        }
    }
}