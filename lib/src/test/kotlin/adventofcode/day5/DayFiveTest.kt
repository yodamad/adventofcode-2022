package adventofcode.day5

class DayFiveTest {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            //DayFiveTest().test()
            //DayFiveTest().computeCrates()
            //DayFiveTest().testWithoutReverse()
            DayFiveTest().computeCratesWithoutReverse()
        }
    }

    fun test() {
        return DayFive().computeStacks("sample")
    }

    fun computeCrates() {
        return DayFive().computeStacks("crates")
    }

    fun testWithoutReverse() {
        return DayFive().computeStacks("sample", false)
    }

    fun computeCratesWithoutReverse() {
        return DayFive().computeStacks("crates", false)
    }
}