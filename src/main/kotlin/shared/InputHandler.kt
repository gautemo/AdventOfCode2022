package shared

import java.io.File

fun getText(filename: String) = File(Thread.currentThread().contextClassLoader.getResource(filename)!!.toURI()).readText().trimEnd()

fun String.chunks() = split("\n\n")
