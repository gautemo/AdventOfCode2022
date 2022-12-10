import shared.getText

fun main() {
    val input = getText("day10.txt")
    println(day10A(input))
    println(day10B(input))
}

fun day10A(input: String): Int {
    val signalStrengths = mutableListOf<Int>()
    handleInstructions(input, 20) { x, cycle, isCycleShift ->
        if(isCycleShift) {
            signalStrengths.add(cycle * x)
        }
    }
    return signalStrengths.sum()
}

fun day10B(input: String): String {
    var image = ""
    handleInstructions(input, 40) { x, cycle, isCycleShift ->
        val crtPos = (cycle - 1) % 40
        image += if(crtPos in x-1..x+1){ "#" } else { "." }
        if(isCycleShift) {
            image += "\n"
        }
    }
    return image.trim()
}

private fun handleInstructions(input: String, firstCycle: Int, onCycle: (x: Int, cycle: Int, isCycleShift: Boolean) -> Unit) {
    val instructions = input.lines()
    var x = 1
    var cycle = 0
    var nextCycle = firstCycle

    fun bumpCycle(){
        cycle++
        val isCycleShift = cycle == nextCycle
        if(isCycleShift) {
            nextCycle += 40
        }
        onCycle(x, cycle, isCycleShift)
    }

    instructions.forEach { instruction ->
        if(instruction == "noop") {
            bumpCycle()
        } else {
            val (_, add) = instruction.split(" ")
            bumpCycle()
            bumpCycle()
            x += add.toInt()
        }
    }
}