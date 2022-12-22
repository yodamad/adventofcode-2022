package adventofcode.day15

import adventofcode.shared.getLines
import java.awt.geom.Point2D
import java.util.stream.LongStream
import kotlin.math.abs
import kotlin.math.sign

class Day15 {

    val DAY = "day15"
    val map = mutableListOf<Point>()
    private val ranges = mutableListOf<LongRange>()

    fun sensors(file: String, lineNb: Long, max: Int) {
        val sensors = file.getLines(DAY).map { buildSensor(it) }
        println(sensors)

        map.addAll(sensors.map { Point(it.x, it.y, 'S') })
        map.addAll(sensors.map { Point(it.beaconX, it.beaconY, 'B') })

        // printMap()

        sensors.forEach { optimizeVoid(it, lineNb) }

        println(abs(ranges.maxOf { it.last } - ranges.minOf { it.first }))

        // Understand thanks to https://github.com/tginsberg/advent-2022-kotlin
        val result = sensors.firstNotNullOf {
            val top = Point(it.x, it.y - it.distance() - 1)
            val bottom = Point(it.x, it.y + it.distance() + 1)
            val left = Point(it.x - it.distance() - 1, it.y)
            val right = Point(it.x + it.distance() + 1, it.y)

            // Draw diamond
            (top.drawLine(right) + right.drawLine(bottom) + bottom.drawLine(left) + left.drawLine(top))
                .filter { pt -> pt.x in (0..max) && pt.y in (0 .. max) }
                .firstOrNull { checkPoint -> sensors.none { s -> s.isInZone(checkPoint) } }
            }.computeTuningFrequency()

        println(result)
    }

    private fun buildSensor(line: String): Sensor {
        val regex = """Sensor at x=(\d+), y=(\d+): closest beacon is at x=(-*)(\d+), y=(-*)(\d+)""".toRegex()
        val (sX, sY, negativeX, bX, negativeY, bY) = regex.find(line)!!.destructured
        return Sensor(sX.toLong(), sY.toLong(),
            if (negativeX.isNullOrEmpty()) bX.toLong() else bX.toLong()*-1,
            if (negativeY.isNullOrEmpty()) bY.toLong() else bY.toLong()*-1)
    }

    private fun printMap() {
        val sortedMap = map.sortedBy { it.y }.sortedBy { it.x }
        println(sortedMap)

        LongStream.range(sortedMap.minOf { it.y }, sortedMap.maxOf { it.y }+1).forEach {indexY ->
            LongStream.range(sortedMap.minOf { it.x }, sortedMap.maxOf { it.x }+1).forEach { indexX ->
                if (!sortedMap.contains(Point(indexX, indexY, null))) {
                    print('.')
                } else {
                    print(sortedMap[sortedMap.indexOf(Point(indexX, indexY, null))]!!.type)
                }
            }
            println()
        }
    }

    private fun optimizeVoid(sensor: Sensor, lineNb: Long) {
        println("Analyzing sensor $sensor")
        if (lineNb in sensor.y - sensor.distance() .. sensor.y + sensor.distance()) {
            val absX = sensor.distance() - abs(lineNb - sensor.y)
            println("Add range ${LongRange(-absX + sensor.x, absX + sensor.x)}")
            ranges.add(LongRange(-absX + sensor.x, absX + sensor.x))
        }
    }
}

data class Sensor(val x:Long, val y:Long, val beaconX:Long, val beaconY: Long) {
    fun distance(): Long = abs(x-beaconX) + abs(y-beaconY)

    fun isInZone(point: Point): Boolean {
        return Point(x, y).distanceWith(point) <= distance()
    }

    override fun toString(): String {
        return "($x,$y) has ${distance()} from ($beaconX,$beaconY)"
    }
}

data class Point(val x:Long, val y:Long, val type: Char? = '.') {

    fun distanceWith(other: Point): Long = abs(x - other.x) + abs(y - other.y)

    fun computeTuningFrequency() = x * 4_000_000 + y

    fun drawLine(endOfLine: Point): List<Point> {
        val xWay = (endOfLine.x - x).sign
        val yWay = (endOfLine.y - y).sign
        return (1 .. maxOf(abs(x- endOfLine.x), abs(y-endOfLine.y)))
            .scan(this) { newPoint, _ -> Point(newPoint.x + xWay, newPoint.y + yWay, '#') }
    }

    override fun equals(other: Any?): Boolean {
        return other is Point && other.x == x && other.y == y
    }
}