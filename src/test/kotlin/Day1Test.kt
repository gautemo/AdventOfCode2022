import kotlin.test.Test
import kotlin.test.assertEquals

private val input = """
    1000
    2000
    3000

    4000

    5000
    6000

    7000
    8000
    9000

    10000
""".trimIndent()

class Day1Test {
    @Test
    fun `elf with most calories should carry 24000 calories`(){
        val result = oneA(input)
        assertEquals(24000, result)
    }

    @Test
    fun `top three elves carry 45000 calories`() {
        val result = oneB(input)
        assertEquals(45000, result)
    }
}