package dev.rockyj.advent_kt

private fun path(start: Pair<Int, Int>, field: Map<Pair<Int, Int>, String>, pathVisited: MutableList<Pair<Int, Int>>) {
    val curr = field[Pair(start.first, start.second)]
    val last = pathVisited.last()

    if (curr == "|") {
        if (Pair(start.first, start.second + 1) == last) {
            path(Pair(start.first, start.second - 1), field, pathVisited.plus(start).toMutableList())
        } else {
            path(Pair(start.first, start.second + 1), field, pathVisited.plus(start).toMutableList())
        }
    }
    if (curr == "-") {
        if (Pair(start.first + 1, start.second) == last) {
            path(Pair(start.first - 1, start.second), field, pathVisited.plus(start).toMutableList())
        } else {
            path(Pair(start.first + 1, start.second), field, pathVisited.plus(start).toMutableList())
        }
    }
    if (curr == "L") {
        if (Pair(start.first, start.second - 1) == last) {
            path(Pair(start.first + 1, start.second), field, pathVisited.plus(start).toMutableList())
        } else {
            path(Pair(start.first, start.second - 1), field, pathVisited.plus(start).toMutableList())
        }
    }
    if (curr == "J") {
        if (Pair(start.first - 1, start.second) == last) {
            path(Pair(start.first, start.second - 1), field, pathVisited.plus(start).toMutableList())
        } else {
            path(Pair(start.first - 1, start.second), field, pathVisited.plus(start).toMutableList())
        }
    }
    if (curr == "7") {
        if (Pair(start.first - 1, start.second) == last) {
            path(Pair(start.first, start.second + 1), field, pathVisited.plus(start).toMutableList())
        } else {
            path(Pair(start.first - 1, start.second), field, pathVisited.plus(start).toMutableList())
        }
    }
    if (curr == "F") {
        if (Pair(start.first + 1, start.second) == last) {
            path(Pair(start.first, start.second + 1), field, pathVisited.plus(start).toMutableList())
        } else {
            path(Pair(start.first + 1, start.second), field, pathVisited.plus(start).toMutableList())
        }
    }
    if (curr == "." || curr == "S") {
        println(pathVisited.size / 2)
    }
}

private fun part1(input: List<String>) {
    val fieldM = mutableMapOf<Pair<Int, Int>, String>()

    input.forEachIndexed { idx1, line ->
        val pipes = line.trim().split("").filter { it != "" }
        pipes.forEachIndexed { idx2, s ->
            fieldM.put(Pair(idx2, idx1), s)
        }
    }

    val field = fieldM.toMap()
    val start = field.keys.find { k -> field[k] == "S" } ?: throw RuntimeException("no start found")

    println(start)

    if (field[Pair(start.first + 1, start.second)] == "-") {
        path(Pair(start.first + 1, start.second), field, mutableListOf(start))
    }
    if (field[Pair(start.first - 1, start.second)] == "-") {
        path(Pair(start.first - 1, start.second), field, mutableListOf(start))
    }
    if (field[Pair(start.first + 1, start.second)] == "7") {
        path(Pair(start.first + 1, start.second), field, mutableListOf(start))
    }


    if (field[Pair(start.first, start.second + 1)] == "|") {
        path(Pair(start.first, start.second + 1), field, mutableListOf(start))
    }
    if (field[Pair(start.first, start.second - 1)] == "|") {
        path(Pair(start.first, start.second - 1), field, mutableListOf(start))
    }

    if (field[Pair(start.first, start.second + 1)] == "L") {
        path(Pair(start.first, start.second + 1), field, mutableListOf(start))
    }
    if (field[Pair(start.first + 1, start.second)] == "J") {
        path(Pair(start.first + 1, start.second), field, mutableListOf(start))
    }
}

fun main() {
    val lines = fileToArr("day10_2.txt")
    timed {
        part1(lines)
    }
}