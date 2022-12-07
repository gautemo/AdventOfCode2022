import kotlin.test.Test
import kotlin.test.assertEquals

private val input = """
    ${'$'} cd /
    ${'$'} ls
    dir a
    14848514 b.txt
    8504156 c.dat
    dir d
    ${'$'} cd a
    ${'$'} ls
    dir e
    29116 f
    2557 g
    62596 h.lst
    ${'$'} cd e
    ${'$'} ls
    584 i
    ${'$'} cd ..
    ${'$'} cd ..
    ${'$'} cd d
    ${'$'} ls
    4060174 j
    8033020 d.log
    5626152 d.ext
    7214296 k
""".trimIndent()

class Day7Test {
    @Test
    fun `directories under 100000 has total size of 95437`(){
        val result = day7A(input)
        assertEquals(95437, result)
    }



    @Test
    fun `should delete dir with size 24933642`(){
        val result = day7B(input)
        assertEquals(24933642, result)
    }
}