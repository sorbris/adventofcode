package day9

object Solution {
    val step = 25
    fun problem1(): Long {
        val input = Solution::class.java.getResource("/day9.txt").readText().lines().map { it.toLong() }
        for (i in step..input.lastIndex) {
            if (!isValid(input[i], input.subList(i-step,i))) {
                return input[i]
            }
        }
        return -1
    }

    fun problem2(): Long {
        val input = Solution::class.java.getResource("/day9.txt").readText().lines().map { it.toLong() }
        val target = findInvalid(input)
        val current = mutableListOf<Long>()
        for (i in input) {
            if (i >= target) {
                current.clear()
            } else {
                current.add(i)
                var sum = current.fold(0L, {acc, l ->
                    acc + l
                })
                if (sum == target) {
                    break
                } else if (sum > target) {
                    do {
                        val first = current.removeAt(0)
                        sum -= first
                    } while (sum > target)
                    if (sum == target) {
                        break
                    }
                }
            }
        }
        current.sort()
        val smallest = current.first()
        val largest = current.last()
        return smallest + largest

    }

    fun findInvalid(input: List<Long>): Long {
        for (i in step..input.lastIndex) {
            if (!isValid(input[i], input.subList(i-step,i))) {
                return input[i]
            }
        }
        return -1
    }

    fun isValid(value: Long, pool: List<Long>): Boolean {
        for (i in 0 until pool.lastIndex) {
            for (j in (i+1)..pool.lastIndex) {
                if (pool[i] + pool[j] == value) {
                    return true
                }
            }
        }
        return false
    }
}