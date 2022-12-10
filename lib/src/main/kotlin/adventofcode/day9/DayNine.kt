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
                    if (abs(element.first - headPosition.first) > 1 || abs(element.second - headPosition.second) > 1) {
                        if (element.first < headPosition.first+1 && element.first != headPosition.first) {
                            val newX = element.first+1
                            var newY = element.second
                            if (element.second > headPosition.second) {
                                newY = element.second-1
                            } else if (element.second < headPosition.second) {
                                newY = element.second+1
                            }
                            ropeElementsPositions[index] = Pair(newX, newY)
                        } else if (element.first > headPosition.first-1 && element.first != headPosition.first) {
                            val newX = element.first-1
                            var newY = element.second
                            if (element.second > headPosition.second) {
                                newY = element.second-1
                            } else if (element.second < headPosition.second) {
                                newY = element.second+1
                            }
                            ropeElementsPositions[index] = Pair(newX, newY)
                        } else if (element.second < headPosition.second+1 && element.second != headPosition.second) {
                            var newX = element.first
                            val newY = element.second+1
                            if (element.first > headPosition.first) {
                                newX = element.first-1
                            } else if (element.first < headPosition.first) {
                                newX = element.second
                            }
                            ropeElementsPositions[index] = Pair(newX, newY)
                        } else if (element.second > headPosition.second-1 && element.second != headPosition.second) {
                            var newX = element.first
                            val newY = element.second-1
                            if (element.first > headPosition.first) {
                                newX = element.first-1
                            } else if (element.first < headPosition.first) {
                                newX = element.second
                            }
                            ropeElementsPositions[index] = Pair(newX, newY)
                        }
                        pointsVisited.add(ropeElementsPositions[index])
                        println("head in ${headPosition}, tail in $element")
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