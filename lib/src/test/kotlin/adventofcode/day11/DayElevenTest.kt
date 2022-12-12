package adventofcode.day11

class DayElevenTest {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            //DayElevenTest().sampleMonkeys()
            DayElevenTest().monkeys()
        }
    }

    fun sampleMonkeys() {
        DayEleven().buildMonkey("sample")
    }

    fun monkeys() {
        DayEleven().buildMonkey("monkeys", 2)
    }
}