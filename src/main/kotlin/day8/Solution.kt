package day8


object Solution {
    fun problem1(): Int {
        val content = Solution::class.java.getResource("/day8.txt").readText().lines().map { it.trim() }
        val instructions = parseProgramme(content)
        return execute(0, 0, instructions)

    }

    fun problem2(): Int {
        val content = Solution::class.java.getResource("/day8.txt").readText().lines().map { it.trim() }
        val instructions = parseProgramme(content)

        instructions.forEachIndexed { index, instruction ->
            instructions.forEach { it.visited = false }
            when (instruction) {
                is Nop -> {
                    try {
                        return execute2(0, 0, instructions.replace(index, Jmp(instruction.change)))
                    } catch (e: IllegalStateException) {
                        //println("replacing $index did not work")
                        // Do nothing
                    }
                }
                is Acc -> {
                    //println("$index is acc ignoring")
                }
                is Jmp -> {
                    try {
                        return execute2(0, 0, instructions.replace(index, Nop(instruction.change)))
                    } catch (e: IllegalStateException) {
                        // Do nothing
                        //println("replacing $index did not work")
                    }

                }
            }
        }
        throw IllegalStateException("wtf")
    }

    fun execute2(acc: Int, ptr: Int, instructions: List<Instruction>): Int {
        if (ptr == instructions.size) {
            return acc
        }
        val instruction = instructions[ptr]
        if (instruction.visited) {
            throw IllegalStateException("instruction at ptr $ptr already visited")
        }
        instruction.visited = true
        return when (instruction) {
            is Nop -> execute2(acc, ptr+1, instructions)
            is Acc -> execute2(acc + instruction.change, ptr + 1, instructions)

            is Jmp -> execute2(acc, ptr + instruction.change, instructions)
        }
    }

    fun execute(acc: Int, ptr: Int, instructions: List<Instruction>): Int {
        val instruction = instructions[ptr]
        if (instruction.visited) {
            return acc
        }
        instruction.visited = true
        return when (instruction) {
            is Nop -> execute(acc, ptr+1, instructions)
            is Acc -> execute(acc + instruction.change, ptr + 1, instructions)
            is Jmp -> execute(acc, ptr + instruction.change, instructions)
        }
    }



    fun parseProgramme(rows: List<String>): List<Instruction> {
        return rows.map {
            val parts = it.split(" ")
            val instruction = parts.first()
            val change = parts.last().toInt()
            when (instruction) {
                "nop" -> {
                    Nop(change)
                }
                "jmp" -> {
                    Jmp(change)
                }
                "acc" -> {
                    Acc(change)
                }
                else -> throw IllegalArgumentException("Unrecognised instruction: $instruction")
            }
        }
    }
}

sealed class Instruction(val change: Int, var visited: Boolean = false)

class Nop(change: Int) : Instruction(change)
class Acc(change: Int) : Instruction(change)
class Jmp(change: Int) : Instruction(change)

fun List<Instruction>.replace(index: Int, instruction: Instruction): List<Instruction> {
    val newList = mutableListOf<Instruction>()
    if (index > 0) {
        newList.addAll(this.subList(0, index))
    }
    newList.add(instruction)
    if (index < this.lastIndex) {
        newList.addAll(this.subList(index + 1,this.size))
    }
    return newList
}