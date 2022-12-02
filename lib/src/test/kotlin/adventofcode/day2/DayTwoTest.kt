package adventofcode.day2

class DayTwoTest {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println(DayTwoTest().sample())
            println(DayTwoTest().easy())
            println(DayTwoTest().sampleAdvanced())
            println(DayTwoTest().easyAdvanced())
        }
    }

    fun sample() : Int {
        return DayTwo().globalScore("sample")
    }

    fun easy() : Int {
        return DayTwo().globalScore("easy")
    }

    fun sampleAdvanced(): Int {
        return DayTwo().globalAdvancedScore("sample")
    }

    fun easyAdvanced(): Int {
        return DayTwo().globalAdvancedScore("easy")
    }
}
