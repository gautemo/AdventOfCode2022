import kotlin.test.Test
import kotlin.test.assertEquals

private val input = """
    2,2,2
    1,2,2
    3,2,2
    2,1,2
    2,3,2
    2,2,1
    2,2,3
    2,2,4
    2,2,6
    1,2,5
    3,2,5
    2,1,5
    2,3,5
""".trimIndent()

class Day18Test {
    @Test
    fun `64 surface area`(){
        assertEquals(64, day18A(input))
    }

    @Test
    fun `58 exterior surface area`(){
        assertEquals(58, day18B(input))
    }
}