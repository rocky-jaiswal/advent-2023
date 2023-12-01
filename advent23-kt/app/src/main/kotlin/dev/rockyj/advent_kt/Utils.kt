package dev.rockyj.advent_kt

import java.io.File

fun fileToArr(filePath: String): List<String> {
    val res = ClassLoader.getSystemClassLoader().getResource(filePath)
    return File(res.file).readLines()
}