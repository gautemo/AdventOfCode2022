package shared

import java.io.File

fun getText(filename: String) = File(Thread.currentThread().contextClassLoader.getResource(filename)!!.toURI()).readText().trim()

fun toLines(text: String) = text.split("\n")

fun toChunks(text: String) = text.split("\n\n")

fun toIntLines(text: String) = toLines(text).map { it.toInt() }