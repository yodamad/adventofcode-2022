package adventofcode.day3

import adventofcode.shared.getLines
import java.util.stream.IntStream

class DayThree {

    val DAY = "day3"
    val alphabet = "_abcdefghijklmnopqrstuvwxyz"

    fun findLetterInRushpacks(file: String): Int {
        val lines = file.getLines(DAY)
        return lines.map { l -> findLetterInRushpack(l) }.sumOf { c -> computeScore(c) }
    }

    fun findLetterInRushpack(line: String): Char {
        val firstHalf = line.dropLast(line.length / 2)
        //println(firstHalf)
        val secondHalf = line.drop(line.length / 2)
        //println(secondHalf)
        val instersection = firstHalf.toCharArray().intersect(secondHalf.toCharArray().toSet())
        //println(instersection)
        return instersection.first()
    }

    private fun computeScore(char: Char): Int {
        val score = if (char.isUpperCase()) {
            26 + alphabet.indexOf(char.lowercaseChar())
        } else {
            alphabet.indexOf(char)
        }
        //println(score)
        return score
    }

    fun findBadges(file: String): Int {
        val lines = file.getLines(DAY)
        val nbOfGroups = lines.size / 3
        val groups = mutableListOf<List<String>>()

        IntStream.range(0, nbOfGroups).forEach { i ->
            groups.add(lines.slice(0 + (i * 3) .. 2 + (i * 3)))
        }

        //println(groups)

        return groups.map { g -> g.first().toSet().intersect(g[1].toSet()).intersect(g[2].toSet()) }
            .map {
                //println(it.first())
                it.first()
            }
            .sumOf { c -> computeScore(c) }
    }
}