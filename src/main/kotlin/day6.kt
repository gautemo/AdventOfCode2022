import shared.getText

fun main() {
    val input = getText("day6.txt")
    println(day6A(input))
    println(day6B(input))
}

fun day6A(input: String): Int {
    return findMarker(input, 4)
}

fun day6B(input: String): Int {
    return findMarker(input, 14)
}

private fun findMarker(input: String, size: Int): Int {
    for(i in size..input.length) {
        val check = input.substring(i-size,i).toSet()
        if(check.size == size){
            return i
        }
    }
    return -1
}