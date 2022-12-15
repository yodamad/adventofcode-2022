package adventofcode.day13

import adventofcode.shared.getLines
import adventofcode.shared.isNumeric
import java.util.stream.IntStream

class Day13 {

    var DAY = "day13"
    val pairs = mutableListOf<Pair<String, String>>()

    var leftElements = mutableListOf<Any>()
    var rightElements = mutableListOf<Any>()
    var globalIndex = 0

    fun computePairs(file: String) {
        val lines = file.getLines(DAY)
        buildPairs(lines)

        var sumOfPairs = 0
        pairs.forEachIndexed { index, pair ->
            leftElements = analyzeLine(pair.first.substring(1, pair.first.length-1))
            globalIndex = 0
            rightElements = analyzeLine(pair.second.substring(1, pair.second.length-1))
            // println("Left corner is $leftElements")
            // println("Right corner is $rightElements")
            val isOk = comparePair(leftElements, rightElements) == 1
            if (isOk) {
                println(">>>>>>>>>>>>>>>>>>>>> Right order of ${index+1} ? $isOk")
                sumOfPairs += index+1
            }
        }
        println("Sum of pairs = $sumOfPairs (vs 5760)")
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
        val currentElements = mutableListOf<Any>()

        if (line.isNotEmpty()) {
            while (index < splittedLine.size && index != -1) {
                if (splittedLine[index].isEmpty()) {
                } else if (splittedLine[index].isNumeric()) {
                    val value = splittedLine[index].toInt()
                    currentElements.add(value)
                    buffer += splittedLine[index].length + 1
                } else if (splittedLine[index].startsWith("[")) {
                    // println("$splittedLine in $line")
                    val endOfList = findClosingBracket(line.substring(buffer, line.length))
                    val nextElement = line.substring(buffer+1, buffer+endOfList)
                    currentElements.add(analyzeLine(nextElement))
                    buffer += nextElement.length+1+2 // 1 for ']', 1 for ','
                }
                index = findCurrentIndex(currentElements)
            }
        }
        return currentElements
    }

    private fun findCurrentIndex(elements: MutableList<Any>): Int {
        var index = 0
        elements.forEach {
            if (it is Int) index++
            else if (it is MutableList<*>) {
                if (it.isEmpty()) index++
                else index += findCurrentIndex(it as MutableList<Any>)
            }

        }
        return index
    }

    fun comparePair(left: MutableList<Any>, right: MutableList<Any>): Int {
        var index = 0
        var rightOrder = 0
        var continueCheck = true

        while (rightOrder == 0 && index < left.size && index < right.size && continueCheck) {

            val leftElement = left[index]
            val rightElement = right[index]

            println("Compare $leftElement to $rightElement")

            if (leftElement is Int) {
                if (rightElement is Int) {
                    rightOrder =
                        if (leftElement == rightElement) 0
                        else if (leftElement < rightElement) 1
                        else -1
                } else if (rightElement is MutableList<*>) {
                    val rightValue = findFirstInt(rightElement)
                    rightOrder =
                        if (leftElement == rightValue && rightElement.size == 1) 0
                        else if (leftElement <= rightValue) 1
                        else -1
                } else {
                    println("Unimplemented 1")
                }
            } else if (leftElement is MutableList<*>) {
                if (rightElement is Int) {
                    val rightValue = rightElement.toInt()
                    val leftValue = findFirstInt(leftElement)
                    rightOrder =
                        if (leftValue == rightValue && leftElement.size == 1) 0
                        else if (leftValue < rightValue) 1
                        else -1
                } else if (rightElement is MutableList<*>) {
                    if (leftElement.isEmpty() && rightElement.size > 0) {
                        rightOrder = 1
                        continueCheck = false
                    } else if (rightElement.isEmpty() && leftElement.size > 0) {
                        rightOrder = -1
                        continueCheck = false
                    }
                    else rightOrder = comparePair(leftElement as MutableList<Any>, rightElement as MutableList<Any>)
                } else {
                    rightOrder = -1
                }
            } else {
                println("Unimplemented 3")
            }
            index++
        }

        // Nothing left side
        if (rightOrder == 0 && index >= left.size) rightOrder = 1
        if (rightOrder == 0 && index >= right.size) rightOrder = -1

        return rightOrder
    }

    private fun findFirstInt(element: Any?): Int {
        return if (element is Int) element
        else if (element is MutableList<*>) {
            if (element.isEmpty()) -1
            else if (element.first() is Int) element.first() as Int
            else findFirstInt(element.first())
        } else {
            -1
        }
    }

    private fun findClosingBracket(input: String): Int {
        //println("Find bracket in $input")
        val openIndexes = input.mapIndexed { index, c -> if (c == '[') index else -1}.filter { it >= 0 }
        val closeIndexes = input.mapIndexed { index, c -> if (c == ']') index else -1}.filter { it > 0 }
        // 1,2,3,5 vs 4,6,7,8
        var index = 0
        var opened = true

        while (opened) {
            val current = closeIndexes[index]
            opened = (index+1) < openIndexes.count { current > it }
            index++
        }
        return closeIndexes[index-1]
    }
}
