package adventofcode.day9

import adventofcode.shared.getLines
import java.util.stream.IntStream
import kotlin.math.abs

class DayNine {

    private val DAY = "day9"
    private var headPosition = Pair(0, 0)
    private val ropeElementsPositions = mutableListOf<Pair<Int, Int>>()
    private val pointsVisited = mutableListOf<Pair<Int, Int>>()

    init {
        pointsVisited.add(Pair(0,0))
    }

    fun playWithRope(file: String, nbElements: Int = 1) {
        val lines = file.getLines(DAY)

        IntStream.range(0, nbElements).forEach{
            ropeElementsPositions.add(Pair(0,0))
        }

        lines.forEach { m ->
            val wayToGo = m.split(" ")[0]
            val nbOfSteps = m.split(" ")[1].toInt()
            IntStream.range(0, nbOfSteps).forEach { s ->
                when (wayToGo) {
                    "R" -> headPosition = Pair(headPosition.first+1, headPosition.second)
                    "L" -> headPosition = Pair(headPosition.first-1, headPosition.second)
                    "U" -> headPosition = Pair(headPosition.first, headPosition.second+1)
                    "D" -> headPosition = Pair(headPosition.first, headPosition.second-1)
                }
                println("head move $wayToGo $nbOfSteps")

                ropeElementsPositions.forEachIndexed { index, element ->

                    var previousOne = headPosition
                    if (index > 0) {
                        previousOne = ropeElementsPositions[index-1]
                    }

                    if (abs(element.first - previousOne.first) > 1 || abs(element.second - previousOne.second) > 1) {
                        if (element.first < previousOne.first+1 && element.first != previousOne.first) {
                            val newX = element.first+1
                            var newY = element.second
                            if (element.second > previousOne.second) {
                                newY = element.second-1
                            } else if (element.second < previousOne.second) {
                                newY = element.second+1
                            }
                            ropeElementsPositions[index] = Pair(newX, newY)
                        } else if (element.first > previousOne.first-1 && element.first != previousOne.first) {
                            val newX = element.first-1
                            var newY = element.second
                            if (element.second > previousOne.second) {
                                newY = element.second-1
                            } else if (element.second < previousOne.second) {
                                newY = element.second+1
                            }
                            ropeElementsPositions[index] = Pair(newX, newY)
                        } else if (element.second < previousOne.second+1 && element.second != previousOne.second) {
                            var newX = element.first
                            val newY = element.second+1
                            if (element.first > previousOne.first) {
                                newX = element.first-1
                            } else if (element.first < previousOne.first) {
                                newX = element.second
                            }
                            ropeElementsPositions[index] = Pair(newX, newY)
                        } else if (element.second > previousOne.second-1 && element.second != previousOne.second) {
                            var newX = element.first
                            val newY = element.second-1
                            if (element.first > previousOne.first) {
                                newX = element.first-1
                            } else if (element.first < previousOne.first) {
                                newX = element.second
                            }
                            ropeElementsPositions[index] = Pair(newX, newY)
                        }
                        if (index == nbElements-1) {
                            pointsVisited.add(ropeElementsPositions[index])
                            println("previous in ${previousOne}, tail in $element")

                        }
                        println("$index in $element")
                    } else {
                        //println("tail doesn't move")
                    }
                }
            }
        }
        println("${pointsVisited.distinct().size} points visited")
        println(pointsVisited.distinct())
    }
}