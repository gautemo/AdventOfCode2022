package shared

fun <T> List<T>.takeWhileInclusive(predicate: (T) -> Boolean): List<T> {
    var continueToNext = true
    return takeWhile {
        val shouldContinue = continueToNext
        continueToNext = predicate(it)
        shouldContinue
    }
}

fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)
fun lcm(a: Long, b: Long): Long = a / gcd(a, b) * b

fun Char.placeInAlphabet(): Int {
    if(code < 97) return code - 65 + 27
    return code - 96
}