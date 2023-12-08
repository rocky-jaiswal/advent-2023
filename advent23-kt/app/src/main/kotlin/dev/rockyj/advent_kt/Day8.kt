package dev.rockyj.advent_kt

private fun part1(input: List<String>) {
    val mapM = mutableMapOf<String, Pair<String, String>>()

    val steps = input.get(0).trim().split("").filter { it != "" }
    // println(steps)

    input.subList(2, input.size - 1).forEach { line ->
        val parts = line.split(" = ")

        val lr = parts.last().split(",")

        mapM.put(parts.first(), Pair(lr.first().substring(1), lr.last().substring(1, 4)))
    }

    val map = mapM.toMap()
    // println(map)

    var dirs = map.get("AAA")
    var counter = 0
    var stepCounter = 0
    var done = false

    while (!done) {
        counter += 1

        val step = if (stepCounter < steps.size) {
            steps.get(stepCounter)
        } else {
            stepCounter = 0
            steps.get(stepCounter)
        }

        val next = if (step == "L") {
            dirs!!.first
        } else {
            dirs!!.second
        }

        if (next == "ZZZ") {
            done = true
        }

        stepCounter += 1
        dirs = map.get(next)
    }

    println(counter)
}

fun main() {
    val lines = fileToArr("day8_2.txt")
    part1(lines)
}