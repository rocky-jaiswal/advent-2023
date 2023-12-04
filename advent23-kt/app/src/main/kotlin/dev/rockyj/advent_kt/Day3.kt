package dev.rockyj.advent_kt

private fun part1(input: List<String>) {
    val symbols = mutableMapOf<Pair<Int, Int>, String>()
    var lenX = 0

    input.forEachIndexed { idx, line ->
        val sls = line.trim().split("")
        lenX = sls.subList(1, sls.size - 1).size
        sls.subList(1, sls.size - 1).forEachIndexed { idx2, s -> symbols.put(Pair(idx, idx2), s.trim()) }
    }

    val numbers = mutableListOf<Pair<String, Pair<Int, Int>>>()
    var s = ""

    symbols.forEach { symb ->
        if (symb.value.matches(Regex("\\d"))) {
            s += symb.value
        }
        if (symb.key.second == lenX - 1) {
            if (s.isNotBlank() && symb.value.matches(Regex("\\d"))) {
                numbers.add(Pair(s, Pair(symb.key.first, symb.key.second)))
            }
            if (s.isNotBlank() && !symb.value.matches(Regex("\\d"))) {
                numbers.add(Pair(s, Pair(symb.key.first, symb.key.second - 1)))
            }
            s = ""
        }
        if (!symb.value.matches(Regex("\\d")) && symb.key.second != lenX - 1) {
            if (s.isNotBlank()) {
                numbers.add(Pair(s, Pair(symb.key.first, symb.key.second - 1)))
            }
            s = ""
        }
    }

    val numsWithPos = numbers.map { num ->
        val lastPos = num.second
        val newPos = (0..<num.first.length).map { pos ->
            Pair(lastPos.first, lastPos.second - pos)
        }
        Pair(num.first, newPos.reversed())
    }

    // println(numbers)

    val res = findPartNumbers(numsWithPos.toList(), symbols.toMap())

    println(res.map { it.first }.map { Integer.parseInt(it) }.sum())
}

private fun part2(input: List<String>) {
    val symbols = mutableMapOf<Pair<Int, Int>, String>()
    var lenX = 0

    input.forEachIndexed { idx, line ->
        val sls = line.trim().split("")
        lenX = sls.subList(1, sls.size - 1).size
        sls.subList(1, sls.size - 1).forEachIndexed { idx2, s -> symbols.put(Pair(idx, idx2), s.trim()) }
    }

    val numbers = mutableListOf<Pair<String, Pair<Int, Int>>>()
    var s = ""

    symbols.forEach { symb ->
        if (symb.value.matches(Regex("\\d"))) {
            s += symb.value
        }
        if (symb.key.second == lenX - 1) {
            if (s.isNotBlank() && symb.value.matches(Regex("\\d"))) {
                numbers.add(Pair(s, Pair(symb.key.first, symb.key.second)))
            }
            if (s.isNotBlank() && !symb.value.matches(Regex("\\d"))) {
                numbers.add(Pair(s, Pair(symb.key.first, symb.key.second - 1)))
            }
            s = ""
        }
        if (!symb.value.matches(Regex("\\d")) && symb.key.second != lenX - 1) {
            if (s.isNotBlank()) {
                numbers.add(Pair(s, Pair(symb.key.first, symb.key.second - 1)))
            }
            s = ""
        }
    }

    val numsWithPos = numbers.map { num ->
        val lastPos = num.second
        val newPos = (0..<num.first.length).map { pos ->
            Pair(lastPos.first, lastPos.second - pos)
        }
        Pair(num.first, newPos.reversed())
    }

    // println(numbers)

    val allStars = symbols.filter { symbol ->
        symbol.value == "*"
    }

    var sgr = 0

    allStars.forEach { star ->
        val neighbours = findNeighbours(star.key.first, star.key.second)
        // println(star)
        // println(neighbours)

        val starNums = numsWithPos.filter { numWithPos ->
            numWithPos.second.any {
                neighbours.contains(it)
            }
        }

        // println(starNums)

        val prod = if (starNums.size == 2) {
            starNums
                .map { it.first }
                .map { Integer.parseInt(it) }
                .fold(1) { acc, i -> acc * i }
        } else {
            0
        }

        //println(prod)
        sgr += prod
    }

    println(sgr)
}

fun findPartNumbers(
    numsWithPos: List<Pair<String, List<Pair<Int, Int>>>>,
    allSymbols: Map<Pair<Int, Int>, String>
): List<Pair<String, List<Pair<Int, Int>>>> {
    return numsWithPos.filter { numWithPos ->
        val allDots = numWithPos.second
            .flatMap { findNeighbours(it.first, it.second) }
            .filter { !numWithPos.second.contains(it) }
            .map {
                allSymbols[it]
            }
            .filterNotNull()
            .all {
                it == "."
            }

        !allDots
    }
}

fun findNeighbours(x: Int, y: Int): List<Pair<Int, Int>> {
    return listOf(
        Pair(x - 1, y - 1),
        Pair(x - 1, y),
        Pair(x - 1, y + 1),
        Pair(x, y - 1),
        Pair(x, y + 1),
        Pair(x + 1, y - 1),
        Pair(x + 1, y),
        Pair(x + 1, y + 1)
    )
}


fun main() {
    val lines = fileToArr("day3_2.txt")
    part1(lines)
    part2(lines)
}