import kotlin.test.Test
import kotlin.test.assertEquals

private const val input = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>"

class Day17Test {
    @Test
    fun `tower is 3068 units tall`(){
        assertEquals(3068, day17A(input))
    }

    @Test
    fun `tower is 1514285714288 units tall after 1000000000000 rocks`() {
        assertEquals(1514285714288, day17B(input))
    }
}