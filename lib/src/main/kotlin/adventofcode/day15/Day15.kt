package adventofcode.day15

import adventofcode.shared.getLines
import java.util.stream.LongStream
import kotlin.math.abs

class Day15 {

    val DAY = "day15"
    val map = mutableListOf<Point>()
    private val ranges = mutableListOf<LongRange>()

    fun sensors(file: String, lineNb: Long) {
        val sensors = file.getLines(DAY).map { buildSensor(it) }
        println(sensors)

        map.addAll(sensors.map { Point(it.x, it.y, 'S') })
        map.addAll(sensors.map { Point(it.beaconX, it.beaconY, 'B') })

        // printMap()

        sensors.forEach { optimizeVoid(it, lineNb) }

        println(abs(ranges.maxOf { it.endInclusive } - ranges.minOf { it.first }))
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

    private fun fillVoid(sensor: Sensor, lineNb: Long) {
        println("Analyzing sensor $sensor")
        if (lineNb in sensor.y - sensor.distance() .. sensor.y + sensor.distance()) {
            val absX = sensor.distance() - abs(lineNb - sensor.y)
            LongRange(-absX, absX).forEach {
                val point = Point(it + sensor.x, lineNb, '#')
                if (!map.contains(point)) {
                    println("Adding point $point")
                    map.add(point)
                }
            }
        }
    }

    private fun fillMap(sensor: Sensor, lineNb: Long) {
        println("Analyzing sensor $sensor")

        if (lineNb in sensor.y - sensor.distance() .. sensor.y + sensor.distance()) {

            LongStream.range(-sensor.distance(), 1).forEach { indexX ->
                if (lineNb == sensor.y + sensor.distance() + indexX + 1) {
                    LongStream.range(sensor.y, sensor.y + sensor.distance() + indexX + 1).forEach { indexY ->
                        val point = Point(sensor.x - indexX, indexY, '#')
                        if (!map.contains(point) && indexY == lineNb) {
                            map.add(point)
                        }
                    }
                }
            }

            LongStream.range(-sensor.distance(), 1).forEach { indexX ->
                if (lineNb in sensor.y .. sensor.y + sensor.distance() + indexX + 1) {
                    LongStream.range(sensor.y, sensor.y + sensor.distance() + indexX + 1).forEach { indexY ->
                        val point = Point(sensor.x + indexX, indexY, '#')
                        if (!map.contains(point) && indexY == lineNb) {
                            map.add(point)
                        }
                    }
                }
            }

            LongStream.range(-sensor.distance(), 1).forEach { indexX ->
                if (lineNb in sensor.y - sensor.distance() - indexX .. sensor.y) {
                    LongStream.range(sensor.y - sensor.distance() - indexX, sensor.y).forEach { indexY ->
                        val point = Point(sensor.x + indexX, indexY, '#')
                        if (!map.contains(point) && indexY == lineNb) {
                            map.add(point)
                        }
                    }
                }
            }

            LongStream.range(-sensor.distance(), 1).forEach { indexX ->
                if (lineNb in sensor.y - sensor.distance() - indexX .. sensor.y) {
                    LongStream.range(sensor.y - sensor.distance() - indexX, sensor.y).forEach { indexY ->
                        val point = Point(sensor.x - indexX, indexY, '#')
                        if (!map.contains(point) && indexY == lineNb) {
                            map.add(point)
                        }
                    }
                }
            }
        }
    }
}

data class Sensor(val x:Long, val y:Long, val beaconX:Long, val beaconY: Long) {
    fun distance(): Long = abs(x-beaconX) + abs(y-beaconY)

    override fun toString(): String {
        return "($x,$y) has ${distance()} from ($beaconX,$beaconY)"
    }
}

data class Point(val x:Long, val y:Long, val type: Char?) {
    override fun equals(other: Any?): Boolean {
        return other is Point && other.x == x && other.y == y
    }
}