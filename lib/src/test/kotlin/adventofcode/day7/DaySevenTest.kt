package adventofcode.day7

class DaySevenTest {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            DaySevenTest().sample()
            DaySevenTest().commands()
        }
    }

    fun sample() {
        DaySeven().commands("sample")
    }

    fun commands() {
        DaySeven().commands("commands")
    }
}