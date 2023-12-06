package dev.rockyj.advent_kt

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

suspend fun <T, R> Iterable<T>.mapParallel(transform: (T) -> R): List<R> = coroutineScope {
    map { async { transform(it) } }.map { it.await() }
}

private fun findWaysToWin(timeAndDist: Pair<Long, Long>): Int {
    val time = timeAndDist.first
    val dist = timeAndDist.second

    val min = dist / time

    val x = runBlocking(Dispatchers.Default) {
        (min..time)
            .mapParallel { hold ->
                val speed = hold
                val rem = time - hold
                speed * rem
            }
            .filter { it > dist }
            .size
    }

    return x
}

private fun part1_2() {
    // val timeAndDist = listOf(Pair(7L, 9L), Pair(15L, 40L), Pair(30L, 200L))
    // val timeAndDist = listOf(Pair(51L, 377L), Pair(69L, 1171L), Pair(98L, 1224L), Pair(78L, 1505L))
    // val timeAndDist = listOf(Pair(71530L, 940200L))
    val timeAndDist = listOf(Pair(51699878L, 377117112241505))
    val numberOfOptions = timeAndDist.map {
        findWaysToWin(it)
    }
    val prod = numberOfOptions.fold(1) { acc, i -> acc * i }
    println(prod)
}

fun main() {
    part1_2()
}