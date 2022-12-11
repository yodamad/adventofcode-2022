package adventofcode.day10

import adventofcode.shared.getLines
import java.util.stream.IntStream

class DayTen {

    val DAY = "day10"
    private var registry = 1
    private val cycles = mutableMapOf<Int, Int>()

    private val screen = mutableMapOf<Int, String>(0 to "", 1 to "", 2 to "", 3 to "", 4 to "", 5 to "")
    private var cpt = 0

    fun anotherCompute(file: String, nbCycles: Int): Int {
        val commands = file.getLines(DAY)
        var commandIndex = 0
        var isAddx = false

        cycles[0] = 0

        IntStream.range(1,nbCycles).forEach { currentCycle ->

            if (cpt in (registry-1 .. registry+1)) {
                screen[(currentCycle-1)/40] += "#"
            } else {
                screen[(currentCycle-1)/40] += "."
            }

            println("CPT in $cpt")
            println("Registry is $registry")

            if (cpt >= 39) cpt = 0
            else cpt++

            val currentCommand = commands[commandIndex]
            if (currentCommand.startsWith("noop")) {
                // noop case
                // Move to next command
                commandIndex++
                isAddx = false
            } else if (isAddx) {
                // addx case
                registry += currentCommand.split(" ")[1].toInt()
                // Move to next command
                commandIndex++
                isAddx = false
            } else  {
                // new addx case
                isAddx = true

            }
            cycles[currentCycle+1]  = registry
        }

        /* cycles.entries.forEachIndexed{ idx, i ->
            println("$idx = ${cycles[idx]}")
        }
        println("${cycles[20]!!} * 20 + ${cycles[60]!!} * 60 + ${cycles[100]!!} * 100 + ${cycles[140]!!}" +
                " * 140 + ${cycles[180]!!} * 180 + ${cycles[220]!!} * 220") */
        printScreen()
        return cycles[20]!! * 20 + cycles[60]!! * 60 + cycles[100]!! * 100 + cycles[140]!! * 140 + cycles[180]!! * 180 + cycles[220]!! * 220

    }

    private fun printScreen() {
        screen.keys.forEach {
            println("${screen[it]!!}")
        }
    }
}