import kotlin.test.Test
import kotlin.test.assertEquals

private val input = """
    ....#..
    ..###.#
    #...#.#
    .#...##
    #.###..
    ##.#.##
    .#..#..
""".trimIndent()

class Day23Test {
    @Test
    fun `ground tiles is 110`() {
        assertEquals(110, day23A(input))
    }

    @Test
    fun `no moves after 20`() {
        assertEquals(20, day23B(input))
    }
}