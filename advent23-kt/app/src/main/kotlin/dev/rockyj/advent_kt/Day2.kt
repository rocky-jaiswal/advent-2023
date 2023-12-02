package dev.rockyj.advent_kt

private fun buildGames(input: List<String>): MutableMap<Int, List<Pair<String, Int>>> {
    val games = mutableMapOf<Int, List<Pair<String, Int>>>()

    input.forEach { line ->
        val game = mutableListOf<Pair<String, Int>>()
        val gameParts = line.trim().split(":")
        val gameId = gameParts.get(0).substring(4).trim()

        val gamePlays = gameParts.get(1).split(";")

        gamePlays.forEach { gamePlay ->
            val cubes = gamePlay.split(",")
            cubes.forEach { draw ->
                val x1 = draw.trim().split(Regex("\\s"))
                val pair = Pair(x1.get(1), Integer.parseInt(x1.get(0)))

                game.add(pair)
            }
        }

        games.put(Integer.parseInt(gameId), game)
    }

    // println(games)
    return games
}

private fun part1(input: List<String>) {
    val games = buildGames(input)

    val possibleGames = games.filter { game ->
        val pairs = game.value
        pairs.all { pair ->
                    (pair.first == "red" && pair.second <= 12) ||
                    (pair.first == "green" && pair.second <= 13) ||
                            (pair.first == "blue" && pair.second <= 14)
        }
    }

    println(possibleGames.keys.sum())
}

private fun part2(input: List<String>) {
    val games = buildGames(input)

    val powers = games.map { game ->
        var pr = 0
        var pg = 0
        var pb = 0

        game.value.forEach { pair ->
            if(pair.first == "red" && pair.second >= pr) {
                pr = pair.second
            }
            if(pair.first == "green" && pair.second >= pg) {
                pg = pair.second
            }
            if(pair.first == "blue" && pair.second >= pb) {
                pb = pair.second
            }
        }

        pr * pg * pb
    }

    println(powers.sum())
}

fun main() {
    val lines = fileToArr("day2_2.txt")
    part1(lines)
    part2(lines)
}