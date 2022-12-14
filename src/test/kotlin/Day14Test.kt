import kotlin.test.Test
import kotlin.test.assertEquals

private val input = """
    498,4 -> 498,6 -> 496,6
    503,4 -> 502,4 -> 502,9 -> 494,9
""".trimIndent()

class Day14Test {
    @Test
    fun `24 units of sand will rest`() {
        assertEquals(24, day14A(input))
    }

    @Test
    fun `93 units of sand will rest with floor`() {
        assertEquals(93, day14B(input))
    }
}