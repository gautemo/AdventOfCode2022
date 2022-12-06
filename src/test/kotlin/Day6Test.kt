import kotlin.test.Test
import kotlin.test.assertEquals

class Day6Test {
    @Test
    fun `datastream has packet marker at 7`(){
        val input = "mjqjpqmgbljsphdztnvjfqwrcgsmlb"
        val result = day6A(input)
        assertEquals(7, result)
    }

    @Test
    fun `datastream has first start-of-message marker at 19`(){
        val input = "mjqjpqmgbljsphdztnvjfqwrcgsmlb"
        val result = day6B(input)
        assertEquals(19, result)
    }
}