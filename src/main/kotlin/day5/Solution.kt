package day5

import java.lang.IllegalStateException


object Solution {
    fun problem1(): Int {
        val content = Solution::class.java.getResource("/day5.txt").readText().lines().map {it.trim()}

        return content.fold(0, {acc, input ->
            val row = getRow(input)
            val seat = getSeat(input)
            val id = (row*8)+seat
            if (id > acc) {
                println("new high")
                println("input: $input")
                println("row: $row seat: $seat id: $id")
                id
            } else {
                acc
            }
        })
    }

    fun problem2(): Int {
        val content = Solution::class.java.getResource("/day5.txt").readText().lines().map {it.trim()}

        val rows = (0..127).map { mutableListOf<Int>() }

        content.forEach { input ->
            val row = getRow(input)
            val seat = getSeat(input)
            val id = (row*8)+seat

            rows[row].add(id)

        }

        val row = rows.dropWhile { it.size != 8 }.dropLastWhile { it.size != 8 }.find { it.size != 8 }
        row!!.sort()
        row.forEachIndexed { index, seat ->
            if (index != 0) {
                if (seat - row[index-1] != 1) {
                    return seat-1
                }
            }
        }
        throw IllegalStateException("wtf")
    }

    fun getRow(input: String): Int {

        val nbrs = input.take(7)
        return nbrs.fold(0..127, { acc, char ->
            if (char == 'F') {
                acc.lower()
            } else {
                acc.upper()
            }
        }).last

    }

    fun getSeat(input: String): Int {
        val nbrs = input.takeLast(3)
        return nbrs.fold(0..7, { acc, char ->
            if (char == 'L') {
                acc.lower()
            } else {
                acc.upper()
            }
        }).last
    }
}

fun IntRange.upper(): IntRange {
    return start+(size()/2)..endInclusive
}

fun IntRange.lower(): IntRange {
    return start..endInclusive-size()/2
}

fun IntRange.size(): Int {
    return (endInclusive - start) + 1
}