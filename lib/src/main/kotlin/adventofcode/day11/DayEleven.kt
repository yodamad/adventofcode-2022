package adventofcode.day11

import adventofcode.shared.getLines
import java.util.stream.IntStream
import kotlin.math.abs

class DayEleven {

    private val DAY = "day11"

    private val monkeys = mutableListOf<Monkey>()

    fun buildMonkey(file: String) {
        var currentMonkey = Monkey(0)
        file.getLines(DAY).forEach { l ->
            if (l.startsWith("Monkey")) {
                val regex = """Monkey (\d+):""".toRegex()
                val nb = regex.find(l)!!.destructured
                currentMonkey = Monkey(nb.component1().toInt())
            } else if (l.trimStart().startsWith("Starting")) {
                val items = l.trimStart().removePrefix("Starting items: ").split(", ")
                currentMonkey.items = items.map { it.toLong() }.toMutableList()
            } else if (l.trimStart().startsWith("Operation")) {
                currentMonkey.operation = l.trimStart().removePrefix("Operation: new = ")
            } else if (l.trimStart().startsWith("Test")) {
                val regex = """Test: divisible by (\d+)""".toRegex()
                val div = regex.find(l.trimStart())!!.destructured.component1()
                currentMonkey.modulo = div.toInt()
            } else if (l.trimStart().startsWith("If true")) {
                val regex = """If true: throw to monkey (\d+)""".toRegex()
                val div = regex.find(l.trimStart())!!.destructured.component1()
                currentMonkey.ifTrue = div.toInt()
            } else if (l.trimStart().startsWith("If false")) {
                val regex = """If false: throw to monkey (\d+)""".toRegex()
                val div = regex.find(l.trimStart())!!.destructured.component1()
                currentMonkey.ifFalse = div.toInt()
                monkeys.add(currentMonkey)
            }
        }
        monkeys.forEach { println(it) }
        playRounds()

        val result = monkeys.map { it.itemsInspected }.sortedDescending().take(2).reduce { acc, i -> acc * i }
        println("Result is $result")
    }

    private fun playRounds(nb: Int = 20) {
        IntStream.range(1, nb+1).forEach { round ->
            monkeys.forEach { monkey ->
                playMonkey(monkey)
            }

            println("---------------")
            println("End of round $round")
            println("---------------")
            monkeys.forEach { println(it) }
            println("==========================")
        }
    }

    private fun playMonkey(monkey: Monkey) {
        monkey.items.forEach { item ->
            val timesRegex = """old \* (\d+)""".toRegex()
            val plusRegex = """old \+ (\d+)""".toRegex()
            val squareRegex = """old \* old""".toRegex()
            var boring = 0L

            if (timesRegex.matches(monkey.operation)) {
                val times = timesRegex.find(monkey.operation)!!.destructured.component1().toInt()
                boring = (item * times).toLong()
            } else if (plusRegex.matches(monkey.operation)) {
                val adds = plusRegex.find(monkey.operation)!!.destructured.component1().toInt()
                boring = (item + adds).toLong()
            } else if (squareRegex.matches(monkey.operation)) {
                boring = (item * item).toLong()
            }

            boring = abs(boring / 3)
            if (boring % monkey.modulo == 0L) {
                // println("(true) Monkey ${monkey.number} sends ${abs(boring / 3)} to ${monkey.ifTrue}")
                sendToMonkey(monkey.ifTrue, boring)
            } else {
                // println("(false) Monkey ${monkey.number} sends ${abs(boring / 3)} to ${monkey.ifFalse}")
                sendToMonkey(monkey.ifFalse, boring)
            }
            monkey.itemsInspected++
        }
        monkey.items = mutableListOf()
    }

    private fun sendToMonkey(monkeyId: Int, item: Long) {
        monkeys.first { it.number == monkeyId }.items.add(item)
    }
}

data class Monkey(val number: Int, var items: MutableList<Long> = mutableListOf(), var itemsInspected: Int = 0,
                  var operation: String = "", var modulo: Int = 0, var ifTrue: Int = 0, var ifFalse: Int = 0) {
    override fun toString(): String {
        return "Monkey $number is holding $items, operates with '$operation', condition of division is $modulo : if true, send to $ifTrue otherwise $ifFalse. He inspected $itemsInspected items"
    }
}