package day7

object Solution {
    fun problem1(): Int {
        val content = Solution::class.java.getResource("/day7.txt").readText().lines().map { it.trim() }
        val rules = parseRules(content)
        return rules.entries.fold(0, {acc, entry ->
            if (findChild(entry.value, rules, "shiny gold")) {
                acc + 1
            } else {
                acc
            }
        })
    }

    fun problem2(): Long {
        val content = Solution::class.java.getResource("/day7.txt").readText().lines().map { it.trim() }
        val rules = parseRules(content)
        return countChildren(rules["shiny gold"]!!, rules)
    }

    fun countChildren(node: Node, nodes: Map<String, Node>): Long {
        return node.children.fold(0L, {acc, pair ->
            acc + pair.second + pair.second * countChildren(nodes[pair.first]!!, nodes)
        })
    }

    fun findChild(node: Node, nodes: Map<String, Node>, target: String): Boolean {
        if (node.children.any { it.first == target }) {
            return true
        }
        if (node.children.isNotEmpty()) {
            node.children.forEach {
                if (findChild(nodes[it.first]!!, nodes, target)) {
                    return true
                }
            }
            return false
        }
        return false
    }

    val namePattern = Regex("^([a-zA-Z\\s]*)bags$")
    val rulePattern = Regex("^([0-9]*)\\s([a-zA-Z\\s]*)\\sbags*$")
    fun parseRules(list: List<String>): Map<String, Node> {
        val nodes = list.map { row ->
            val parts = row.split(" contain ")
            val name = namePattern.matchEntire(parts.first())!!.groups[1]!!.value
            if (parts.last() == "no other bags.") {
                Node(name.trim(), listOf())
            } else {
                val ruleParts = parts.last().split(",").map {
                    it.trim().let { trimmed ->
                        if(trimmed.last() == '.') {
                            trimmed.dropLast(1)
                        } else {
                            trimmed
                        }
                    }
                }
                val children = ruleParts.map {
                    val result = rulePattern.matchEntire(it)
                    result!!.groups[2]!!.value to result.groups[1]!!.value.toInt()
                }
                Node(name.trim(), children)
            }
        }
        val map = mutableMapOf<String, Node>()
        nodes.forEach { map[it.color] = it }
        return map
    }

    class Node(val color: String, val children: List<Pair<String, Int>>)
}