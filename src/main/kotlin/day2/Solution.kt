package day2

import java.util.regex.Pattern

object Solution {
    fun problem1(): Int {
        val pattern = Regex("([0-9]*)-([0-9]*) ([a-z]): ([a-z]*)")

        val content = Solution::class.java.getResource("/day2/input.txt").readText().lines()
        return content.fold(0, { acc, row ->
            pattern.matchEntire(row)?.let { result ->
                val lower = result.groups[1]!!.value.toInt()
                val upper = result.groups[2]!!.value.toInt()
                val char = result.groups[3]!!.value.toChar()
                val pass = result.groups[4]!!.value
                val count = pass.count { it == char }
                if (count in lower..upper) {
                    return@fold acc + 1
                }
            } ?: throw IllegalArgumentException("wtf")
            acc
        })
    }

    fun problem2(): Int {
        val pattern = Regex("([0-9]*)-([0-9]*) ([a-z]): ([a-z]*)")

        val content = Solution::class.java.getResource("/day2/input.txt").readText().lines()
        return content.fold(0, { acc, row ->
            pattern.matchEntire(row)?.let { result ->
                val first = result.groups[1]!!.value.toInt() - 1
                val second = result.groups[2]!!.value.toInt() - 1
                val char = result.groups[3]!!.value.toChar()
                val pass = result.groups[4]!!.value
                if ((pass[first] == char).xor(pass[second] == char)) {
                    return@fold acc + 1
                }
            } ?: throw IllegalArgumentException("wtf")
            acc
        })
    }
}

fun String?.toChar(): Char {
    if (this == null || this.length != 1) {
        throw IllegalArgumentException("String $this not acceptable as char")
    }
    return this[0]
}