package dev.rockyj.advent_kt

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

private fun process(nums: MutableList<Long>): Long {
    val listOfLists = mutableListOf<MutableList<Long>>()

    listOfLists.add(0, nums)

    while (!listOfLists.last().all { it == 0L }) {
        val newList = mutableListOf<Long>()

        listOfLists.last().forEachIndexed { idx, elem ->
            if (idx + 1 < listOfLists.last().size) {
                newList.add(listOfLists.last().get(idx + 1) - listOfLists.last().get(idx))
            }
        }

        listOfLists.plusAssign(newList)
    }

    return listOfLists.reversed().mapIndexed { index, longs ->
        if (index == 0) {
            longs.plusAssign(0L)
            longs
        } else {
            // longs.plusAssign(longs.last() + listOfLists.reversed().get(index - 1).last())
            longs.add(0, longs.first() - listOfLists.reversed().get(index - 1).first())
            longs//.reversed()
        }
    }.last().first()
}

private fun part1_2(input: List<String>) {
    val res = runBlocking(Dispatchers.Default) {
        input.mapParallel {
            val nums = it.trim().split(Regex("\\s+")).filter { str -> str != "" }.map { str -> str.toLong() }
            process(nums.toMutableList())
        }
    }

    println(res.sum())
}

fun main() {
    val lines = fileToArr("day9_2.txt")
    timed {
        part1_2(lines)
    }
}


