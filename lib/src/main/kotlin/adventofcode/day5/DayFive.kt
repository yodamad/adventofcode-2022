package adventofcode.day5

import adventofcode.shared.getLines

class DayFive {

    val DAY = "day5"

    fun computeStacks(file: String, reverse: Boolean = true) {
        val stacks = file.getLines(DAY)
            .filter { l -> l.isNotEmpty() }
            .filter { l -> !l.startsWith("move") }
            .map { l -> computeStack(l) }
            .toList()


        val moves = file.getLines(DAY)
            .filter { l -> l.isNotEmpty() }
            .filter { l -> l.startsWith("move") }
            .map { l -> computeMoves(l) }
            .toList()

        moves.forEach { m ->
            var currentList = stacks[m.originalStack -1].list
            val movingCrates = currentList.takeLast(m.moves)
            stacks[m.originalStack -1].list = currentList.dropLast(m.moves).toMutableList()
            if (reverse)
                stacks[m.targetStack -1].list.addAll(movingCrates.reversed())
            else
                stacks[m.targetStack -1].list.addAll(movingCrates)

            /*
            println("--------")
            println(stacks)
            println("--------")
            */
        }
        println(stacks)

        val result = stacks.map { s -> s.list }
            .map { l -> l.last() }
            .toList().joinToString("")

        println(result)
    }

    fun computeStack(line: String): Stack {
        val stackNb = line.split(" ").first().toInt()
        val rawCrates = line.split(" ")[1].removePrefix("[").removeSuffix("]")
        val crates = rawCrates.split("][")
        return Stack(stackNb, crates.toMutableList())
    }

    fun computeMoves(line: String): Move {
        val regex = """move (\d+) from (\d+) to (\d+)""".toRegex()
        val (moves, origin, target) = regex.find(line)!!.destructured
        return Move(moves.toInt(), origin.toInt(), target.toInt())
    }
}

data class Stack(val index: Int, var list: MutableList<String>) {
    override fun toString(): String {
        return "Stack $index is $list"
    }
}

data class Move(val moves: Int, val originalStack: Int, val targetStack: Int) {
    override fun toString(): String {
        return "Moving $moves from $originalStack to $targetStack"
    }
}