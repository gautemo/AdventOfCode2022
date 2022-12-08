package shared

fun <T> List<T>.takeWhileInclusive(predicate: (T) -> Boolean): List<T> {
    var continueToNext = true
    return takeWhile {
        val shouldContinue = continueToNext
        continueToNext = predicate(it)
        shouldContinue
    }
}