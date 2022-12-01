package adventofcode.day1

import adventofcode.shared.getLines

class DayOne {

    val DAY = "day1"

    fun computeFood(file: String): MutableList<Int> {
        val lines = file.getLines(DAY)
        var currentCarrier = 0
        val carriers = mutableListOf<Int>()
        lines.forEachIndexed { index, s ->
            if (s.isEmpty()) {
                carriers.add(currentCarrier)
                currentCarrier = 0
            } else
                currentCarrier += s.toInt()
        }
        carriers.add(currentCarrier)
        return carriers
    }

    fun getStrongest(file: String) = computeFood(file).maxOrNull()

    fun getTop3(file: String)  = computeFood(file).sortedDescending().take(3).sum()
}