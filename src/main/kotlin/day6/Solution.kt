package day6

object Solution {
    fun problem1(): Int {
        val content = Solution::class.java.getResource("/day6.txt").readText().lines().map { it.trim() }

        val groups = content.fold(mutableListOf<MutableSet<Char>>(mutableSetOf()), { acc, row ->
            if (row.isEmpty()) {
                acc.add(mutableSetOf())
            } else {
                row.forEach {
                    acc.last().add(it)
                }
            }
            acc
        })

        return groups.fold(0, {acc, mutableSet ->
            acc + mutableSet.size
        })
    }

    fun problem2(): Int {
        val content = Solution::class.java.getResource("/day6.txt").readText().lines().map { it.trim() }

        val groups = content.fold(mutableListOf<MutableList<String>>(mutableListOf()), {acc, row ->
            if (row.isEmpty()) {
                acc.add(mutableListOf())
            } else {
                acc.last().add(row)
            }
            acc
        })

        return groups.fold(0, {acc, mutableList ->

            acc + countStuff(mutableList)
        })
    }

    private fun countStuff(list: List<String>): Int {

        return list.drop(1).fold(list.first().toCharArray().map { it }.toMutableSet(), { acc, string ->
            val newSet = mutableSetOf<Char>()
            string.forEach{
                if (acc.contains(it)) {
                    newSet.add(it)
                }
            }
            newSet
        }).size
    }
}

