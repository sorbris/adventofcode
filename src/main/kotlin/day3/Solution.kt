package day3

import day2.Solution

object Solution {
    fun problem1(): Int {
        val content = Solution::class.java.getResource("/day3/input.txt").readText().lines()
        var j = 0
        var trees = 0
        val width = content.first().length
        for(i in 1..content.lastIndex) {
            j = (j + 3).rem(width)
            val item = content[i][j]
            if (item == '#') ++trees
        }
        return trees
    }

    fun calc(x: Int, y: Int, input: List<String>): Long {
        var trees = 0L
        var j = 0
        val width = input.first().length

        for (i in y..input.lastIndex step y) {
            j = (j + x).rem(width)
            val item = input[i][j]
            if (item == '#') ++trees
        }
        return trees
    }

    fun problem2(): Long {
        var sum = 0L
        val content = Solution::class.java.getResource("/day3/input.txt").readText().lines()
        sum = calc(1, 1, content)
        sum *= calc(3, 1, content)
        sum *= calc(5, 1, content)
        sum *= calc(7, 1, content)
        sum *= calc(1, 2, content)
        return sum
    }
}