import kotlin.test.Test
import kotlin.test.assertEquals

private val input = """
    30373
    25512
    65332
    33549
    35390
""".trimIndent()

class Day8Test {
    @Test
    fun `should be 21 visible trees`(){
        val result = day8A(input)
        assertEquals(21, result)
    }

    @Test
    fun `highest scenic score is 8`(){
        val result = day8B(input)
        assertEquals(8, result)
    }
}