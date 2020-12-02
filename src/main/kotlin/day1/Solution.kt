package day1

class Solution {

    companion object {
        fun problem1(): Int {
            val content = Solution::class.java.getResource("/1/input.txt").readText()
            val lines = content.lines().map { it.toInt() }
            for (i in 0..lines.lastIndex) {
                for (j in i..lines.lastIndex) {
                    if (lines[i] + lines[j] == 2020) {
                        return lines[i] * lines[j]
                    }
                }
            }
            throw IllegalArgumentException("wtf")
        }

        fun problem2(): Int {
            val content = Solution::class.java.getResource("/1/input.txt").readText()
            val lines = content.lines().map { it.toInt() }.sorted()
             for (i in 0..lines.lastIndex) {
                 outer@ for (j in (i + 1)..lines.lastIndex) {
                    for (k in (j + 1)..lines.lastIndex) {
                        val sum = lines[i] + lines[j] + lines[k]
                        if (sum == 2020) {
                            return lines[i] * lines[j] * lines[k]
                        } else if (sum > 2020) {
                            continue@outer
                        }
                    }
                }
            }
            throw IllegalArgumentException("wtf")
        }
    }
}