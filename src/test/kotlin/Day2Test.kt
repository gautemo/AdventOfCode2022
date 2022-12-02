import kotlin.test.Test
import kotlin.test.assertEquals

private val input = """
    A Y
    B X
    C Z
""".trimIndent()

class Day2Test {
    @Test
    fun `strategy gives a total score of 15`(){
        val result = twoA(input)
        assertEquals(15, result)
    }

    @Test
    fun `strategy gives a total score of 12`() {
        val result = twoB(input)
        assertEquals(12, result)
    }
}