package dev.rockyj.advent_kt

private fun maxX(symbols: MutableMap<Pair<Int, Int>, String>): Int {
    var x = 0
    symbols.forEach {
        if (it.key.second > x) {
            x = it.key.second
        }
    }
    return x
}

private fun maxY(symbols: MutableMap<Pair<Int, Int>, String>): Int {
    var y = 0
    symbols.forEach {
        if (it.key.first > y) {
            y = it.key.first
        }
    }
    return y
}

private fun part1(input: List<String>) {
    val symbols = mutableMapOf<Pair<Int, Int>, String>()

    input.forEachIndexed { idx, line ->
        val sls = line.trim().split("")
        sls.subList(1, sls.size - 1).forEachIndexed { idx2, s -> symbols.put(Pair(idx, idx2), s.trim()) }
    }

    // find expansions
    val expRows = (0..maxX(symbols)).filter { x ->
        (0..maxY(symbols)).all { y ->
            symbols[Pair(x, y)] == "."
        }
    }
    val expCols = (0..maxY(symbols)).filter { x ->
        (0..maxX(symbols)).all { y ->
            symbols[Pair(y, x)] == "."
        }
    }

    // println(expRows)
    // println(expCols)

    val galaxies = symbols.filter {
        it.value == "#"
    }

    val pairs = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()

    galaxies.forEach { gal1 ->
        galaxies.forEach { gal2 ->
            if ((gal1.key != gal2.key)) { // && !pairs.contains(Pair(gal2.key, gal1.key))) {
                pairs.plusAssign(Pair(gal1.key, gal2.key))
            }
        }
    }

    // println(galaxies)
    // println(pairs)
    // println(pairs.size)

    val dist = pairs.map {
        val pair1 = it.first
        val pair2 = it.second

        val left = if (pair1.second < pair2.second) {
            pair1
        } else {
            pair2
        }

        val right = if (pair1.second > pair2.second) {
            pair1
        } else {
            pair2
        }

        val up = if (pair1.first < pair2.first) {
            pair1
        } else {
            pair2
        }

        val down = if (pair1.first > pair2.first) {
            pair1
        } else {
            pair2
        }

        val exp1 = expCols.filter { cols -> cols > left.second && cols < right.second }.size
        val exp2 = expRows.filter { rows -> rows > up.first && rows < down.first }.size

        val mul = 1000000 - 1 // or 1

        val res =
            Math.abs(right.second - left.second).toLong() + Math.abs(down.first - up.first)
                .toLong() + ((exp1 + exp2) * mul).toLong()

        res
    }.sum()

    println(dist / 2)
}


fun main() {
    val lines = fileToArr("day11_2.txt")
    timed {
        part1(lines)
    }
}