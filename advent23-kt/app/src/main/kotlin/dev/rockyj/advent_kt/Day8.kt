package dev.rockyj.advent_kt

private fun part2(input: List<String>) {
    val mapM = mutableMapOf<String, Pair<String, String>>()

    val steps = input.get(0).trim().split("").filter { it != "" }

    input.subList(2, input.size).forEach { line ->
        val parts = line.split(" = ")
        val lr = parts.last().split(",")

        mapM.put(parts.first(), Pair(lr.first().substring(1), lr.last().substring(1, 4)))
    }

    val mapX = mapM.toMap()
    println(mapX.keys.last())

    var starts = mapX.filter { it.key.endsWith("A") }.map { it.key }

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
            starts.map { mapX[it]!!.first }
        } else {
            starts.map { mapX[it]!!.second }
        }

        if (next.all { it.endsWith("Z") }) {
            done = true
        }

        stepCounter += 1
        starts = next
    }

    println(counter)
}

private fun part1(input: List<String>) {
    val mapM = mutableMapOf<String, Pair<String, String>>()

    val steps = input.get(0).trim().split("").filter { it != "" }
    // println(steps)

    input.subList(2, input.size).forEach { line ->
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
    // part2(lines) // brute force does not work!!
}