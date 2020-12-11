package day4

import day2.Solution
import java.lang.Exception

object Solution {
    private val keys = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
    fun problem1(): Int {
        val content = Solution::class.java.getResource("/day4.txt").readText().lines().map { it.trim() }
        val empty = content.count { it.isEmpty() }
        println("content rows ${content.size}")
        println("empty rows $empty")
        val items = content.fold(mutableListOf<String>(), { acc, row ->
            if (row.isEmpty()) {
                acc.add("")
                acc
            } else {
                acc.add(acc.removeLastOrNull()?.let { "$it $row" } ?: row)
                acc
            }

        })

        return items.fold(0, { acc, row ->
            println("input: $row")
            if (valid(createMap(row))) {
                println("input valid: acc now ${acc + 1}")
                println()
                acc + 1
            } else {
                println()
                acc
            }
        })
    }

    fun problem2(): Int {
        val content = Solution::class.java.getResource("/day4.txt").readText().lines().map { it.trim() }
        val empty = content.count { it.isEmpty() }
        println("content rows ${content.size}")
        println("empty rows $empty")
        val items = content.fold(mutableListOf<String>(), { acc, row ->
            if (row.isEmpty()) {
                acc.add("")
                acc
            } else {
                acc.add(acc.removeLastOrNull()?.let { "$it $row" } ?: row)
                acc
            }

        })

        return items.fold(0, { acc, row ->
            println("input: $row")
            if (valid2(createMap(row))) {
                println("input valid: acc now ${acc + 1}")
                println()
                acc + 1
            } else {
                println()
                acc
            }
        })
    }

    fun valid(data: Map<String, String>): Boolean {

        return keys.fold(true, {acc, key ->
            if (data[key].isNullOrEmpty()) {
                false
            } else {
                acc
            }
        })
    }

    fun valid2(data: Map<String, String>): Boolean {
        if (!validateByr(data)) {
            println("Byr validation failed for value ${data["byr"]}")
            return false
        }
        if (!validateIyr(data)) {
            println("iyr validation failed for value ${data["iyr"]}")
            return false
        }
        if (!validateEyr(data)) {
            println("eyr validation failed for value ${data["eyr"]}")
            return false
        }
        if (!validateHgt(data)) {
            println("hgt validation failed for value ${data["hgt"]}")
            return false
        }
        if (!validateHcl(data)) {
            println("hcl validation failed for value ${data["hcl"]}")
            return false
        }
        if (!validateEcl(data)) {
            println("ecl validation failed for value ${data["ecl"]}")
            return false
        }
        if (!validatePid(data)) {
            println("pid validation failed for value ${data["pid"]}")
            return false
        }
        return true
    }

    private fun validateByr(data: Map<String, String>): Boolean {
        try {
            return data["byr"]?.let {
                val y = it.toInt()
                return y in 1920..2002
            } ?: false
        } catch (e: Exception) {
            return false
        }
    }

    private fun validateIyr(data: Map<String, String>): Boolean {
        try {
            return data["iyr"]?.let {
                val y = it.toInt()
                return y in 2010..2020
            } ?: false
        } catch (e : Exception) {
            return false
        }
    }

    private fun validateEyr(data: Map<String, String>): Boolean {
        try {
            return data["eyr"]?.let {
                val y = it.toInt()
                return y in 2020..2030
            } ?: false
        } catch (e : Exception) {
            return false
        }
    }

    val heightRegex = Regex("([0-9]*)([incm]*)")
    private fun validateHgt(data: Map<String, String>): Boolean {
        try {
            data["hgt"]?.let { value ->
                heightRegex.matchEntire(value)?.let { result ->
                    val h = result.groups[1]!!.value.toInt()
                    val unit = result.groups[2]!!.value
                    if (unit == "in") {
                        return h in 59..76
                    } else if (unit == "cm") {
                        return h in 150..193
                    }
                    return false
                }
            }
        } catch (e: Exception) {

        }
        return false
    }

    val hclPattern = Regex("^#[0-9a-f]{6}$")
    fun validateHcl(data: Map<String, String>): Boolean {
        return data["hcl"]?.let {
            hclPattern.matches(it)
        } ?: false
    }

    val eclVals = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    fun validateEcl(data: Map<String, String>): Boolean {
        return data["ecl"]?.let {
            eclVals.contains(it)
        } ?: false
    }

    val pidRegex = Regex("^0*[0-9]{9}$")
    fun validatePid(data: Map<String, String>): Boolean {
        return data["pid"]?.let {
            pidRegex.matches(it)
        } ?: false
    }

    fun createMap(input: String): Map<String, String> {
        return mapOf(*input.split(" ").map { it.split(":").let { it.first() to it.last() } }.toTypedArray())
    }
}