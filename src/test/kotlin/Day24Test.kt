import kotlin.test.Test
import kotlin.test.assertEquals

private val input = """
    #.######
    #>>.<^<#
    #.<..<<#
    #>v.><>#
    #<^v^^>#
    ######.#
""".trimIndent()

class Day24Test {
    @Test
    fun `fastest way is 18 steps`() {
        assertEquals(18, day24A(input))
    }

    @Test
    fun `fastest way goal, start, goal is 54 steps`() {
        assertEquals(54, day24B(input))
    }
}