import kotlin.test.Test
import kotlin.test.assertEquals

private val input = """
    [1,1,3,1,1]
    [1,1,5,1,1]

    [[1],[2,3,4]]
    [[1],4]

    [9]
    [[8,7,6]]

    [[4,4],4,4]
    [[4,4],4,4,4]

    [7,7,7,7]
    [7,7,7]

    []
    [3]

    [[[]]]
    [[]]

    [1,[2,[3,[4,[5,6,7]]]],8,9]
    [1,[2,[3,[4,[5,6,0]]]],8,9]
""".trimIndent()

class Day13Test {
    @Test
    fun `sum of indices of pairs with right order is 13`(){
        assertEquals(13, day13A(input))
    }

    @Test
    fun `decoder key is 140`(){
        assertEquals(140, day13B(input))
    }
}