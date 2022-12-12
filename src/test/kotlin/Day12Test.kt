import kotlin.test.Test
import kotlin.test.assertEquals

private val input = """
    Sabqponm
    abcryxxl
    accszExk
    acctuvwj
    abdefghi
""".trimIndent()

class Day12Test {
    @Test
    fun `fewest steps to top is 31`() {
        assertEquals(31, day12A(input))
    }

    @Test
    fun `fewest steps from elevation a to top is 29`() {
        assertEquals(29, day12B(input))
    }
}