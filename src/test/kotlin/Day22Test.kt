import kotlin.test.Test
import kotlin.test.assertEquals

private val input = """
            ...#
            .#..
            #...
            ....
    ...#.......#
    ........#...
    ..#....#....
    ..........#.
            ...#....
            .....#..
            .#......
            ......#.
    
    10R5L5R10L4R5L5
""".trimIndent()

class Day22Test {
    @Test
    fun `password is 6032`() {
        assertEquals(6032, day22A(input))
    }
}