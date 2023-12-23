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

    // add expansions
    var maxX = maxX(symbols)
    var maxY = maxY(symbols)
    expRows.forEachIndexed { idx, exp ->
        (exp + 2 + idx..maxY + idx + 1).reversed().forEach {
            (0..maxX).forEach { x ->
                symbols[Pair(it, x)] = symbols[Pair(it - 1, x)]!!
            }
        }
        (0..maxX).forEach { x ->
            symbols[Pair(exp + 1 + idx, x)] = "."
        }
    }


    maxX = maxX(symbols)
    maxY = maxY(symbols)
    expCols.forEachIndexed { idx, exp ->
        (exp + 2 + idx..maxX + idx + 1).reversed().forEach {
            (0..maxY).forEach { y ->
                symbols[Pair(y, it)] = symbols[Pair(y, it - 1)]!!
            }
        }
        (0..maxY).forEach { y ->
            symbols[Pair(y, exp + 1 + idx)] = "."
        }
    }

    val galaxies = symbols.filter {
        it.value == "#"
    }

    val pairs = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()

    galaxies.forEach { gal1 ->
        galaxies.forEach { gal2 ->
            if (gal1.key != gal2.key) {
                pairs.plusAssign(Pair(gal1.key, gal2.key))
            }
        }
    }

    // println(galaxies)
    // println(pairs)
    // println(pairs.size)

    val dist = pairs.map {
        Math.abs(it.first.first - it.second.first) + Math.abs(it.first.second - it.second.second)
    }.sum()

    println(dist / 2)
}


fun main() {
    val lines = fileToArr("day11_2.txt")
    timed {
        part1(lines)
    }
}