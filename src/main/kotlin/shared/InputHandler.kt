package shared

import java.io.File

fun getText(filename: String) = File(Thread.currentThread().contextClassLoader.getResource(filename)!!.toURI()).readText().trim()

fun String.chunks() = split("\n\n")
