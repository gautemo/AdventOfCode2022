import kotlin.test.Test
import kotlin.test.assertEquals

private val input = """
    1=-0-2
    12111
    2=0=
    21
    2=01
    111
    20012
    112
    1=-1=
    1-12
    12
    1=
    122
""".trimIndent()

class Day25Test {
    @Test
    fun `bob need snafu number 2=-1=0`() {
        assertEquals("2=-1=0", day25A(input))
    }
}