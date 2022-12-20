import kotlin.test.Test
import kotlin.test.assertEquals

private val input = """
    1
    2
    -3
    3
    -2
    0
    4
""".trimIndent()

class Day20Test {
    @Test
    fun `sum of grove coordinates is 3`() {
        assertEquals(3, day20A(input))
    }

    @Test
    fun `sum of grove coordinates is 1623178306 with decryption key and 10 mix`() {
        assertEquals(1623178306, day20B(input))
    }
}