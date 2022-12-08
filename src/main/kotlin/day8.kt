import shared.getText
import shared.toLines
import shared.takeWhileInclusive

fun main(){
    val input = getText("day8.txt")
    println(day8A(input))
    println(day8B(input))
}

fun day8A(input: String): Int {
    val trees = toTrees(input)
    var count = 0
    for(y in trees.indices) {
        for((x, tree) in trees[y].withIndex()) {
            val visibleTreesLeft = treesLeft(tree, x, y, trees)
            val visibleTreesRight = treesRight(tree, x, y, trees)
            val visibleTreesUp = treesUp(tree, x, y, trees)
            val visibleTreesDown = treesDown(tree, x, y, trees)
            val isVisibleLeft = visibleTreesLeft.isEmpty() || visibleTreesLeft.all { it < tree }
            val isVisibleRight = visibleTreesRight.isEmpty() || visibleTreesRight.all { it < tree }
            val isVisibleUp = visibleTreesUp.isEmpty() || visibleTreesUp.all { it < tree }
            val isVisibleDown = visibleTreesDown.isEmpty() || visibleTreesDown.all { it < tree }
            if(isVisibleLeft || isVisibleRight || isVisibleUp || isVisibleDown) {
                count++
            }
        }
    }
    return count
}

fun day8B(input: String): Int {
    val trees = toTrees(input)
    var best = 0
    for(y in trees.indices) {
        for((x, tree) in trees[y].withIndex()) {
            val visibleTreesLeft = treesLeft(tree, x, y, trees)
            val visibleTreesRight = treesRight(tree, x, y, trees)
            val visibleTreesUp = treesUp(tree, x, y, trees)
            val visibleTreesDown = treesDown(tree, x, y, trees)
            best = maxOf(best, visibleTreesLeft.size * visibleTreesRight.size * visibleTreesUp.size * visibleTreesDown.size)
        }
    }
    return best
}

private fun toTrees(input: String): List<List<Int>> {
    return toLines(input).map { line -> line.map { it.digitToInt() } }
}

private fun treesLeft(height: Int, xPos: Int, yPos: Int, trees: List<List<Int>>): List<Int> {
    return trees[yPos].filterIndexed{ x, _ -> x < xPos }.reversed().takeWhileInclusive { it < height }
}

private fun treesRight(height: Int, xPos: Int, yPos: Int, trees: List<List<Int>>): List<Int> {
    return trees[yPos].filterIndexed{ x, _ -> x > xPos }.takeWhileInclusive { it < height }
}

private fun treesUp(height: Int, xPos: Int, yPos: Int, trees: List<List<Int>>): List<Int> {
    return trees.filterIndexed { y, _ -> y < yPos }.map { it[xPos] }.reversed().takeWhileInclusive { it < height }
}

private fun treesDown(height: Int, xPos: Int, yPos: Int, trees: List<List<Int>>): List<Int> {
    return trees.filterIndexed { y, _ -> y > yPos }.map { it[xPos] }.takeWhileInclusive { it < height }
}