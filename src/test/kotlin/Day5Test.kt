import kotlin.test.Test
import kotlin.test.assertEquals

private val input = """
    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
""".trimIndent()

class Day5Test {
    @Test
    fun `rearrangement gives CMZ`(){
        val result = day5A(input)
        assertEquals("CMZ", result)
    }

    @Test
    fun `CrateMover 9001 rearrangement gives MCD`(){
        val result = day5B(input)
        assertEquals("MCD", result)
    }
}