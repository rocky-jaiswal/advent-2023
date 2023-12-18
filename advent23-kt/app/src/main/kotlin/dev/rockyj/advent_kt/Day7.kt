package dev.rockyj.advent_kt

private val cardRanking = "A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, 2".split(",")
    .map { it.trim() }
    .filter { it != "" }
    .reversed()

private val cardRanking2 = "A, K, Q, T, 9, 8, 7, 6, 5, 4, 3, 2, J".split(",")
    .map { it.trim() }
    .filter { it != "" }
    .reversed()

private data class Hand(val cards: List<String>, val bid: Long, val index: Int)

private fun comp(h1: Hand, h2: Hand): Int {
    val h1c = h1.cards.map { cardRanking2.indexOf(it) }
    val h2c = h2.cards.map { cardRanking2.indexOf(it) }

    var big = 0

    h1c.forEachIndexed lit@{ index, _rank ->
        if (big == 0) {
            if ((h1c.get(index) == h2c.get(index))) {
                return@lit
            }
            if (h1c.get(index) > h2c.get(index)) {
                big = 1
            }
            if (h1c.get(index) < h2c.get(index)) {
                big = -1
            }
        }
    }

    return big
}

private fun part2(input: List<String>) {
    val handsM = mutableListOf<Hand>()

    input.forEachIndexed { idx, it ->
        val parts = it.trim().split(Regex("\\s+"))
        val cards = parts.first().split("").filter { it != "" }
        val bid = parts.last()

        handsM.add(Hand(cards, bid.toLong(), idx))
    }

    val hands = handsM.toList()

    // 5 of a kind
    val fiveOfAKind = hands.filter { hand ->
        val cards = hand.cards
        val groupsWithoutJoker = cards.filter { it != "J" }.groupBy { it }

        cards.distinct().size == 1 || groupsWithoutJoker.size == 1
    }.sortedWith { h1, h2 -> comp(h1, h2) }

    // 4 of a kind
    val fourOfAKind = hands.filter { !fiveOfAKind.map { ex -> ex.index }.contains(it.index) }.filter { hand ->
        val cards = hand.cards
        val groups = cards.groupBy { it }
        val groupsWithoutJoker = cards.filter { it != "J" }.groupBy { it }
        val biggestGroupWithoutJoker = groupsWithoutJoker.keys.sortedBy { groupsWithoutJoker[it]!!.size }.last()
        val cloned = cards.map { if (it == "J" || it == biggestGroupWithoutJoker) "X" else it }
        val clonedGroup = cloned.groupBy { it }

        (cards.distinct().size == 2 && groups.keys.map { groups[it]!!.size }.sorted() == listOf(
            1,
            4
        )) || clonedGroup.keys.map { clonedGroup[it]!!.size }.sorted() == listOf(1, 4)
    }.sortedWith(Comparator<Hand> { h1, h2 -> comp(h1, h2) })

    // full house
    val fullHouse =
        hands.filter { !fiveOfAKind.plus(fourOfAKind).map { ex -> ex.index }.contains(it.index) }.filter { hand ->
            val cards = hand.cards
            val groups = cards.groupBy { it }
            val groupsWithoutJoker = cards.filter { it != "J" }.groupBy { it }
            val biggestGroupWithoutJoker = groupsWithoutJoker.keys.sortedBy { groupsWithoutJoker[it]!!.size }.last()
            val cloned = cards.map { if (it == "J" || it == biggestGroupWithoutJoker) "X" else it }
            val clonedGroup = cloned.groupBy { it }

            (groups.size == 2 && groups.keys.map { groups[it]!!.size }.sorted() == listOf(
                2,
                3
            )) || clonedGroup.keys.map { clonedGroup[it]!!.size }.sorted() == listOf(2, 3)
        }.sortedWith(Comparator<Hand> { h1, h2 -> comp(h1, h2) })

    // 3 of a kind
    val threeOfAKind =
        hands.filter { !fiveOfAKind.plus(fourOfAKind).plus(fullHouse).map { ex -> ex.index }.contains(it.index) }
            .filter { hand ->
                val cards = hand.cards
                val groups = cards.groupBy { it }
                val groupsWithoutJoker = cards.filter { it != "J" }.groupBy { it }
                val biggestGroupWithoutJoker = groupsWithoutJoker.keys.sortedBy { groupsWithoutJoker[it]!!.size }.last()
                val cloned = cards.map { if (it == "J" || it == biggestGroupWithoutJoker) "X" else it }
                val clonedGroup = cloned.groupBy { it }

                (groups.keys.map { groups[it]!!.size }.sorted() == listOf(
                    1,
                    1,
                    3
                )) || clonedGroup.keys.map { clonedGroup[it]!!.size }.sorted() == listOf(1, 1, 3)
            }.sortedWith(Comparator<Hand> { h1, h2 -> comp(h1, h2) })

    // 2 pair
    val twoPair =
        hands.filter {
            !fiveOfAKind.plus(fourOfAKind).plus(fullHouse).plus(threeOfAKind).map { ex -> ex.index }.contains(it.index)
        }
            .filter { hand ->
                val cards = hand.cards
                val groups = cards.groupBy { it }
                val groupsWithoutJoker = cards.filter { it != "J" }.groupBy { it }
                val biggestGroupWithoutJoker = groupsWithoutJoker.keys.sortedBy { groupsWithoutJoker[it]!!.size }.last()
                val cloned = cards.map { if (it == "J" || it == biggestGroupWithoutJoker) "X" else it }
                val clonedGroup = cloned.groupBy { it }

                (cards.distinct().size == 3 && groups.keys.filter { k -> groups[k]!!.size == 2 }.size == 2)
                        || clonedGroup.keys.map { clonedGroup[it]!!.size }.sorted() == listOf(1, 2, 2)
            }.sortedWith(Comparator<Hand> { h1, h2 -> comp(h1, h2) })

    // 1 pair
    val onePair =
        hands.filter {
            !fiveOfAKind.plus(fourOfAKind).plus(fullHouse).plus(threeOfAKind).plus(twoPair).map { ex -> ex.index }
                .contains(it.index)
        }
            .filter { hand ->
                val cards = hand.cards
                val groups = cards.groupBy { it }
                val groupsWithoutJoker = cards.filter { it != "J" }.groupBy { it }
                val biggestGroupWithoutJoker = groupsWithoutJoker.keys.sortedBy { groupsWithoutJoker[it]!!.size }.last()
                val cloned = cards.map { if (it == "J" || it == biggestGroupWithoutJoker) "X" else it }
                val clonedGroup = cloned.groupBy { it }

                (cards.distinct().size == 4 && groups.keys.filter { k -> groups[k]!!.size == 2 }.size == 1)
                        || clonedGroup.keys.map { clonedGroup[it]!!.size }.sorted() == listOf(1, 1, 1, 2)
            }.sortedWith(Comparator<Hand> { h1, h2 -> comp(h1, h2) })

    // high card
    val highCard =
        hands.filter {
            !fiveOfAKind.plus(fourOfAKind).plus(fullHouse).plus(threeOfAKind).plus(twoPair).plus(onePair)
                .map { ex -> ex.index }
                .contains(it.index)
        }
            .filter { hand ->
                hand.cards.distinct().size == 5
            }.sortedWith(Comparator<Hand> { h1, h2 -> comp(h1, h2) })

    val score =
        highCard
            .plus(onePair)
            .plus(twoPair)
            .plus(threeOfAKind)
            .plus(fullHouse)
            .plus(fourOfAKind)
            .plus(fiveOfAKind)
            .mapIndexed { index, hand ->
                //println(hand.bid)
                (index + 1) * hand.bid
            }.sum()

    println(score)
}

private fun part1(input: List<String>) {
    val handsM = mutableListOf<Hand>()

    input.forEachIndexed { idx, it ->
        val parts = it.trim().split(Regex("\\s+"))
        val cards = parts.first().split("").filter { it != "" }
        val bid = parts.last()

        handsM.add(Hand(cards, bid.toLong(), idx))
    }

    val hands = handsM.toList()

    // 5 of a kind
    val fiveOfAKind = hands.filter { hand ->
        hand.cards.distinct().size == 1
    }.sortedWith { h1, h2 -> comp(h1, h2) }

    // 4 of a kind
    val fourOfAKind = hands.filter { !fiveOfAKind.map { ex -> ex.index }.contains(it.index) }.filter { hand ->
        val cards = hand.cards
        val groups = cards.groupBy { it }

        cards.distinct().size == 2 && groups.keys.map { groups[it]!!.size }.sorted() == listOf(1, 4)
    }.sortedWith(Comparator<Hand> { h1, h2 -> comp(h1, h2) })

    // full house
    val fullHouse =
        hands.filter { !fiveOfAKind.plus(fourOfAKind).map { ex -> ex.index }.contains(it.index) }.filter { hand ->
            val cards = hand.cards
            val groups = cards.groupBy { it }

            groups.size == 2 && groups.keys.map { groups[it]!!.size }.sorted() == listOf(2, 3)
        }.sortedWith(Comparator<Hand> { h1, h2 -> comp(h1, h2) })

    // 3 of a kind
    val threeOfAKind =
        hands.filter { !fiveOfAKind.plus(fourOfAKind).plus(fullHouse).map { ex -> ex.index }.contains(it.index) }
            .filter { hand ->
                val cards = hand.cards
                val groups = cards.groupBy { it }

                groups.keys.map { groups[it]!!.size }.sorted() == listOf(1, 1, 3)
            }.sortedWith(Comparator<Hand> { h1, h2 -> comp(h1, h2) })

    // 2 pair
    val twoPair =
        hands.filter {
            !fiveOfAKind.plus(fourOfAKind).plus(fullHouse).plus(threeOfAKind).map { ex -> ex.index }.contains(it.index)
        }
            .filter { hand ->
                val cards = hand.cards
                val groups = cards.groupBy { it }

                cards.distinct().size == 3 && groups.keys.filter { k -> groups[k]!!.size == 2 }.size == 2
            }.sortedWith(Comparator<Hand> { h1, h2 -> comp(h1, h2) })

    // 1 pair
    val onePair =
        hands.filter {
            !fiveOfAKind.plus(fourOfAKind).plus(fullHouse).plus(threeOfAKind).plus(twoPair).map { ex -> ex.index }
                .contains(it.index)
        }
            .filter { hand ->
                val cards = hand.cards
                val groups = cards.groupBy { it }

                cards.distinct().size == 4 && groups.keys.filter { k -> groups[k]!!.size == 2 }.size == 1
            }.sortedWith(Comparator<Hand> { h1, h2 -> comp(h1, h2) })

    // high card
    val highCard =
        hands.filter {
            !fiveOfAKind.plus(fourOfAKind).plus(fullHouse).plus(threeOfAKind).plus(twoPair).plus(onePair)
                .map { ex -> ex.index }
                .contains(it.index)
        }
            .filter { hand ->
                hand.cards.distinct().size == 5
            }.sortedWith(Comparator<Hand> { h1, h2 -> comp(h1, h2) })

    val score =
        highCard
            .plus(onePair)
            .plus(twoPair)
            .plus(threeOfAKind)
            .plus(fullHouse)
            .plus(fourOfAKind)
            .plus(fiveOfAKind)
            .mapIndexed { index, hand ->
                //println(hand.bid)
                (index + 1) * hand.bid
            }.sum()

    println(score)
}

fun main() {
    val lines = fileToArr("day7_2.txt")
    // part1(lines)
    part2(lines)
}