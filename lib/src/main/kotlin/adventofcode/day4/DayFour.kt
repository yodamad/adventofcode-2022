package adventofcode.day4

import adventofcode.shared.getLines
import java.util.stream.IntStream

class DayFour {

    val DAY = "day4"

    fun computeLines(file: String): Int =
        file.getLines(DAY)
            .map { l -> computeLine(l) }
            .map { p -> findEmptySet(p) }
            .filter { it == 0 }.toList().size

    fun computeLine(line: String): Pair<IntRange, IntRange> {
        val elves = line.split(",")
        var temp = elves[0].split("-")
        val firstRange = temp[0].toInt() .. temp[1].toInt()
        temp = elves[1].split("-")
        val secondRange =temp[0].toInt() .. temp[1].toInt()

        return Pair(firstRange, secondRange)
    }

    fun findEmptySet(pair: Pair<IntRange, IntRange>) =
        minOf(pair.first.minus(pair.second).size, pair.second.minus(pair.first).size)

    fun findOverlaps(file: String) =
        file.getLines(DAY)
            .map { l -> computeLine(l) }
            .map { p -> findOverlap(p) }
            .map { i ->
                //println(i)
                i.size
            }
            .count { it > 0 }

    fun findOverlap(pair: Pair<IntRange, IntRange>) =
        pair.first.intersect(pair.second)
}