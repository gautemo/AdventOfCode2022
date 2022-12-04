import kotlin.test.Test
import kotlin.test.assertEquals

private val input = """
    2-4,6-8
    2-3,4-5
    5-7,7-9
    2-8,3-7
    6-6,4-6
    2-6,4-8
""".trimIndent()

class Day4Test {
    @Test
    fun `2 pairs should have fully contained range`(){
        val result = day4A(input)
        assertEquals(2, result)
    }

    @Test
    fun `4 pairs have overlap`(){
        val result = day4B(input)
        assertEquals(4, result)
    }
}