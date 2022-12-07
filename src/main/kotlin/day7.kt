import shared.getText
import shared.toLines

fun main() {
    val input = getText("day7.txt")
    println(day7A(input))
    println(day7B(input))
}

fun day7A(input: String): Int {
    val rootDir = getRootDir(input)
    return rootDir.allDirs().sumOf {
        if(it.getSize() <= 100000) it.getSize() else 0
    }
}

fun day7B(input: String): Int {
    val rootDir = getRootDir(input)
    val unusedSpace = 70000000 - rootDir.getSize()
    val toDelete = 30000000 - unusedSpace
    return rootDir.allDirs().minOf {
        if(it.getSize() > toDelete) it.getSize() else Int.MAX_VALUE
    }
}

private fun getRootDir(input: String): Dir {
    val terminalOutput = toLines(input)
    val rootDir = Dir("/", null)
    var currentDir = rootDir
    terminalOutput.forEach {
        when {
            it == "$ cd /" -> currentDir = rootDir
            it.contains("dir") -> {
                currentDir.dirs.add(Dir(it.split(" ")[1], currentDir))
            }
            it =="$ cd .." ->  {
                currentDir = currentDir.parent ?: throw Exception("too far out")
            }
            Regex("""\$ cd .+""").matches(it) -> {
                currentDir = currentDir.dirs.find { dir -> dir.name == it.split(" ")[2] } ?: throw Exception("dir not found")
            }
            Regex("""\d+ .+""").matches(it) -> {
                currentDir.files.add(it.split(" ")[0].toInt())
            }
        }
    }
    return rootDir
}

private class Dir(val name: String, val parent: Dir?, val files: MutableList<Int> = mutableListOf(), val dirs: MutableList<Dir> = mutableListOf()) {
    fun getSize(): Int {
        return files.sum() + dirs.sumOf { it.getSize() }
    }

    fun allDirs(): List<Dir> {
        return listOf(this) + dirs.flatMap { it.allDirs() }
    }
}