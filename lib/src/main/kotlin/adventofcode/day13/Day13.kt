package adventofcode.day13

import adventofcode.shared.getLines
import adventofcode.shared.isNumeric
import java.util.stream.IntStream

class Day13 {

    var DAY = "day13"
    val pairs = mutableListOf<Pair<String, String>>()

    var leftElements = mutableListOf<Any>()
    var rightElements = mutableListOf<Any>()

    fun computePairs(file: String) {
        val lines = file.getLines(DAY)
        buildPairs(lines)

        pairs.forEach {
            leftElements = analyzeLine(it.first.substring(1, it.first.length-1))
            rightElements = analyzeLine(it.second.substring(1, it.second.length-1))
            println("Left corner is $leftElements")
            println("Right corner is $rightElements")
        }

    }

    fun buildPairs(lines: List<String>) {
        IntStream.range(0, (lines.size / 3) + 1).forEach { round ->
            pairs.add(Pair(lines[round * 3], lines[round * 3 +1]))
        }
    }

    fun analyzeLine(line: String): MutableList<Any> {
        // println("Analyzing $line")
        var index = 0
        var buffer = 0
        val splittedLine = line.split(",")
        var currentElements = mutableListOf<Any>()

        if (line.isNotEmpty()) {
            while (index < splittedLine.size && index != -1) {
                if (splittedLine[index].isEmpty()) {
                    index++
                } else if (splittedLine[index].isNumeric()) {
                    currentElements.add(splittedLine[index].toInt())
                    buffer += splittedLine[index].length + 1
                    index++
                } else if (splittedLine[index].startsWith("[")) {
                    if (splittedLine[index].endsWith("]")) {
                        val innerList = splittedLine[index].removePrefix("[").removeSuffix("]")
                        println("Innerlist is $innerList, Line is ${splittedLine[index]}")
                        if (innerList.length > 0) {
                            currentElements.add(analyzeLine(innerList))
                            buffer += splittedLine[index].length + 2
                        } else
                            currentElements.add("")
                        index++
                    } else {
                        val endOfList = line.lastIndexOf("]")
                        val newList = line.substring(buffer+1, endOfList)
                        currentElements.add(analyzeLine(newList))
                        buffer = endOfList+1
                        index += newList.split(",").size
                    }
                } else {
                    // ","
                    index++
                }
            }
            // println(currentElements)
        }
        return currentElements
    }
}
