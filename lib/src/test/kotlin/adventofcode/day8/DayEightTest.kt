package adventofcode.day8

class DayEightTest {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            DayEightTest().testSimple()
            DayEightTest().analyzeForest()
        }
    }

    fun testSimple() {
        DayEight().discoverForest("small")
    }

    fun analyzeForest() {
        DayEight().discoverForest("forest")
    }
}