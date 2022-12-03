import kotlin.test.Test
import kotlin.test.assertEquals

private val input = """
    vJrwpWtwJgWrhcsFMMfFFhFp
    jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
    PmmdzqPrVvPwwTWBwg
    wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
    ttgJtRGJQctTZtZT
    CrZsJsPPZsGzwwsLwLmpwMDw
""".trimIndent()

class Day3Test {

    @Test
    fun `priority sum should be 157`(){
        val result = threeA(input)
        assertEquals(157, result)
    }

    @Test
    fun `priority sum should be 70 for badges`(){
        val result = threeB(input)
        assertEquals(70, result)
    }
}