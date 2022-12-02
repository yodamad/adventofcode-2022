package adventofcode.day2

import adventofcode.shared.getLines

class DayTwo() {

    val DAY = "day2"
    val choices = mutableMapOf<String, Choice>()
    val newChoices = mutableMapOf<String, Map<String, Choice>>()

    init {
        val rock = Choice("X", 1, mapOf("A" to 3, "B" to 0, "C" to 6))
        val paper = Choice("Y", 2, mapOf("A" to 6, "B" to 3, "C" to 0))
        val scissor = Choice("Z", 3, mapOf("A" to 0, "B" to 6, "C" to 3))

        choices["X"] = rock
        choices["Y"] = paper
        choices["Z"] = scissor

        newChoices["A"] = mapOf("X" to scissor, "Y" to rock, "Z" to paper)
        newChoices["B"] = mapOf("X" to rock, "Y" to paper, "Z" to scissor)
        newChoices["C"] = mapOf("X" to paper, "Y" to scissor, "Z" to rock)
    }

    private fun computeRow(row: String): Int {
        val elements = row.split(" ")
        val score = choices[elements[1]]!!.points + choices[elements[1]]!!.challenges[elements[0]]!!
        // println(score)
        return score
    }

    fun globalScore(file: String): Int = file.getLines(DAY).sumOf { e -> computeRow(e) }

    private fun computeAdvancedRow(row: String): Int {
        val elements = row.split(" ")

        val score = when (elements[1]) {
            "X" -> 0 + newChoices[elements[0]]!![elements[1]]!!.points
            "Y" -> 3 + newChoices[elements[0]]!![elements[1]]!!.points
            "Z" -> 6 + newChoices[elements[0]]!![elements[1]]!!.points
            else -> 0
        }
        // println(score)
        return score
    }

    fun globalAdvancedScore(file: String) = file.getLines(DAY).sumOf { e -> computeAdvancedRow(e) }
}

class Choice(val name: String, val points: Int, val challenges: Map<String, Int>)