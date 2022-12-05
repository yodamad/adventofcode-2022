package adventofcode.day4

class DayFourTest {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            //println(DayFourTest().findElves())
            println(DayFourTest().testOverlaps())
        }
    }

    fun testSample(): Int {
        return DayFour().computeLines("sample")
    }

    fun findElves(): Int {
        return DayFour().computeLines("pairs")
    }

    fun testSampleOverlap(): Int {
        return DayFour().findOverlaps("sample")
    }

    fun testOverlaps(): Int {
        return DayFour().findOverlaps("pairs")
    }
}