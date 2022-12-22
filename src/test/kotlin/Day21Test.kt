import kotlin.test.Test
import kotlin.test.assertEquals

private val input = """
    root: pppw + sjmn
    dbpl: 5
    cczh: sllz + lgvd
    zczc: 2
    ptdq: humn - dvpt
    dvpt: 3
    lfqf: 4
    humn: 5
    ljgn: 2
    sjmn: drzm * dbpl
    sllz: 4
    pppw: cczh / lfqf
    lgvd: ljgn * ptdq
    drzm: hmdt - zczc
    hmdt: 32
""".trimIndent()

class Day21Test {
    @Test
    fun `root yells 152`() {
        assertEquals(152, day21A(input))
    }

    @Test
    fun `should yell 301`() {
        assertEquals(301, day21B(input))
    }
}