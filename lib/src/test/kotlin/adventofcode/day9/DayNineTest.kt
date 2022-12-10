package adventofcode.day9

class DayNineTest {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            DayNineTest().sample()
            DayNineTest().playWithRope()
        }
    }

    fun sample() {
        DayNine().playWithRope("sample")
    }

    fun playWithRope() {
        DayNine().playWithRope("moves")
    }
}