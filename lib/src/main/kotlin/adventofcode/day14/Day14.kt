package adventofcode.day14

import adventofcode.shared.getLines
import java.util.stream.IntStream
import kotlin.properties.Delegates

class Day14 {

    val DAY = "day14"
    var map = mutableListOf<Point>()
    val startingPoint = Point(500, 0, "o")
    var floor by Delegates.notNull<Int>()

    fun fillWithSand(file: String) {
        printMap(file)
        findFloor()
        putSand()
    }

    private fun printMap(file: String) {
        val lines = file.getLines(DAY)
        lines.forEach { l ->
            val points = l.split(" -> ")
                .map { it.split(",") }
                .map { Point(it[0].toInt(), it[1].toInt(), "#") }
            println(points)
            map.addAll(points)

            points.forEachIndexed { index, point ->
                if (index < points.size - 1) drawLine(point, points[index + 1])
            }
            map = map.sortedBy { it.y }.sortedBy { it.x }.distinct().toMutableList()
        }
        println(map)
    }

    private fun findFloor() {
        floor = map.map { it.y }.maxOf { it } + 2
        println("Floor level is $floor")
    }

    private fun putSand() {
        var moreMove = true
        var currentPoint = startingPoint
        var sandBlocks = 0
        var turn = 1
        while(moreMove) {
            println("Current point is $currentPoint (turn $turn)")
            if (map.contains(startingPoint)) {
                moreMove = false
                println("Stooopppp")
            } else {
                if (currentPoint.y == floor-1) {
                    map.add(Point(currentPoint.x, currentPoint.y, "o"))
                    currentPoint = startingPoint
                    sandBlocks++
                } else if (map.contains(Point(currentPoint.x, currentPoint.y+1))) {
                    if (map.contains(Point(currentPoint.x-1, currentPoint.y+1))) {
                        if (map.contains(Point(currentPoint.x+1, currentPoint.y+1))) {
                            map.add(Point(currentPoint.x, currentPoint.y, "o"))
                            currentPoint = startingPoint
                            sandBlocks++
                        } else {
                            currentPoint = Point(currentPoint.x+1, currentPoint.y+1)
                        }
                    } else {
                        currentPoint = Point(currentPoint.x-1, currentPoint.y+1)
                    }
                } else {
                    currentPoint = Point(currentPoint.x, currentPoint.y+1)
                }
            }
            turn++
        }
        println("$sandBlocks sand blocks")
    }

    private fun drawLine(start: Point, end: Point) {
        if (start.x == end.x) {
            IntStream.range(minOf(start.y, end.y)+1, maxOf(start.y, end.y)).forEach {
                map.add(Point(start.x, it, "#"))
            }
        } else {
            IntStream.range(minOf(start.x, end.x)+1, maxOf(start.x, end.x)).forEach {
                map.add(Point(it, start.y, "#"))
            }
        }
    }
}

data class Point(val x: Int, val y: Int, val type: String = "") {
    override fun toString(): String {
        return "($x,$y)"
    }

    override fun equals(other: Any?): Boolean {
        return other is Point && other.x == x && other.y == y
    }
}