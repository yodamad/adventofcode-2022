package adventofcode.day9

class DayNineTest {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            //DayNineTest().sample()
            //DayNineTest().playWithRope()
            DayNineTest().complexSample()
            DayNineTest().medium()
            DayNineTest().playWithFullRope()
        }
    }

    fun sample() {
        DayNine().playWithRope("sample")
    }

    fun playWithRope() {
        DayNine().playWithRope("moves")
    }

    fun complexSample() {
        DayNine().playWithRope("sample", 9)
    }

    fun medium() {
        DayNine().playWithRope("medium", 9)
    }

    fun playWithFullRope() {
        DayNine().playWithRope("moves", 9)
    }
}