package dev.rockyj.advent_kt

private data class ScratchCard(val id: Int, val wins: List<Int>, val nums: List<Int>)

private fun toCards(input: List<String>): List<Pair<List<Int>, List<Int>>> {
    val cards = mutableListOf<Pair<List<Int>, List<Int>>>()

    input.forEachIndexed { _idx, line ->
        val card = line.split(":")
        val play = card.get(1).trim().split("|")

        val winningNumbers = play.get(0).trim().split(Regex("\\s+")).map { Integer.parseInt(it) }
        val myNumbers = play.get(1).trim().split(Regex("\\s+")).map { Integer.parseInt(it) }

        cards.add(Pair(winningNumbers, myNumbers))
    }

    return cards.toList()
}

private fun foobar(cardPack: List<ScratchCard>, copies: MutableMap<Int, Int>, from: Int = 0) {
    if (from >= cardPack.size) {
        val s = (1..cardPack.size).map {
            copies[it] ?: 1
        }
        println(s.sum())
        return
    }

    val card = cardPack.get(from)
    val wins = card.wins
    val myNums = card.nums

    val currCardCopies = copies[card.id] ?: 1
    val winCount = myNums.filter { wins.contains(it) }.size
    val copyIds = (1..winCount).map { card.id + it }

    copyIds
        .mapNotNull { cardPack.find { card -> card.id == it } }
        .forEach { copy ->
            repeat((1..currCardCopies).count()) {
                copies[copy.id] = (copies[copy.id] ?: 1) + 1
            }
        }

    foobar(cardPack, copies, from + 1)
}

private fun part2(input: List<String>) {
    val cardPack = toCards(input).mapIndexed { idx, cardLine ->
        ScratchCard(idx + 1, cardLine.first, cardLine.second)
    }

    foobar(cardPack, cardPack.fold(mutableMapOf()) { agg, card ->
        agg[card.id] = 1
        agg
    })
}


private fun part1(input: List<String>) {
    val res = toCards(input).toList().map { card ->
        val wins = card.first
        val myNums = card.second

        val winCount = myNums.filter { wins.contains(it) }.size

        if (winCount == 0) {
            0.0
        } else {
            Math.pow(2.0, winCount.toDouble() - 1)
        }
    }

    println(res.sum())
}

fun main() {
    val lines = fileToArr("day4_2.txt")
    part1(lines)
    part2(lines)
}