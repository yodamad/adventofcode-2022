package adventofcode.day13

import adventofcode.shared.getLines
import adventofcode.shared.isNumeric
import java.util.stream.IntStream

class Day13 {

    var DAY = "day13"
    val pairs = ArrayListOfAny<Pair<String, String>>()

    var leftElements = ArrayListOfAny<Any>()
    var rightElements = ArrayListOfAny<Any>()

    fun computePairs(file: String) {
        val lines = file.getLines(DAY)
        buildPairs(lines)

        var sumOfPairs = 0
        pairs.forEachIndexed { index, pair ->
            leftElements = analyzeLine(pair.first.substring(1, pair.first.length-1))
            rightElements = analyzeLine(pair.second.substring(1, pair.second.length-1))
            // println("Left corner is $leftElements")
            // println("Right corner is $rightElements")
            val isOk = comparePair(leftElements, rightElements) >= 0
            if (isOk) {
                // println(">>>>>>>>>>>>>>>>>>>>> Right order of ${index+1} ? $isOk")
                sumOfPairs += index+1
            }
        }
        println("Sum of pairs = $sumOfPairs (vs 5760)")

        pairs.add(Pair("[[2]]", "[[6]]"))

        val allLines = pairs.map { listOf(it.first, it.second) }
            .flatten()
            .map { InputString<String>(it) }
            .toList()

        val output = allLines.stream().sorted().map { it.input }.toList().joinToString("|")
        val twoIndex = output.split("|").indexOf("[[2]]")+1
        val sixIndex = output.split("|").indexOf("[[6]]")+1
        println("$twoIndex * $sixIndex")
        println(twoIndex*sixIndex)
    }

    private fun buildPairs(lines: List<String>) {
        IntStream.range(0, (lines.size / 3)+1).forEach { round ->
            pairs.add(Pair(lines[round * 3], lines[round * 3 +1]))
        }
    }

}

fun comparePair(left: ArrayListOfAny<Any>, right: ArrayListOfAny<Any>): Int {
    var index = 0
    var rightOrder = 0
    var continueCheck = true

    while (rightOrder == 0 && index < left.size && index < right.size && continueCheck) {

        val leftElement = left[index]
        val rightElement = right[index]

        // println("Compare $leftElement to $rightElement")

        if (leftElement is Int) {
            if (rightElement is Int) {
                rightOrder = rightElement - leftElement
            } else if (rightElement is MutableList<*>) {
                rightOrder = findFirstInt(rightElement) - leftElement
            } else {
                println("Unimplemented 1")
            }
        } else if (leftElement is MutableList<*>) {
            if (rightElement is Int) {
                rightOrder = rightElement.toInt() - findFirstInt(leftElement)
            } else if (rightElement is MutableList<*>) {
                if (leftElement.isEmpty() && rightElement.size > 0) {
                    rightOrder = 1
                    continueCheck = false
                } else if (rightElement.isEmpty() && leftElement.size > 0) {
                    rightOrder = -1
                    continueCheck = false
                }
                else rightOrder = comparePair(leftElement as ArrayListOfAny<Any>, rightElement as ArrayListOfAny<Any>)
            } else {
                rightOrder = -1
            }
        } else {
            println("Unimplemented 3")
        }
        index++
    }

    if (rightOrder == 0) {
        rightOrder = elementSize(right) - elementSize(left)
    }
    // println("$left vs $right is $rightOrder")

    return rightOrder
}

fun elementSize(list: ArrayListOfAny<Any>): Int =
    list.sumOf { e ->
        if (e is Int) 1
        else elementSize(e as ArrayListOfAny<Any>)
    }

fun analyzeLine(line: String): ArrayListOfAny<Any> {
    // println("Analyzing $line")
    var index = 0
    var buffer = 0
    val splittedLine = line.split(",")
    val currentElements = ArrayListOfAny<Any>()

    if (line.isNotEmpty()) {
        while (index < splittedLine.size && index != -1) {
            if (splittedLine[index].isEmpty()) {
            } else if (splittedLine[index].isNumeric()) {
                val value = splittedLine[index].toInt()
                currentElements.add(value)
                buffer += splittedLine[index].length + 1
            } else if (splittedLine[index].startsWith("[")) {
                val endOfList = findClosingBracket(line.substring(buffer, line.length))
                val nextElement = line.substring(buffer+1, buffer+endOfList)
                currentElements.add(analyzeLine(nextElement))
                buffer += nextElement.length+1+2
            }
            index = findCurrentIndex(currentElements)
        }
    }
    return currentElements
}
fun findCurrentIndex(elements: MutableList<Any>): Int {
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
fun findFirstInt(element: Any?): Int {
    return if (element is Int) element
    else if (element is MutableList<*>) {
        if (element.isEmpty()) -1
        else if (element.first() is Int) element.first() as Int
        else findFirstInt(element.first())
    } else {
        -1
    }
}
fun findClosingBracket(input: String): Int {
    val openIndexes = input.mapIndexed { index, c -> if (c == '[') index else -1}.filter { it >= 0 }
    val closeIndexes = input.mapIndexed { index, c -> if (c == ']') index else -1}.filter { it > 0 }
    var index = 0
    var opened = true

    while (opened) {
        val current = closeIndexes[index]
        opened = (index+1) < openIndexes.count { current > it }
        index++
    }
    return closeIndexes[index-1]
}

class ArrayListOfAny<T> : ArrayList<T>(), Comparable<T> {
    override fun compareTo(other: T): Int {
        return comparePair(this as ArrayListOfAny<Any>, other as ArrayListOfAny<Any>)
    }

}

class InputString<T>(var input: String): Comparable<T> {
    override fun compareTo(other: T): Int {
        return -1*comparePair(analyzeLine(input), analyzeLine((other as InputString<*>).input))
    }

    override fun toString(): String {
        return input
    }
}